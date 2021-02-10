package com.aurgiyalgo.Grapa.world.data;

import org.joml.Vector3f;
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
	@Getter private Vector3i gridPosition;
	@Getter private Vector3f worldPosition;
	
	public Chunk(Vector3i gridPosition) {
		this.gridPosition = gridPosition;
		this.worldPosition = new Vector3f(gridPosition.x, gridPosition.y, gridPosition.z).mul(Chunk.CHUNK_WIDTH);
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
		
		updateModel();
	}
	
	/**
	 * Updates the chunk model
	 */
	private void updateModel() {
		long timer = System.nanoTime();
		ModelBuilder modelBuilder = new ModelBuilder();
		for (int x = 0; x < CHUNK_WIDTH; x++) {
			for (int y = 0; y < CHUNK_WIDTH; y++) {
				for (int z = 0; z < CHUNK_WIDTH; z++) {
					BlockRegister.getBlock(data[x][y][z]).createModel(modelBuilder, x, y, z, data);
				}
			}
		}
		System.out.println("Model time: " + (System.nanoTime() - timer) / 1000000d + "ms");
		
		ModelData modelData = modelBuilder.getModelData();
		model = new Model(modelData, new Transform());
	}
	
	/**
	 * @param x The X coordinate of the block in the chunk
	 * @param y The Y coordinate of the block in the chunk
	 * @param z The Z coordinate of the block in the chunk
	 * @return ID of the block at the coordinates, or 0 if the block is air or not found
	 */
	public int getBlock(int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x > 7 || y > 7 || z > 7) return 0;
		return data[x][y][z];
	}

	/**
	 * @param id ID of the block to set
	 * @param x The X coordinate of the block in the chunk
	 * @param y The Y coordinate of the block in the chunk
	 * @param z The Z coordinate of the block in the chunk
	 * @return <code>true</code> if block was succesfully set, <code>false</code> if not
	 */
	public boolean setBlock(int id, int x, int y, int z) {
		if (id == data[x][y][z]) return false;
		if (x < 0 || y < 0 || z < 0 || x > 7 || y > 7 || z > 7) return false;
		data[x][y][z] = id;
		updateModel();
		return true;
	}

}
