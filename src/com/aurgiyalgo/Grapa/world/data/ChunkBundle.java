package com.aurgiyalgo.Grapa.world.data;

import com.aurgiyalgo.Grapa.arch.Transform;
import com.aurgiyalgo.Grapa.graphics.model.Model;
import com.aurgiyalgo.Grapa.graphics.model.ModelBuilder;
import com.aurgiyalgo.Grapa.graphics.model.ModelData;
import com.aurgiyalgo.Grapa.world.blocks.BlockRegister;
import com.aurgiyalgo.Grapa.world.components.ChunkHandler;

import lombok.Getter;

/**
 * Contains a chunk and its model
 */
public class ChunkBundle {
	
	@Getter
	private Chunk chunk;
	@Getter
	private Model model;
	
	@Getter
	private ChunkHandler chunkHandler;
	
	@Getter
	private boolean updateNextFrame;
	
	@Getter
	private boolean isModelUpdated = false;
	
	private ModelBuilder modelBuilder;
	
	public ChunkBundle(Chunk chunk, ChunkHandler chunkHandler) {
		this.chunkHandler = chunkHandler;
		this.chunk = chunk;
		this.chunk.setBundle(this);
		
		forceUpdateNextFrame();
	}
	
	/**
	 * Updates the chunk model
	 */
	public void updateModel() {
		long timer = System.nanoTime();
		modelBuilder = new ModelBuilder();
		for (int x = 0; x < Chunk.CHUNK_WIDTH; x++) {
			for (int y = 0; y < Chunk.CHUNK_WIDTH; y++) {
				for (int z = 0; z < Chunk.CHUNK_WIDTH; z++) {
					BlockRegister.getBlock(chunk.data[x][y][z]).createModel(modelBuilder, x, y, z, chunk.data, chunk);
				}
			}
		}
//		System.out.println("Model time: " + (System.nanoTime() - timer) / 1000000d + "ms");
	}
	
	public boolean loadModelToGpu() {
		if (modelBuilder == null) return false;
		ModelData modelData = modelBuilder.getModelData();
		model = new Model(modelData, new Transform());
		isModelUpdated = true;
		modelBuilder = null;
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
	
	protected void forceUpdateNextFrame() {
		chunkHandler.addChunkForMeshing(this);
		isModelUpdated = false;
	}

}
