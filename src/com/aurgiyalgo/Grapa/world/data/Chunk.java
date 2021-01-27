package com.aurgiyalgo.Grapa.world.data;

import org.joml.Vector3i;

import com.aurgiyalgo.Grapa.arch.Transform;
import com.aurgiyalgo.Grapa.graphics.model.Model;
import com.aurgiyalgo.Grapa.graphics.model.ModelData;
import com.aurgiyalgo.Grapa.graphics.model.ModelLoader;

import lombok.Getter;

public class Chunk {
	
	public static final int CHUNK_WIDTH = 8;
	
	@Getter private Model model;
	private int[] data;
	@Getter private Vector3i position;
	
	public Chunk(Vector3i position) {
		this.position = position;
		this.data = new int[CHUNK_WIDTH * CHUNK_WIDTH * CHUNK_WIDTH];
		
		float[] positions = new float[] {
				0.5f, 0.5f, 0f,
				0.5f, -0.5f, 0f,
				-0.5f, 0.5f, 0f,
				-0.5f, 0.5f, 0f,
				0.5f, -0.5f, 0f,
				-0.5f, -0.5f, 0f
		};
		float[] textureCoords = new float[] {
				1f, 1f,
				1f, 0f,
				0f, 1f,
				0f, 1f,
				1f, 0f,
				0f, 0f
		};
		float[] normals = new float[] {
				0f, 0f, 1f,
				0f, 0f, 1f,
				0f, 0f, 1f
		};
		
		ModelData modelData = ModelLoader.loadModelFromData(positions, textureCoords, normals);
		model = new Model(modelData, new Transform());
		
		for (int i = 0; i < 8*8*8; i++) {
			data[i] = 1;
		}
	}

}
