package com.aurgiyalgo.Grapa.world.components;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

import com.aurgiyalgo.Grapa.Grapa;
import com.aurgiyalgo.Grapa.arch.Component;
import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.graphics.display.DisplayManager;
import com.aurgiyalgo.Grapa.graphics.generic.Camera;
import com.aurgiyalgo.Grapa.graphics.generic.CameraRaycast;
import com.aurgiyalgo.Grapa.graphics.model.Model;
import com.aurgiyalgo.Grapa.graphics.shaders.StaticShader;
import com.aurgiyalgo.Grapa.utils.GrapaMaths;
import com.aurgiyalgo.Grapa.world.data.Chunk;

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
	
	private CameraRaycast raycast;

	public ChunkRenderer(GameObject object, Camera camera) {
		super(object);
		
		this.camera = camera;
		
		createProjectionMatrix();
		
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();

		this.raycast = new CameraRaycast(camera, projectionMatrix);
		
		chunkHandler = object.getComponent(ChunkHandler.class).get();
	}

	@Override
	public void update(double delta) {
		raycast.update();
		
		shader.start();
		shader.loadViewMatrix(camera);
		
		GL30.glActiveTexture(GL30.GL_TEXTURE0);
		Grapa.TEXTURE.bind();
		
		long timer = System.nanoTime();
		for (Chunk c : chunkHandler.getChunks()) {
			Model model = c.getModel();
			GL30.glBindVertexArray(model.getData().getVaoId());
			GL30.glEnableVertexAttribArray(Grapa.POSITION_VERTEX_ATTRIB_INDEX);
			GL30.glEnableVertexAttribArray(Grapa.TEXTURE_VERTEX_ATTRIB_INDEX);
			GL30.glEnableVertexAttribArray(Grapa.NORMALS_VERTEX_ATTRIB_INDEX);
			
			Matrix4f transformationMatrix = GrapaMaths.createTransformationMatrix(c.getWorldPosition());
			shader.loadTransformationMatrix(transformationMatrix);
			
			GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, model.getData().getVertexCount());
		}
//		System.out.println("Draw time: " + (System.nanoTime() - timer)/1000000d + "ms");
		
		GL30.glDisableVertexAttribArray(Grapa.POSITION_VERTEX_ATTRIB_INDEX);
		GL30.glDisableVertexAttribArray(Grapa.TEXTURE_VERTEX_ATTRIB_INDEX);
		GL30.glDisableVertexAttribArray(Grapa.NORMALS_VERTEX_ATTRIB_INDEX);
		GL30.glBindVertexArray(0);
		
		shader.stop();
	}
	
	public void createProjectionMatrix() {
		projectionMatrix = new Matrix4f();
		RELATION = (float) DisplayManager.getWindowHeight() / (float) DisplayManager.getWindowWidth();
		projectionMatrix.setOrtho(-SCREEN_LIMIT/2, SCREEN_LIMIT/2, -SCREEN_LIMIT * RELATION/2, SCREEN_LIMIT * RELATION/2, NEAR_PLANE, FAR_PLANE);
//		projectionMatrix.perspective((float) Math.toRadians(30), RELATION, NEAR_PLANE, FAR_PLANE);
		
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

}
