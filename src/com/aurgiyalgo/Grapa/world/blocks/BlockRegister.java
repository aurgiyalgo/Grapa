package com.aurgiyalgo.Grapa.world.blocks;

import java.util.ArrayList;
import java.util.List;

import com.aurgiyalgo.Grapa.world.blocks.materials.Air;
import com.aurgiyalgo.Grapa.world.blocks.materials.BaseRock;
import com.aurgiyalgo.Grapa.world.blocks.materials.Brick;
import com.aurgiyalgo.Grapa.world.blocks.materials.Dirt;
import com.aurgiyalgo.Grapa.world.blocks.materials.Stone;

public class BlockRegister {
	
	private static List<Block> BLOCKS;
	
	static {
		BLOCKS = new ArrayList<Block>();
		BLOCKS.add(new Air());
		BLOCKS.add(new Dirt());
		BLOCKS.add(new BaseRock());
		BLOCKS.add(new Stone());
		BLOCKS.add(new Brick());
	}
	
	public static Block getBlock(int id) {
		for (int i = 0; i < BLOCKS.size(); i++) {
			if (BLOCKS.get(i).getId() == id) return BLOCKS.get(i);
		}
		return null;
	}

}
