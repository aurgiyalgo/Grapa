package com.aurgiyalgo.Grapa.world.blocks.materials;

import com.aurgiyalgo.Grapa.graphics.model.ModelBuilder;
import com.aurgiyalgo.Grapa.world.blocks.Block;
import com.aurgiyalgo.Grapa.world.data.Chunk;

public class Air extends Block {

	public Air() {
		super(0);
	}
	
	@Override
	public void createModel(ModelBuilder modelBuilder, int x, int y, int z, int[][][] data, Chunk chunk) {}
	
}
