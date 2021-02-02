package com.aurgiyalgo.Grapa.world;

import com.aurgiyalgo.Grapa.arch.Engine;
import com.aurgiyalgo.Grapa.graphics.generic.Camera;
import com.aurgiyalgo.Grapa.graphics.generic.CameraRaycast;
import com.aurgiyalgo.Grapa.world.components.ChunkRenderer;
import com.aurgiyalgo.Grapa.world.data.World;

public class WorldEngine extends Engine {
	
	private World world;
	private Camera camera;
	
	public WorldEngine() {
		camera = new Camera();
		world = new World(camera);
		addGameObject(camera);
		addGameObject(world);
	}
	
	public void updateProjectionMatrix() {
		world.getComponent(ChunkRenderer.class).get().createProjectionMatrix();
	}
	
	public void onClick() {
		CameraRaycast raycast = new CameraRaycast(camera, world.getComponent(ChunkRenderer.class).get().getProjectionMatrix());
		raycast.update();
		
//		System.out.println("Position: " + camera.transform.position);
		world.raycast(raycast.getStartPosition(), raycast.getCurrentRay(), 64);
	}

}
