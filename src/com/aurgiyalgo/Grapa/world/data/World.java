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

	/**
	 * @param position  Start position of the ray
	 * @param direction Normalized {@link Vector3f} indicating the direction of the ray
	 * @param distance  Maximum distance of the ray
	 * @param isPlace <code>True</code> for placing a block, <code>false</code> for breaking
	 */
	public void raycast(Vector3f position, Vector3f direction, float distance, boolean isPlace) {
		
	    float xPos = (float) Math.floor(position.x);
	    float yPos = (float) Math.floor(position.y);
	    float zPos = (float) Math.floor(position.z);
	    int stepX = GrapaMaths.signum(direction.x);
	    int stepY = GrapaMaths.signum(direction.y);
	    int stepZ = GrapaMaths.signum(direction.z);
	    Vector3f tMax = new Vector3f(GrapaMaths.intbound(position.x, direction.x), GrapaMaths.intbound(position.y, direction.y), GrapaMaths.intbound(position.z, direction.z));
	    Vector3f tDelta = new Vector3f((float)stepX / direction.x, (float)stepY / direction.y, (float)stepZ / direction.z);
	    float faceX = 0;
	    float faceY = 0;
	    float faceZ = 0;

	    do {
	        if (chunkHandler.getBlock((int)xPos, (int)yPos, (int)zPos) != 0) {
	            if (!isPlace) chunkHandler.setBlock(0, (int)xPos, (int)yPos, (int)zPos);
	            else chunkHandler.setBlock(6, (int)(xPos + faceX), (int)(yPos + faceY), (int)(zPos + faceZ));
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
