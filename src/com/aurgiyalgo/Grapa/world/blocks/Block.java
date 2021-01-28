package com.aurgiyalgo.Grapa.world.blocks;

import com.aurgiyalgo.Grapa.graphics.model.ModelBuilder;
import com.aurgiyalgo.Grapa.graphics.textures.TextureRegion;

public abstract class Block {
	
	private int id;

	protected TextureRegion texture;
	
	public Block(int id) {
		this.id = id;
		texture = new TextureRegion(2);
	}
	
	public void createModel(ModelBuilder modelBuilder, int x, int y, int z, boolean drawLeft, boolean drawUp, boolean drawRight) {
		
		if (drawLeft) {
			modelBuilder.normal(-1, 0, 0);
			modelBuilder.uv(texture.getU1(), texture.getV1());
			modelBuilder.vertex(0 + x, 0 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 0 + y, 0 + z);
			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 0 + z);

			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 0 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV2());
			modelBuilder.vertex(1 + x, 1 + y, 0 + z);
		}
		if (drawUp) {
			modelBuilder.normal(0, 0, -1);
			modelBuilder.uv(texture.getU1(), texture.getV1());
			modelBuilder.vertex(0 + x, 0 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(0 + x, 0+ y, 0 + z);
			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 1 + z);

			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(0 + x, 0 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 0 + z);
		}
		if (drawRight) {
			modelBuilder.normal(0, 1, 0);
			modelBuilder.uv(texture.getU1(), texture.getV1());
			modelBuilder.vertex(0 + x, 1 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 1 + y, 0 + z);
			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 1 + z);

			modelBuilder.uv(texture.getU1(), texture.getV2());
			modelBuilder.vertex(0 + x, 1 + y, 1 + z);
			modelBuilder.uv(texture.getU2(), texture.getV1());
			modelBuilder.vertex(1 + x, 1 + y, 0 + z);
			modelBuilder.uv(texture.getU2(), texture.getV2());
			modelBuilder.vertex(1 + x, 1 + y, 1 + z);
		}
		
	}

	public int getId() {
		return id;
	}

}
