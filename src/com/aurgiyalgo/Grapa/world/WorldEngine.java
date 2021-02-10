package com.aurgiyalgo.Grapa.world;

import com.aurgiyalgo.Grapa.arch.Engine;
import com.aurgiyalgo.Grapa.graphics.generic.Camera;
import com.aurgiyalgo.Grapa.graphics.generic.CameraRaycast;
import com.aurgiyalgo.Grapa.world.components.ChunkRenderer;
import com.aurgiyalgo.Grapa.world.data.World;

public class WorldEngine extends Engine {
	
	private World world;
	private Camera camera;
	private CameraRaycast raycast;
	
	public WorldEngine() {
		camera = new Camera();
		world = new World(camera);
		addGameObject(camera);
		addGameObject(world);
		
		raycast = new CameraRaycast(camera);
	}
	
	public void updateProjectionMatrix() {
		world.getComponent(ChunkRenderer.class).get().createProjectionMatrix();
	}
	
	public void onClick(int button) {
		raycast.update();
		
		world.raycast(camera.transform.position, raycast.getCurrentRay(), 5, button != 0);
	}

}
