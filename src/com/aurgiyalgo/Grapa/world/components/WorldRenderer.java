package com.aurgiyalgo.Grapa.world.components;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;

import com.aurgiyalgo.Grapa.Grapa;
import com.aurgiyalgo.Grapa.arch.Component;
import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.graphics.display.DisplayManager;
import com.aurgiyalgo.Grapa.graphics.model.Model;
import com.aurgiyalgo.Grapa.graphics.shaders.StaticShader;
import com.aurgiyalgo.Grapa.utils.GrapaMaths;
import com.aurgiyalgo.Grapa.world.data.Chunk;

public class WorldRenderer extends Component {
	
	private static final float FAR_PLANE = 1000;
	private static final float NEAR_PLANE = 0.01f;
	private static final float SCREEN_LIMIT = 16f;
//	private static float RELATION;
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	
	private ChunkHandler chunkHandler;

	public WorldRenderer(GameObject object) {
		super(object);
		createProjectionMatrix();
		
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		
		chunkHandler = object.getComponent(ChunkHandler.class).get();
	}

	@Override
	public void update(double delta) {
		shader.start();
		
		for (Chunk c : chunkHandler.getChunks()) {
			Model model = c.getModel();
			GL30.glBindVertexArray(model.getData().getVaoId());
			GL30.glEnableVertexAttribArray(Grapa.POSITION_VERTEX_ATTRIB_INDEX);
			GL30.glEnableVertexAttribArray(Grapa.TEXTURE_VERTEX_ATTRIB_INDEX);
			GL30.glEnableVertexAttribArray(Grapa.NORMALS_VERTEX_ATTRIB_INDEX);
			
			GL30.glActiveTexture(GL30.GL_TEXTURE0);
			Grapa.TEXTURE.bind();
			
			Matrix4f transformationMatrix = GrapaMaths.createTransformationMatrix(c.getPosition());
			shader.loadTransformationMatrix(transformationMatrix);
			
			GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, model.getData().getVertexCount());
		}
		
		GL30.glDisableVertexAttribArray(Grapa.POSITION_VERTEX_ATTRIB_INDEX);
		GL30.glDisableVertexAttribArray(Grapa.TEXTURE_VERTEX_ATTRIB_INDEX);
		GL30.glDisableVertexAttribArray(Grapa.NORMALS_VERTEX_ATTRIB_INDEX);
		GL30.glBindVertexArray(0);
		
		shader.stop();
	}
	
	public void createProjectionMatrix() {
		projectionMatrix = new Matrix4f();
		float RELATION = (float) DisplayManager.getWindowHeight() / (float) DisplayManager.getWindowWidth();
		projectionMatrix.setOrtho(-SCREEN_LIMIT/2, SCREEN_LIMIT/2, -SCREEN_LIMIT * RELATION/2, SCREEN_LIMIT * RELATION/2, NEAR_PLANE, FAR_PLANE);
	}

}
