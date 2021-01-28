package com.aurgiyalgo.Grapa.world.blocks;

import com.aurgiyalgo.Grapa.world.blocks.materials.Air;
import com.aurgiyalgo.Grapa.world.blocks.materials.Dirt;

public class BlockRegister {
	
	public static Block[] blocks = new Block[256];
	
	static {
		blocks[0] = new Air();
		blocks[1] = new Dirt();
//		blocks[2] = new Stone();
//		blocks[3] = new BaseRock();
	}
	
	public static Block getBlock(int id) {
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] == null) continue;
			if (blocks[i].getId() == id) return blocks[i];
		}
		return null;
	}

}
