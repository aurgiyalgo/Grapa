package com.aurgiyalgo.Grapa.world.blocks;

import com.aurgiyalgo.Grapa.graphics.model.ModelBuilder;
import com.aurgiyalgo.Grapa.graphics.textures.TextureRegion;
import com.aurgiyalgo.Grapa.world.components.ChunkHandler;
import com.aurgiyalgo.Grapa.world.data.Chunk;

public abstract class Block {
	
	private int id;

	protected TextureRegion texture;
	
	public Block(int id) {
		this.id = id;
		texture = new TextureRegion(id);
	}
	
	public void createModel(ModelBuilder modelBuilder, int x, int y, int z, int[][][] data, Chunk chunk) {
		
		ChunkHandler chunkHandler = chunk.getChunkHandler();
		int chunkX = chunk.getGridPosition().x * Chunk.CHUNK_WIDTH;
		int chunkY = chunk.getGridPosition().y * Chunk.CHUNK_WIDTH;
		int chunkZ = chunk.getGridPosition().z * Chunk.CHUNK_WIDTH;
		
		if ((z-1 >= 0 && data[x][y][z-1] == 0) || z-1 < 0 && chunkHandler.getBlock(chunkX + x, chunkY + y, chunkZ + z - 1) == 0) {
			modelBuilder.normal(-1, 0, 0);
			modelBuilder.uv(texture.getU1(), texture.getV1());
			modelBuilder.vertex(0 + x, 0 + y, 0 + z);
			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 0 + y, 0 + z);

			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV2());
			modelBuilder.vertex(1 + x, 1 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 0 + y, 0 + z);
		}
		
		if ((z+1 < Chunk.CHUNK_WIDTH && data[x][y][z+1] == 0) || z+1 >= Chunk.CHUNK_WIDTH && chunkHandler.getBlock(chunkX + x, chunkY + y, chunkZ + z + 1) == 0) {
			modelBuilder.normal(-1, 0, 0);
			modelBuilder.uv(texture.getU1(), texture.getV1());
			modelBuilder.vertex(0 + x, 0 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 0 + y, 1 + z);
			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 1 + z);

			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 0 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV2());
			modelBuilder.vertex(1 + x, 1 + y, 1 + z);
		}
		
		if ((x-1 >= 0 && data[x-1][y][z] == 0) || x-1 < 0 && chunkHandler.getBlock(chunkX + x - 1, chunkY + y, chunkZ + z) == 0) {
			modelBuilder.normal(0, 0, -1);
			modelBuilder.uv(texture.getU1(), texture.getV1());
			modelBuilder.vertex(0 + x, 0 + y, 1 + z);
			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(0 + x, 0+ y, 0 + z);

			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(0 + x, 0 + y, 0 + z);
		}
		
		if ((x+1 < Chunk.CHUNK_WIDTH && data[x+1][y][z] == 0) || x+1 >= Chunk.CHUNK_WIDTH && chunkHandler.getBlock(chunkX + x + 1, chunkY + y, chunkZ + z) == 0) {
			modelBuilder.normal(0, 0, 1);
			modelBuilder.uv(texture.getU1(), texture.getV1());
			modelBuilder.vertex(1 + x, 0 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 0+ y, 0 + z);
			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(1 + x, 1 + y, 1 + z);

			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(1 + x, 1 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 0 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV2());
			modelBuilder.vertex(1 + x, 1 + y, 0 + z);
		}
		
		if ((y-1 >= 0 && data[x][y-1][z] == 0) || y-1 < 0 && chunkHandler.getBlock(chunkX + x, chunkY + y - 1, chunkZ + z) == 0) {
			modelBuilder.normal(0, -1, 0);
			modelBuilder.uv(texture.getU1(), texture.getV1());
			modelBuilder.vertex(0 + x, 0 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 0 + y, 0 + z);
			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 0 + y, 1 + z);

			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 0 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 0 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV2());
			modelBuilder.vertex(1 + x, 0 + y, 1 + z);
		}
		
		if ((y+1 < Chunk.CHUNK_WIDTH && data[x][y+1][z] == 0) || y+1 >= Chunk.CHUNK_WIDTH && chunkHandler.getBlock(chunkX + x, chunkY + y + 1, chunkZ + z) == 0) {
			modelBuilder.normal(0, 1, 0);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 1 + y, 0 + z);
			modelBuilder.uv(texture.getU1(), texture.getV1());
			modelBuilder.vertex(0 + x, 1 + y, 0 + z);
			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 1 + z);

			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 1 + y, 0 + z);
			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV2());
			modelBuilder.vertex(1 + x, 1 + y, 1 + z);
		}
		
	}

	public int getId() {
		return id;
	}

}
