package com.aurgiyalgo.Grapa.graphics.model;

import java.util.ArrayList;
import java.util.List;

import com.aurgiyalgo.Grapa.graphics.generic.Vertex;
import com.aurgiyalgo.Grapa.world.data.Chunk;

public class ChunkModelBuilder {
	
	public static Model buildChunkModel(int[] data) {
		List<Vertex> vertices = new ArrayList<Vertex>();
		for (int x = 0; x < Chunk.CHUNK_WIDTH; x++) {
			for (int y = 0; y < Chunk.CHUNK_WIDTH; y++) {
				for (int z = 0; z < Chunk.CHUNK_WIDTH; z++) {
					
				}
			}
		}
		return null;
	}

}
