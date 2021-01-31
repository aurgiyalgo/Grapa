package com.aurgiyalgo.Grapa.world.data;

import org.joml.Vector3f;

import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.graphics.generic.Camera;
import com.aurgiyalgo.Grapa.utils.GrapaMaths;
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
		int xPos = (int) Math.floor(position.x);
		int yPos = (int) Math.floor(position.y);
		int zPos = (int) Math.floor(position.z);
		int stepX = (int) Math.signum(direction.x);
		int stepY = (int) Math.signum(direction.y);
		int stepZ = (int) Math.signum(direction.z);
		
		Vector3f tMax = new Vector3f(GrapaMaths.intbound(position.x, direction.x), GrapaMaths.intbound(position.x, direction.x), GrapaMaths.intbound(position.x, direction.x));
		Vector3f tDelta = new Vector3f((float) stepX / direction.x, (float) stepY / direction.y, (float) stepZ / direction.z);
		
		float faceX, faceY, faceZ;
		
		do {
			if (chunkHandler.getBlock(xPos, yPos, zPos) != 0) {
				System.out.println("Block found | ID: " + chunkHandler.getBlock(xPos, yPos, zPos));
				chunkHandler.setBlock(0, xPos, yPos, zPos);
				break;
			}
			if (tMax.x < tMax.y) {
	            if (tMax.x < tMax.z) {
	                if (tMax.x > distance) break;

	                xPos += stepX;
	                tMax.x += tDelta.x;

	                faceX = -stepX;
	                faceY = 0;
	                faceZ = 0;
	            } else {
	                if (tMax.z > distance) break;
	                zPos += stepZ;
	                tMax.z += tDelta.z;
	                faceX = 0;
	                faceY = 0;
	                faceZ = -stepZ;
	            }
	        } else {
	            if (tMax.y < tMax.z) {
	                if (tMax.y > distance) break;
	                yPos += stepY;
	                tMax.y += tDelta.y;
	                faceX = 0;
	                faceY = -stepY;
	                faceZ = 0;
	            } else {
	                if (tMax.z > distance) break;
	                zPos += stepZ;
	                tMax.z += tDelta.z;
	                faceX = 0;
	                faceY = 0;
	                faceZ = -stepZ;
	            }
	        }
		} while (true);
	}

}
