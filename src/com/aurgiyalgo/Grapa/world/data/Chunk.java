package com.aurgiyalgo.Grapa.world.data;

import org.joml.Vector3i;

import com.aurgiyalgo.Grapa.arch.Transform;
import com.aurgiyalgo.Grapa.graphics.model.Model;
import com.aurgiyalgo.Grapa.graphics.model.ModelBuilder;
import com.aurgiyalgo.Grapa.graphics.model.ModelData;
import com.aurgiyalgo.Grapa.world.blocks.BlockRegister;

import lombok.Getter;

/**
 * Object to hold the data and model of a single chunk ({@value Chunk#CHUNK_WIDTH} blocks ^ 3).
 */
public class Chunk {
	
	public static final int CHUNK_WIDTH = 8;
	public static final int TOTAL_BLOCKS = CHUNK_WIDTH * CHUNK_WIDTH * CHUNK_WIDTH;
	
	@Getter private Model model;
	private int[][][] data;
	@Getter private Vector3i position;
	
	public Chunk(Vector3i position) {
		this.position = position;
		this.data = new int[CHUNK_WIDTH][CHUNK_WIDTH][CHUNK_WIDTH];
		
		for (int x = 0; x < CHUNK_WIDTH; x++) {
			for (int y = 0; y < CHUNK_WIDTH; y++) {
				for (int z = 0; z < CHUNK_WIDTH; z++) {
					if (y == 0) {
						data[x][y][z] = 5;
						continue;
					}
					if (y == 7) {
						data[x][y][z] = 4;
						continue;
					}
					data[x][y][z] = 1;
				}
			}
		}
		
		long timer = System.nanoTime();
		ModelBuilder modelBuilder = new ModelBuilder();
		for (int x = 0; x < CHUNK_WIDTH; x++) {
			for (int y = 0; y < CHUNK_WIDTH; y++) {
				for (int z = 0; z < CHUNK_WIDTH; z++) {
					BlockRegister.getBlock(data[x][y][z]).createModel(modelBuilder, x, y, z, z-1 < 0 || data[x][y][z-1] == 0, y+1 > 7 || data[x][y+1][z] == 0, x-1 < 0 || data[x-1][y][z] == 0);
				}
			}
		}
//		System.out.println("Model time: " + (System.nanoTime() - timer) / 1000000d + "ms");
		ModelData modelData = modelBuilder.getModelData();
		model = new Model(modelData, new Transform());
	}
	
	public int getBlock(int x, int y, int z) {
		return data[x][y][z];
	}
	
	public void setBlock(int id, int x, int y, int z) {
		data[x][y][z] = id;
	}

}
