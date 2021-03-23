package com.aurgiyalgo.Grapa.world.generation;

import java.util.Random;

import com.aurgiyalgo.Grapa.utils.PerlinNoise;
import com.aurgiyalgo.Grapa.world.data.Chunk;

public class ChunkPopulator {

	private long seed;
	private PerlinNoise noise;

	public ChunkPopulator() {
		this.seed = (long) (new Random().nextFloat() * 50000);
		this.noise = new PerlinNoise(seed);
	}

	public ChunkPopulator(long seed) {
		this.seed = seed;
		this.noise = new PerlinNoise(seed);
	}

	public int[][][] populate(Chunk chunk) {
		int[][][] data = new int[Chunk.CHUNK_SIDE][Chunk.CHUNK_SIDE][Chunk.CHUNK_SIDE];

		double currentNoise;

		for (int x = 0; x < Chunk.CHUNK_SIDE; x++) {
			for (int y = 0; y < Chunk.CHUNK_SIDE; y++) {
				for (int z = 0; z < Chunk.CHUNK_SIDE; z++) {
					currentNoise = noise.noise(x + chunk.getGridPosition().x * Chunk.CHUNK_SIDE, z + chunk.getGridPosition().y * Chunk.CHUNK_SIDE) * 50;
					if (currentNoise > y) {
						data[x][y][z] = 1;
					}
					if (Math.floor(currentNoise) == y) {
						data[x][y][z] = 4;
					}
					if (y < currentNoise - 5) {
						data[x][y][z] = 5;
					}
					if (noise.noise(x + chunk.getGridPosition().x * Chunk.CHUNK_SIDE, y,
							z + chunk.getGridPosition().y * Chunk.CHUNK_SIDE) > 0.35) {
						data[x][y][z] = 0;
					}
				}
			}
		}
		
		return data;
	}

}
