package com.aurgiyalgo.Grapa.world.data;

import org.joml.Vector3f;

import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.graphics.generic.Camera;
import com.aurgiyalgo.Grapa.world.components.ChunkHandler;
import com.aurgiyalgo.Grapa.world.components.ChunkRenderer;

public class World extends GameObject {
	
	private ChunkHandler chunkHandler;

	public World(Camera camera) {
		super("World");
		
		chunkHandler = new ChunkHandler(this);
		
		addComponent(chunkHandler);
		addComponent(new ChunkRenderer(this, camera));
	}
	
	/**
	 * @param position Start position of the ray
	 * @param direction Normalized {@link Vector3f} indicating the direction of the ray
	 * @param distance Maximum distance of the ray
	 */
	public void raycast(Vector3f position, Vector3f direction, float distance) {
		
		Vector3f start = new Vector3f(position);
		Vector3f stepVector = new Vector3f(direction).mul(0.125f);

		long timer = System.nanoTime();
		for (float i = 0; i < distance; i+= 0.1f) {
			start.add(stepVector);
			if (chunkHandler.setBlock(0, (int) start.x, (int) start.y, (int) start.z)) break;
		}
		System.out.println("Raycast time: " + (System.nanoTime() - timer) / 1000000d + "ms");
	}

}
