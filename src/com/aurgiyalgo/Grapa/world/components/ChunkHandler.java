package com.aurgiyalgo.Grapa.world.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.joml.Vector3i;

import com.aurgiyalgo.Grapa.arch.Component;
import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.world.data.Chunk;
import com.aurgiyalgo.Grapa.world.generation.ChunkPopulator;

/**
 * Handles all the chunk data of a world.
 */
public class ChunkHandler extends Component {
	
	private List<Chunk> loadedChunks;
	private List<Chunk> chunksToMesh;
	
	private ChunkPopulator populator;

	public ChunkHandler(GameObject object) {
		super(object);
		
		loadedChunks = new ArrayList<Chunk>();
		chunksToMesh = new ArrayList<Chunk>();
		
		populator = new ChunkPopulator();
		
		int sideX = 8;
		int sideY = 8;
		int sideZ = 8;
		
		//Temporary stress test code
		for (int i = 0; i < sideX; i++) {
			for (int j = 0; j < sideY; j++) {
				for (int k = 0; k < sideZ; k++) {
					Chunk c = new Chunk(new Vector3i(i - sideX/2, j - sideY/2, k - sideZ/2), this);
					c.generateChunk(populator);
					loadedChunks.add(c);
				}
			}
		}
	}

	@Override
	public void update(double delta) {
	}
	
	/**
	 * Update the meshes for all the loaded chunks
	 */
	public void updateChunkMeshes() {
		for (Chunk chunk : chunksToMesh) {
			chunk.updateModel();
		}
		chunksToMesh.clear();
	}
	
	/**
	 * Add a chunk to the meshing queue
	 * @param chunk Chunk to queue
	 */
	public void addChunkForMeshing(Chunk chunk) {
		chunksToMesh.add(chunk);
	}
	
	/**
	 * @param x The X component of the coordinates
	 * @param y The Y component of the coordinates
	 * @param z The Z component of the coordinates
	 * @return ID of the block at the coordinates, or 0 if the block is air or not found
	 */
	public int getBlock(int x, int y, int z) {
		for (Chunk c : loadedChunks) {
			if (!c.isInside(x, y, z)) continue;
		    return c.getBlock(Math.abs(c.getGridPosition().x * Chunk.CHUNK_WIDTH - x), Math.abs(c.getGridPosition().y * Chunk.CHUNK_WIDTH - y), Math.abs(c.getGridPosition().z * Chunk.CHUNK_WIDTH - z));
		}
		return 0;
	}
	
	/**
	 * @param id ID of the block to set
	 * @param x The X component of the coordinates
	 * @param y The Y component of the coordinates
	 * @param z The Z component of the coordinates
	 * @return <code>true</code> if block was successfully set, <code>false</code> if not
	 */
	public boolean setBlock(int id, int x, int y, int z) {
		for (Chunk c : loadedChunks) {
			if (!c.isInside(x, y, z)) continue;
		    return c.setBlock(id, Math.abs(c.getGridPosition().x * Chunk.CHUNK_WIDTH - x), Math.abs(c.getGridPosition().y * Chunk.CHUNK_WIDTH - y), Math.abs(c.getGridPosition().z * Chunk.CHUNK_WIDTH - z));
		}
		Chunk c = new Chunk(getChunkPositionAt(x, y, z), this);
		loadedChunks.add(c);
		return c.setBlock(id, Math.abs(c.getGridPosition().x * Chunk.CHUNK_WIDTH - x), Math.abs(c.getGridPosition().y * Chunk.CHUNK_WIDTH - y), Math.abs(c.getGridPosition().z * Chunk.CHUNK_WIDTH - z));
	}
	
	/**
	 * @param x The X component of the coordinates
	 * @param y The Y component of the coordinates
	 * @param z The Z component of the coordinates
	 * @return Chunk position in the world
	 */
	private Vector3i getChunkPositionAt(float x, float y, float z) {
		return new Vector3i((int) Math.floor(x / (float) Chunk.CHUNK_WIDTH), (int) Math.floor(y / (float) Chunk.CHUNK_WIDTH), (int) Math.floor(z / (float) Chunk.CHUNK_WIDTH));
	}
	
	public void updateChunkNextFrame(int x, int y, int z) {
		Optional<Chunk> chunk = getChunk(x, y, z);
		if (chunk.isPresent()) chunk.get().updateNextFrame();
	}
	
	public Optional<Chunk> getChunkAt(int x, int y, int z) {
		for (Chunk c : loadedChunks) {
			if (!c.isInside(x, y, z)) continue;
			return Optional.of(c);
		}
		return Optional.empty();
	}
	
	public Optional<Chunk> getChunk(int x, int y, int z) {
		for (Chunk c : loadedChunks) {
			if (x != c.getGridPosition().x) continue;
			if (y != c.getGridPosition().y) continue;
			if (z != c.getGridPosition().z) continue;
			return Optional.of(c);
		}
		return Optional.empty();
	}
	
	public List<Chunk> getChunks() {
		return loadedChunks;
	}
	
}
