package com.aurgiyalgo.Grapa.world;

import com.aurgiyalgo.Grapa.arch.Engine;
import com.aurgiyalgo.Grapa.graphics.generic.Camera;
import com.aurgiyalgo.Grapa.world.data.World;

public class WorldEngine extends Engine {
	
	public WorldEngine() {
		Camera camera = new Camera();
		addGameObject(camera);
		addGameObject(new World(camera));
	}

}
