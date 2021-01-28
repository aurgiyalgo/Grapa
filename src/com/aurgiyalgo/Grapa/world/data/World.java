package com.aurgiyalgo.Grapa.world.data;

import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.world.components.ChunkHandler;
import com.aurgiyalgo.Grapa.world.components.ChunkRenderer;

public class World extends GameObject {

	public World() {
		super("World");
		
		addComponent(new ChunkHandler(this));
		addComponent(new ChunkRenderer(this));
	}

}
