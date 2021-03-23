package com.aurgiyalgo.Grapa.graphics.model;

import java.util.ArrayList;
import java.util.List;

import com.aurgiyalgo.Grapa.graphics.generic.Vertex;
import com.aurgiyalgo.Grapa.world.data.Chunk;

/**
 * Class to build a chunk mesh using its block data. Made to replace {@link ModelBuilder}.
 */
public class ChunkModelBuilder {
	
	public static Model buildChunkModel(int[] data) {
		List<Vertex> vertices = new ArrayList<Vertex>();
		for (int x = 0; x < Chunk.CHUNK_SIDE; x++) {
			for (int y = 0; y < Chunk.CHUNK_SIDE; y++) {
				for (int z = 0; z < Chunk.CHUNK_SIDE; z++) {
					
				}
			}
		}
		return null;
	}

}
