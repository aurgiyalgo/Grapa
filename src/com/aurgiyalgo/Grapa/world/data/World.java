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
	
	public void raycast(Vector3f position, Vector3f direction, float distance) {
		
		Vector3f start = new Vector3f(position);
		Vector3f stepVector = new Vector3f(direction).mul(0.125f);
		
		System.out.println("Start: " + position);
		System.out.println("Direction: " + direction);
		
		for (float i = 0; i < distance; i+= 0.001f) {
			start.add(stepVector);
			if (chunkHandler.getBlock((int) start.x, (int) start.y, (int) start.z) != 0) {
				System.out.println("Block found | ID: " + chunkHandler.getBlock((int) start.x, (int) start.y, (int) start.z) + " | Pos: " + start.x + " " + start.y + " " + start.z);
				chunkHandler.setBlock(0, (int) start.x, (int) start.y, (int) start.z);
				break;
			}
		}
	}

}
