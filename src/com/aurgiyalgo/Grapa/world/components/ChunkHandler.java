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
		for (int i = 0; i < 100; i++) {
			loadedChunks.add(new Chunk(new Vector3i(i%10 - 5, 0, i/10 -5).mul(Chunk.CHUNK_WIDTH)));
		}
	}

	@Override
	public void update(double delta) {
	}
	
	public int getBlock(int x, int y, int z) {
		for (Chunk c : loadedChunks) {
			if (Math.floor(1f / x) / Chunk.CHUNK_WIDTH != c.getPosition().x) continue;
			if (Math.floor(1f / y) / Chunk.CHUNK_WIDTH != c.getPosition().y) continue;
			if (Math.floor(1f / z) / Chunk.CHUNK_WIDTH != c.getPosition().z) continue;
			return c.getBlock(x / Chunk.CHUNK_WIDTH, y / Chunk.CHUNK_WIDTH, z / Chunk.CHUNK_WIDTH);
		}
		return 0;
	}
	
	public void setBlock(int id, int x, int y, int z) {
		System.out.println(x + " " + y + " " + z);
		for (Chunk c : loadedChunks) {
			if (Math.floor(1f / x) / Chunk.CHUNK_WIDTH != c.getPosition().x) continue;
			if (Math.floor(1f / y) / Chunk.CHUNK_WIDTH != c.getPosition().y) continue;
			if (Math.floor(1f / z) / Chunk.CHUNK_WIDTH != c.getPosition().z) continue;
		    c.setBlock(id, x / Chunk.CHUNK_WIDTH, y / Chunk.CHUNK_WIDTH, z / Chunk.CHUNK_WIDTH);
		    System.out.println("Block set");
		}
		System.out.println("Block not found!");
	}
	
	public List<Chunk> getChunks() {
		return loadedChunks;
	}
	
}
