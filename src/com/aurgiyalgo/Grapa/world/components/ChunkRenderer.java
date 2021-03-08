package com.aurgiyalgo.Grapa.world.components;

import java.util.Iterator;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL30;

import com.aurgiyalgo.Grapa.Grapa;
import com.aurgiyalgo.Grapa.arch.Component;
import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.graphics.display.DisplayManager;
import com.aurgiyalgo.Grapa.graphics.generic.Camera;
import com.aurgiyalgo.Grapa.graphics.model.Model;
import com.aurgiyalgo.Grapa.graphics.shaders.StaticShader;
import com.aurgiyalgo.Grapa.utils.GrapaMaths;
import com.aurgiyalgo.Grapa.world.data.Chunk;
import com.aurgiyalgo.Grapa.world.data.ChunkBundle;

import lombok.Getter;

/**
 * Handles the rendering of all the chunks in a world.
 */
public class ChunkRenderer extends Component {
	
	public static final float FAR_PLANE = 1000;
	public static final float NEAR_PLANE = 0.01f;
	public static final float SCREEN_LIMIT = 16f;
	public static float RELATION = 1f;
	
	@Getter
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	
	private ChunkHandler chunkHandler;
	
	private Camera camera;
	
	private Vector3i renderOrigin;

	public ChunkRenderer(GameObject object, Camera camera) {
		super(object);
		
		this.camera = camera;
		this.renderOrigin = new Vector3i();
		
		createProjectionMatrix();
		
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		
		chunkHandler = object.getComponent(ChunkHandler.class).get();
	}

	@Override
	public void update(double delta) {
		shader.start();
		shader.loadViewMatrix(camera);
		
		GL30.glActiveTexture(GL30.GL_TEXTURE0);
		Grapa.TEXTURE.bind();
		
		setRenderOrigin(camera.transform.position);
		
		renderAround(renderOrigin, 3);
//		System.out.println("Draw time (" + chunkHandler.getChunks().size() + " chunks rendered): " + (System.nanoTime() - timer)/1000000d + "ms");
		
		GL30.glDisableVertexAttribArray(Grapa.POSITION_VERTEX_ATTRIB_INDEX);
		GL30.glDisableVertexAttribArray(Grapa.TEXTURE_VERTEX_ATTRIB_INDEX);
		GL30.glDisableVertexAttribArray(Grapa.NORMALS_VERTEX_ATTRIB_INDEX);
		GL30.glBindVertexArray(0);
		
		shader.stop();
	}
	
	private void setRenderOrigin(Vector3f position) {
		renderOrigin = getChunkPositionAt(position.x, position.y, position.z);
	}
	
	private Vector3i getChunkPositionAt(float x, float y, float z) {
		return new Vector3i((int) Math.floor(x / (float) Chunk.CHUNK_WIDTH), (int) Math.floor(y / (float) Chunk.CHUNK_WIDTH), (int) Math.floor(z / (float) Chunk.CHUNK_WIDTH));
	}
	
	private void renderAround(Vector3i position, int distance) {
		Iterator<ChunkBundle> iterator = chunkHandler.getChunks().iterator();
		
		while (iterator.hasNext()) {
			ChunkBundle bundle = iterator.next();
			Vector3i gridPosition = bundle.getChunk().getGridPosition();
			if (Math.abs(gridPosition.x - position.x) > distance
					|| Math.abs(gridPosition.y - position.y) > distance
					|| Math.abs(gridPosition.z - position.z) > distance) {
				iterator.remove();
				continue;
			}
			if (!bundle.isHasModel()) continue;
			
			Model model = bundle.getModel();
			GL30.glBindVertexArray(model.getData().getVaoId());
			GL30.glEnableVertexAttribArray(Grapa.POSITION_VERTEX_ATTRIB_INDEX);
			GL30.glEnableVertexAttribArray(Grapa.TEXTURE_VERTEX_ATTRIB_INDEX);
			GL30.glEnableVertexAttribArray(Grapa.NORMALS_VERTEX_ATTRIB_INDEX);
			
			Matrix4f transformationMatrix = GrapaMaths.createTransformationMatrix(bundle.getChunk().getWorldPosition());
			shader.loadTransformationMatrix(transformationMatrix);
			
			GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, model.getData().getVertexCount());
		}

		for (int x = -distance; x <= distance; x++) {
			for (int y = -distance; y <= distance; y++) {
				for (int z = -distance; z <= distance; z++) {
					chunkHandler.generateChunk(position.x + x, position.y + y, position.z + z);
				}
			}
		}
	}
	
	/**
	 * Creates the projection matrix and loads it to the shader
	 */
	public void createProjectionMatrix() {
		projectionMatrix = new Matrix4f();
		RELATION = (float) DisplayManager.getWidth() / (float) DisplayManager.getHeight();
		projectionMatrix.perspective((float) Math.toRadians(90), RELATION, NEAR_PLANE, FAR_PLANE);
		
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

}
