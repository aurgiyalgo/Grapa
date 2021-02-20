package com.aurgiyalgo.Grapa.world.data;

import org.joml.Vector3f;
import org.joml.Vector3i;

import com.aurgiyalgo.Grapa.world.generation.ChunkPopulator;

import lombok.Getter;

/**
 * Object to hold the data and model of a single chunk ({@value Chunk#CHUNK_WIDTH} blocks ^ 3).
 */
public class Chunk {
	
	public static final int CHUNK_WIDTH = 16;
	public static final int TOTAL_BLOCKS = CHUNK_WIDTH * CHUNK_WIDTH * CHUNK_WIDTH;
	
	protected int[][][] data;
	@Getter
	private Vector3i gridPosition;
	@Getter
	private Vector3f worldPosition;
	
	@Getter
	private ChunkBundle bundle;
	
	public Chunk(Vector3i gridPosition) {
		this.gridPosition = gridPosition;
		this.worldPosition = new Vector3f(gridPosition.x, gridPosition.y, gridPosition.z).mul(Chunk.CHUNK_WIDTH);
		this.data = new int[CHUNK_WIDTH][CHUNK_WIDTH][CHUNK_WIDTH];
	}
	
	/**
	 * Generates block data for the chunk (temporary until implementation of procedural generation)
	 */
	public void generateChunk(ChunkPopulator populator) {
		this.data = populator.populate(this);
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
		bundle.updateNextFrame();
		
		// TODO Temporary code for updating neighbor chunks when a block is changed in a border
		if (x == 0) bundle.getChunkHandler().updateChunkNextFrame(gridPosition.x - 1, gridPosition.y, gridPosition.z);
		if (x == CHUNK_WIDTH - 1) bundle.getChunkHandler().updateChunkNextFrame(gridPosition.x + 1, gridPosition.y, gridPosition.z);
		if (y == 0) bundle.getChunkHandler().updateChunkNextFrame(gridPosition.x, gridPosition.y - 1, gridPosition.z);
		if (y == CHUNK_WIDTH - 1) bundle.getChunkHandler().updateChunkNextFrame(gridPosition.x, gridPosition.y + 1, gridPosition.z);
		if (z == 0) bundle.getChunkHandler().updateChunkNextFrame(gridPosition.x, gridPosition.y, gridPosition.z - 1);
		if (z == CHUNK_WIDTH - 1) bundle.getChunkHandler().updateChunkNextFrame(gridPosition.x, gridPosition.y, gridPosition.z + 1);
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
	
	protected void setBundle(ChunkBundle bundle) {
		this.bundle = bundle;
	}

}
