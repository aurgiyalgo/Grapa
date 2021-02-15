package com.aurgiyalgo.Grapa.demo;

import org.lwjgl.opengl.GL30;

import com.aurgiyalgo.Grapa.input.Input;
import com.aurgiyalgo.Grapa.scenes.IScene;
import com.aurgiyalgo.Grapa.world.WorldEngine;

public class WorldScene implements IScene {
	
	private WorldEngine engine;
	private Input input;
	
	public WorldScene() {
		engine = new WorldEngine();
		input = Input.getInstance();
	}

	@Override
	public void onShow() {
		input.hideCursor();
	}

	@Override
	public void onHide() {
		
	}

	@Override
	public void update(float delta) {
		GL30.glEnable(GL30.GL_DEPTH_TEST);
		
		GL30.glEnable(GL30.GL_CULL_FACE);
		GL30.glCullFace(GL30.GL_FRONT_FACE);
		
		engine.updateChunkMeshes();
		engine.update(delta);
	}

	@Override
	public void onResize() {
		engine.updateProjectionMatrix();
	}

}
