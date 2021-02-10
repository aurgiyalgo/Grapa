package com.aurgiyalgo.Grapa.world.components;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3i;

import com.aurgiyalgo.Grapa.arch.Component;
import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.world.data.Chunk;

/**
 * Handles all the chunk data of a world.
 */
public class ChunkHandler extends Component {
	
	private List<Chunk> loadedChunks;
	private List<Chunk> chunksToMesh;

	public ChunkHandler(GameObject object) {
		super(object);
		
		loadedChunks = new ArrayList<Chunk>();
		chunksToMesh = new ArrayList<Chunk>();
		
		//Temporary stress test code
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 1; j++) {
				for (int k = 0; k < 20; k++) {
					loadedChunks.add(new Chunk(new Vector3i(i - 10, j, k - 10), this));
				}
			}
		}
	}

	@Override
	public void update(double delta) {
	}
	
	public void updateChunkMeshes() {
		for (Chunk chunk : chunksToMesh) {
			chunk.updateModel();
		}
		chunksToMesh.clear();
	}
	
	public void addChunkForMeshing(Chunk chunk) {
		chunksToMesh.add(chunk);
	}
	
	/**
	 * @param x The X component of the coordinates
	 * @param y The Y component of the coordinates
	 * @param z The Z component of the coordinates
	 * @return ID of the block at the coordinates, or 0 if the block is air or not found
	 */
	public int getBlock(int x, int y, int z) {
		for (Chunk c : loadedChunks) {
			if (!c.isInside(x, y, z)) continue;
		    return c.getBlock(Math.abs(c.getGridPosition().x * Chunk.CHUNK_WIDTH - x), Math.abs(c.getGridPosition().y * Chunk.CHUNK_WIDTH - y), Math.abs(c.getGridPosition().z * Chunk.CHUNK_WIDTH - z));
		}
		return 0;
	}
	
	/**
	 * @param id ID of the block to set
	 * @param x The X component of the coordinates
	 * @param y The Y component of the coordinates
	 * @param z The Z component of the coordinates
	 * @return <code>true</code> if block was successfully set, <code>false</code> if not
	 */
	public boolean setBlock(int id, int x, int y, int z) {
		for (Chunk c : loadedChunks) {
			if (!c.isInside(x, y, z)) continue;
		    return c.setBlock(id, Math.abs(c.getGridPosition().x * Chunk.CHUNK_WIDTH - x), Math.abs(c.getGridPosition().y * Chunk.CHUNK_WIDTH - y), Math.abs(c.getGridPosition().z * Chunk.CHUNK_WIDTH - z));
		}
		return false;
	}
	
	public List<Chunk> getChunks() {
		return loadedChunks;
	}
	
}
