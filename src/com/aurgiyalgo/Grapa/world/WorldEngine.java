package com.aurgiyalgo.Grapa.world;

import com.aurgiyalgo.Grapa.arch.Engine;
import com.aurgiyalgo.Grapa.world.data.World;

public class WorldEngine extends Engine {
	
	public WorldEngine() {
		addGameObject(new World());
	}

}
