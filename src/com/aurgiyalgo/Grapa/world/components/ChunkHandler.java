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

	public ChunkHandler(GameObject object) {
		super(object);
		
		loadedChunks = new ArrayList<Chunk>();
		
		//Temporary stress test code
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 1; j++) {
				loadedChunks.add(new Chunk(new Vector3i(i, 0, j)));
			}
		}
	}

	@Override
	public void update(double delta) {
	}
	
	/**
	 * @param x The X component of the coordinates
	 * @param y The Y component of the coordinates
	 * @param z The Z component of the coordinates
	 * @return ID of the block at the coordinates, or 0 if the block is air or not found
	 */
	public int getBlock(int x, int y, int z) {
		for (Chunk c : loadedChunks) {
			if (Math.floor(x / (float) Chunk.CHUNK_WIDTH) != c.getGridPosition().x) continue;
			if (Math.floor(y / (float) Chunk.CHUNK_WIDTH) != c.getGridPosition().y) continue;
			if (Math.floor(z / (float) Chunk.CHUNK_WIDTH) != c.getGridPosition().z) continue;
			return c.getBlock(Math.abs(x) % Chunk.CHUNK_WIDTH, Math.abs(y) % Chunk.CHUNK_WIDTH, Math.abs(z) % Chunk.CHUNK_WIDTH);
		}
		return 0;
	}
	
	/**
	 * @param id ID of the block to set
	 * @param x The X component of the coordinates
	 * @param y The Y component of the coordinates
	 * @param z The Z component of the coordinates
	 */
	public void setBlock(int id, int x, int y, int z) {
		for (Chunk c : loadedChunks) {
			if (Math.floor(x / (float) Chunk.CHUNK_WIDTH) != c.getGridPosition().x) continue;
			if (Math.floor(y / (float) Chunk.CHUNK_WIDTH) != c.getGridPosition().y) continue;
			if (Math.floor(z / (float) Chunk.CHUNK_WIDTH) != c.getGridPosition().z) continue;
		    c.setBlock(id, Math.abs(x) % Chunk.CHUNK_WIDTH, Math.abs(y) % Chunk.CHUNK_WIDTH, Math.abs(z) % Chunk.CHUNK_WIDTH);
		    return;
		}
	}
	
	public List<Chunk> getChunks() {
		return loadedChunks;
	}
	
}
