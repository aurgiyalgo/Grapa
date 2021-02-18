package com.aurgiyalgo.Grapa.world.data;

import org.joml.Vector3f;
import org.joml.Vector3i;

import com.aurgiyalgo.Grapa.arch.Transform;
import com.aurgiyalgo.Grapa.graphics.model.Model;
import com.aurgiyalgo.Grapa.graphics.model.ModelBuilder;
import com.aurgiyalgo.Grapa.graphics.model.ModelData;
import com.aurgiyalgo.Grapa.utils.PerlinNoise;
import com.aurgiyalgo.Grapa.world.blocks.BlockRegister;
import com.aurgiyalgo.Grapa.world.components.ChunkHandler;

import lombok.Getter;

/**
 * Object to hold the data and model of a single chunk ({@value Chunk#CHUNK_WIDTH} blocks ^ 3).
 */
public class Chunk {
	
	public static final int CHUNK_WIDTH = 8;
	public static final int TOTAL_BLOCKS = CHUNK_WIDTH * CHUNK_WIDTH * CHUNK_WIDTH;
	
	@Getter
	private Model model;
	private int[][][] data;
	@Getter
	private Vector3i gridPosition;
	@Getter
	private Vector3f worldPosition;
	
	@Getter
	private boolean updateNextFrame;
	
	@Getter
	private boolean isModelUpdated = false;
	
	@Getter
	private ChunkHandler chunkHandler;
	
	public Chunk(Vector3i gridPosition, ChunkHandler chunkHandler) {
		this.gridPosition = gridPosition;
		this.chunkHandler = chunkHandler;
		this.worldPosition = new Vector3f(gridPosition.x, gridPosition.y, gridPosition.z).mul(Chunk.CHUNK_WIDTH);
		this.data = new int[CHUNK_WIDTH][CHUNK_WIDTH][CHUNK_WIDTH];
		
		forceUpdateNextFrame();
	}
	
	/**
	 * Generates block data for the chunk (temporary until implementation of procedural generation)
	 */
	public void generateChunk() {
		PerlinNoise noise = new PerlinNoise(50);
		double currentNoise;
		
		for (int x = 0; x < CHUNK_WIDTH; x++) {
			for (int y = 0; y < CHUNK_WIDTH; y++) {
				for (int z = 0; z < CHUNK_WIDTH; z++) {
					currentNoise = noise.noise(x + gridPosition.x * CHUNK_WIDTH, z + gridPosition.z * CHUNK_WIDTH) * 20;
					if (currentNoise > (gridPosition.y * CHUNK_WIDTH + y)) {
						data[x][y][z] = 1;
					}
					if (Math.floor(currentNoise) == (gridPosition.y * CHUNK_WIDTH + y)) {
						data[x][y][z] = 4;
					}
					if (gridPosition.y * CHUNK_WIDTH + y < currentNoise - 5) {
						data[x][y][z] = 5;
					}
					if (y + gridPosition.y * CHUNK_WIDTH < -20 && noise.noise(x + gridPosition.x * CHUNK_WIDTH, y + gridPosition.y * CHUNK_WIDTH, z + gridPosition.z * CHUNK_WIDTH) > 0.35) {
						data[x][y][z] = 0;
					}
				}
			}
		}
	}
	
	/**
	 * Updates the chunk model
	 */
	public void updateModel() {
		long timer = System.nanoTime();
		ModelBuilder modelBuilder = new ModelBuilder();
		for (int x = 0; x < CHUNK_WIDTH; x++) {
			for (int y = 0; y < CHUNK_WIDTH; y++) {
				for (int z = 0; z < CHUNK_WIDTH; z++) {
					BlockRegister.getBlock(data[x][y][z]).createModel(modelBuilder, x, y, z, data, this);
				}
			}
		}
		System.out.println("Model time: " + (System.nanoTime() - timer) / 1000000d + "ms");
		
		ModelData modelData = modelBuilder.getModelData();
		model = new Model(modelData, new Transform());
		isModelUpdated = true;
	}
	
	/**
	 * @param x The X coordinate of the block in the chunk
	 * @param y The Y coordinate of the block in the chunk
	 * @param z The Z coordinate of the block in the chunk
	 * @return ID of the block at the coordinates, or 0 if the block is air or not found
	 */
	public int getBlock(int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x > CHUNK_WIDTH-1 || y > CHUNK_WIDTH-1 || z > CHUNK_WIDTH-1) return 0;
		return data[x][y][z];
	}

	/**
	 * @param id ID of the block to set
	 * @param x The X coordinate of the block in the chunk
	 * @param y The Y coordinate of the block in the chunk
	 * @param z The Z coordinate of the block in the chunk
	 * @return <code>true</code> if block was successfully set, <code>false</code> if not
	 */
	public boolean setBlock(int id, int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x > CHUNK_WIDTH-1 || y > CHUNK_WIDTH-1 || z > CHUNK_WIDTH-1) return false;
		if (data[x][y][z] == id) return false;
		data[x][y][z] = id;
		updateNextFrame();
		
		// TODO Temporary code for updating neighbor chunks when a block is changed in a border
		if (x == 0) chunkHandler.updateChunkNextFrame(gridPosition.x - 1, gridPosition.y, gridPosition.z);
		if (x == CHUNK_WIDTH - 1) chunkHandler.updateChunkNextFrame(gridPosition.x + 1, gridPosition.y, gridPosition.z);
		if (y == 0) chunkHandler.updateChunkNextFrame(gridPosition.x, gridPosition.y - 1, gridPosition.z);
		if (y == CHUNK_WIDTH - 1) chunkHandler.updateChunkNextFrame(gridPosition.x, gridPosition.y + 1, gridPosition.z);
		if (z == 0) chunkHandler.updateChunkNextFrame(gridPosition.x, gridPosition.y, gridPosition.z - 1);
		if (z == CHUNK_WIDTH - 1) chunkHandler.updateChunkNextFrame(gridPosition.x, gridPosition.y, gridPosition.z + 1);
		return true;
	}
	
	/**
	 * @param x The X coordinate of the block in the chunk
	 * @param y The Y coordinate of the block in the chunk
	 * @param z The Z coordinate of the block in the chunk
	 * @return <code>true</code> if the given coordinates are inside the chunk, <code>false</code> if not
	 */
	public boolean isInside(int x, int y, int z) {
		if (Math.floor(x / (float) CHUNK_WIDTH) != gridPosition.x) return false;
		if (Math.floor(y / (float) CHUNK_WIDTH) != gridPosition.y) return false;
		if (Math.floor(z / (float) CHUNK_WIDTH) != gridPosition.z) return false;
		return true;
	}
	
	/**
	 * Queue the chunk to update its model at the beginning of the next frame
	 */
	public void updateNextFrame() {
		if (!isModelUpdated) return;
		chunkHandler.addChunkForMeshing(this);
		isModelUpdated = false;
	}
	
	private void forceUpdateNextFrame() {
		chunkHandler.addChunkForMeshing(this);
		isModelUpdated = false;
	}

}
