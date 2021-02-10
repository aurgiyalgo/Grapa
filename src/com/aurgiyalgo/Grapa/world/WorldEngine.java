package com.aurgiyalgo.Grapa.world;

import com.aurgiyalgo.Grapa.arch.Engine;
import com.aurgiyalgo.Grapa.graphics.generic.Camera;
import com.aurgiyalgo.Grapa.graphics.generic.CameraRaycast;
import com.aurgiyalgo.Grapa.world.components.ChunkHandler;
import com.aurgiyalgo.Grapa.world.components.ChunkRenderer;
import com.aurgiyalgo.Grapa.world.data.World;

import lombok.Getter;
import lombok.Setter;

public class WorldEngine extends Engine {
	
	private World world;
	private Camera camera;
	private CameraRaycast raycast;
	
	private Thread chunkMeshingThread;
	
	@Getter @Setter
	private boolean isMeshingEnabled;
	
	public WorldEngine() {
		camera = new Camera();
		world = new World(camera);
		addGameObject(camera);
		addGameObject(world);
		
		raycast = new CameraRaycast(camera);
		
		setupAsyncChunkMeshing();
	}
	
	public void updateProjectionMatrix() {
		world.getComponent(ChunkRenderer.class).get().createProjectionMatrix();
	}
	
	public void updateChunkMeshes() {
		world.getComponent(ChunkHandler.class).get().updateChunkMeshes();
	}
	
	public void enableChunkMeshing() {
		isMeshingEnabled = true;
		chunkMeshingThread.start();
	}
	
	public void disableChunkMeshing() {
		isMeshingEnabled = false;
	}
	
	private void setupAsyncChunkMeshing() {
		chunkMeshingThread = new Thread("Chunk Meshing") {
			
			@Override
			public void run() {
				while (isMeshingEnabled) {
					world.getComponent(ChunkHandler.class).get().updateChunkMeshes();
				}
			}
			
		};
	}
	
	public void onClick(int button) {
		raycast.update();
		
		world.raycast(camera.transform.position, raycast.getCurrentRay(), 5, button != 0);
	}

}
