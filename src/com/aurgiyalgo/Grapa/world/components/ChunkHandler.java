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
			loadedChunks.add(new Chunk(new Vector3i(0, 0, 0).mul(Chunk.CHUNK_WIDTH)));
		}
	}

	@Override
	public void update(double delta) {
	}
	
	public int getBlock(int x, int y, int z) {
		for (Chunk c : loadedChunks) {
			if (x / Chunk.CHUNK_WIDTH != c.getPosition().x) continue;
			if (y / Chunk.CHUNK_WIDTH != c.getPosition().y) continue;
			if (z / Chunk.CHUNK_WIDTH != c.getPosition().z) continue;
			return c.getBlock(x / Chunk.CHUNK_WIDTH, y / Chunk.CHUNK_WIDTH, z / Chunk.CHUNK_WIDTH);
		}
		System.out.println("Block not found!");
		return 0;
	}
	
	public void setBlock(int id, int x, int y, int z) {
		for (Chunk c : loadedChunks) {
			if (x / Chunk.CHUNK_WIDTH != c.getPosition().x) continue;
			if (y / Chunk.CHUNK_WIDTH != c.getPosition().y) continue;
			if (z / Chunk.CHUNK_WIDTH != c.getPosition().z) continue;
		    c.setBlock(id, x / Chunk.CHUNK_WIDTH, y / Chunk.CHUNK_WIDTH, z / Chunk.CHUNK_WIDTH);
		}
		System.out.println("Block not found!");
	}
	
	public List<Chunk> getChunks() {
		return loadedChunks;
	}
	
}
