package com.aurgiyalgo.Grapa.world.data;

import org.joml.Vector2f;
import org.joml.Vector2i;

import com.aurgiyalgo.Grapa.world.generation.ChunkPopulator;

import lombok.Getter;

/**
 * Object to hold the data and model of a single chunk ({@value Chunk#CHUNK_SIDE} blocks ^ 3).
 */
public class Chunk {
	
	public static final int CHUNK_SIDE = 16;
	public static final int CHUNK_HEIGHT = 16;
	public static final int TOTAL_BLOCKS = CHUNK_SIDE * CHUNK_HEIGHT * CHUNK_SIDE;
	
	protected int[][][] data;
	@Getter
	private Vector2i gridPosition;
	@Getter
	private Vector2f worldPosition;
	
	@Getter
	private ChunkBundle bundle;
	
	public Chunk(Vector2i gridPosition) {
		this.gridPosition = gridPosition;
		this.worldPosition = new Vector2f(gridPosition.x, gridPosition.y).mul(Chunk.CHUNK_SIDE);
		this.data = new int[CHUNK_SIDE][CHUNK_SIDE][CHUNK_SIDE];
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
		if (x < 0 || y < 0 || z < 0 || x > CHUNK_SIDE -1 || y > CHUNK_SIDE -1 || z > CHUNK_SIDE -1) return 0;
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
		if (x < 0 || y < 0 || z < 0 || x > CHUNK_SIDE -1 || y > CHUNK_SIDE -1 || z > CHUNK_SIDE -1) return false;
		if (data[x][y][z] == id) return false;
		data[x][y][z] = id;
		bundle.updateNextFrame();
		
		// TODO Temporary code for updating neighbor chunks when a block is changed in a border
		if (x == 0) bundle.getChunkHandler().updateChunkNextFrame(gridPosition.x - 1, gridPosition.y);
		if (x == CHUNK_SIDE - 1) bundle.getChunkHandler().updateChunkNextFrame(gridPosition.x + 1, gridPosition.y);
		if (z == 0) bundle.getChunkHandler().updateChunkNextFrame(gridPosition.x, gridPosition.y- 1);
		if (z == CHUNK_SIDE - 1) bundle.getChunkHandler().updateChunkNextFrame(gridPosition.x, gridPosition.y + 1);
		return true;
	}
	
	/**
	 * @param x The X coordinate of the block in the chunk
	 * @param y The Y coordinate of the block in the chunk
	 * @param z The Z coordinate of the block in the chunk
	 * @return <code>true</code> if the given coordinates are inside the chunk, <code>false</code> if not
	 */
	public boolean isInside(int x, int y, int z) {
		if (Math.floor(x / (float) CHUNK_SIDE) != gridPosition.x) return false;
		if (y < 0 || y >= CHUNK_HEIGHT) return false;
		if (Math.floor(z / (float) CHUNK_SIDE) != gridPosition.y) return false;
		return true;
	}
	
	protected void setBundle(ChunkBundle bundle) {
		this.bundle = bundle;
	}

}
