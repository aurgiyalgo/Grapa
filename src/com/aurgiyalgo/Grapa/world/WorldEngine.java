package com.aurgiyalgo.Grapa.world;

import com.aurgiyalgo.Grapa.arch.Engine;
import com.aurgiyalgo.Grapa.graphics.generic.Camera;
import com.aurgiyalgo.Grapa.world.components.ChunkRenderer;
import com.aurgiyalgo.Grapa.world.data.World;

public class WorldEngine extends Engine {
	
	private World world;
	
	public WorldEngine() {
		Camera camera = new Camera();
		world = new World(camera);
		addGameObject(camera);
		addGameObject(world);
	}
	
	public void updateProjectionMatrix() {
		world.getComponent(ChunkRenderer.class).get().createProjectionMatrix();
	}

}
