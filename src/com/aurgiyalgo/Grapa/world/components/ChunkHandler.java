package com.aurgiyalgo.Grapa.world.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.joml.Vector3i;

import com.aurgiyalgo.Grapa.arch.Component;
import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.world.data.Chunk;
import com.aurgiyalgo.Grapa.world.data.ChunkBundle;
import com.aurgiyalgo.Grapa.world.generation.ChunkPopulator;

/**
 * Handles all the chunk data of a world.
 */
public class ChunkHandler extends Component {
	
	private List<ChunkBundle> loadedChunks;
	private List<ChunkBundle> chunksToMesh;
	private List<ChunkBundle> chunksToLoad;
	
	private ChunkPopulator populator;

	public ChunkHandler(GameObject object) {
		super(object);
		
		loadedChunks = Collections.synchronizedList(new ArrayList<ChunkBundle>());
		chunksToMesh = Collections.synchronizedList(new ArrayList<ChunkBundle>());
		chunksToLoad = new ArrayList<ChunkBundle>();
		
		populator = new ChunkPopulator();
	}

	@Override
	public void update(double delta) {
	}
	
	/**
	 * Update the meshes for all the loaded chunks
	 */
	public void updateChunkMeshes() {
		synchronized (chunksToMesh) {
			Iterator<ChunkBundle> iterator = chunksToMesh.iterator();
			while (iterator.hasNext()) {
				ChunkBundle bundle = iterator.next();
				bundle.updateModel();
				synchronized (chunksToLoad) {
					chunksToLoad.add(bundle);
				}
				iterator.remove();
			}
		}
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			System.err.println("Fatal error on the meshing thread");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void loadChunkMeshesToGpu() {
		synchronized (chunksToLoad) {
			Iterator<ChunkBundle> iterator = chunksToLoad.iterator();
			while (iterator.hasNext()) {
				ChunkBundle bundle = iterator.next();
				if (bundle.loadModelToGpu()) iterator.remove();
				System.out.println("Loading");
			}
		}
	}
	
	protected void loadChunk(int x, int y, int z) {
		Chunk chunk = new Chunk(new Vector3i(x, y, z));
		chunk.generateChunk(populator);
		loadedChunks.add(new ChunkBundle(chunk, this));
	}
	
	protected void unloadChunk(int x, int y, int z) {
		Iterator<ChunkBundle> iterator = loadedChunks.iterator();
		while (iterator.hasNext()) {
			ChunkBundle bundle = iterator.next();
			if (x != bundle.getChunk().getGridPosition().x) continue;
			if (y != bundle.getChunk().getGridPosition().y) continue;
			if (z != bundle.getChunk().getGridPosition().z) continue;
			iterator.remove();
			break;
		}
	}
	
	/**
	 * Add a chunk to the meshing queue
	 * @param chunk Chunk to queue
	 */
	public void addChunkForMeshing(ChunkBundle chunkBundle) {
		synchronized (chunksToMesh) {
			chunksToMesh.add(chunkBundle);
		}
	}
	
	/**
	 * @param x The X component of the coordinates
	 * @param y The Y component of the coordinates
	 * @param z The Z component of the coordinates
	 * @return ID of the block at the coordinates, or 0 if the block is air or not found
	 */
	public int getBlock(int x, int y, int z) {
		synchronized (loadedChunks) {
			for (ChunkBundle bundle : loadedChunks) {
				if (!bundle.getChunk().isInside(x, y, z)) continue;
			    return bundle.getChunk().getBlock(Math.abs(bundle.getChunk().getGridPosition().x * Chunk.CHUNK_WIDTH - x), Math.abs(bundle.getChunk().getGridPosition().y * Chunk.CHUNK_WIDTH - y), Math.abs(bundle.getChunk().getGridPosition().z * Chunk.CHUNK_WIDTH - z));
			}
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
		for (ChunkBundle bundle : loadedChunks) {
			if (!bundle.getChunk().isInside(x, y, z)) continue;
		    return bundle.getChunk().setBlock(id, Math.abs(bundle.getChunk().getGridPosition().x * Chunk.CHUNK_WIDTH - x), Math.abs(bundle.getChunk().getGridPosition().y * Chunk.CHUNK_WIDTH - y), Math.abs(bundle.getChunk().getGridPosition().z * Chunk.CHUNK_WIDTH - z));
		}
		Chunk c = new Chunk(getChunkPositionAt(x, y, z));
		loadedChunks.add(new ChunkBundle(c, this));
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
		Optional<ChunkBundle> chunk = getChunk(x, y, z);
		if (chunk.isPresent()) chunk.get().updateNextFrame();
	}
	
	public Optional<ChunkBundle> getChunkAt(int x, int y, int z) {
		for (ChunkBundle bundle : loadedChunks) {
			if (!bundle.getChunk().isInside(x, y, z)) continue;
			return Optional.of(bundle);
		}
		return Optional.empty();
	}
	
	public Optional<ChunkBundle> getChunk(int x, int y, int z) {
		for (ChunkBundle bundle : loadedChunks) {
			if (x != bundle.getChunk().getGridPosition().x) continue;
			if (y != bundle.getChunk().getGridPosition().y) continue;
			if (z != bundle.getChunk().getGridPosition().z) continue;
			return Optional.of(bundle);
		}
		return Optional.empty();
	}
	
	public void generateChunk(int x, int y, int z) {
		for (ChunkBundle bundle : loadedChunks) {
			if (x != bundle.getChunk().getGridPosition().x) continue;
			if (y != bundle.getChunk().getGridPosition().y) continue;
			if (z != bundle.getChunk().getGridPosition().z) continue;
			return;
		}
		Chunk chunk = new Chunk(new Vector3i(x, y, z));
		chunk.generateChunk(populator);
		ChunkBundle bundle = new ChunkBundle(chunk, this);
		loadedChunks.add(bundle);
		return;
	}
	
	public List<ChunkBundle> getChunks() {
		return loadedChunks;
	}
	
}
