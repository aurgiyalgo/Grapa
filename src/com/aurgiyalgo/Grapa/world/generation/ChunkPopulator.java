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
		int[][][] data = new int[Chunk.CHUNK_WIDTH][Chunk.CHUNK_WIDTH][Chunk.CHUNK_WIDTH];

		double currentNoise;

		for (int x = 0; x < Chunk.CHUNK_WIDTH; x++) {
			for (int y = 0; y < Chunk.CHUNK_WIDTH; y++) {
				for (int z = 0; z < Chunk.CHUNK_WIDTH; z++) {
					currentNoise = noise.noise(x + chunk.getGridPosition().x * Chunk.CHUNK_WIDTH, z + chunk.getGridPosition().z * Chunk.CHUNK_WIDTH) * 20;
					if (currentNoise > (chunk.getGridPosition().y * Chunk.CHUNK_WIDTH + y)) {
						data[x][y][z] = 1;
					}
					if (Math.floor(currentNoise) == (chunk.getGridPosition().y * Chunk.CHUNK_WIDTH + y)) {
						data[x][y][z] = 4;
					}
					if (chunk.getGridPosition().y * Chunk.CHUNK_WIDTH + y < currentNoise - 5) {
						data[x][y][z] = 5;
					}
					if (noise.noise(x + chunk.getGridPosition().x * Chunk.CHUNK_WIDTH, y + chunk.getGridPosition().y * Chunk.CHUNK_WIDTH,
							z + chunk.getGridPosition().z * Chunk.CHUNK_WIDTH) > 0.35) {
						data[x][y][z] = 0;
					}
				}
			}
		}
		
		return data;
	}

}
