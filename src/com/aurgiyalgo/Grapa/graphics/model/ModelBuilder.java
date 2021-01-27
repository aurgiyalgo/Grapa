package com.aurgiyalgo.Grapa.graphics.model;

import java.util.ArrayList;
import java.util.List;

public class ModelBuilder {
	
	private List<Float> positions;
	private List<Float> textureCoords;
	private List<Float> normals;
	
	private float[] currentUv;
	private float[] currentNormal;
	
	public ModelBuilder() {
		positions = new ArrayList<Float>();
		textureCoords = new ArrayList<Float>();
		normals = new ArrayList<Float>();
		currentUv = new float[2];
		currentNormal = new float[3];
	}
	
	public void uv(float u, float v) {
		currentUv[0] = u;
		currentUv[1] = v;
	}
	
	public void normal(float i, float j, float k) {
		currentNormal[0] = i;
		currentNormal[1] = j;
		currentNormal[2] = k;
	}
	
	public void vertex(float x, float y, float z) {
		positions.add(x);
		positions.add(y);
		positions.add(z);
		
		textureCoords.add(currentUv[0]);
		textureCoords.add(currentUv[1]);
		
		normals.add(currentNormal[0]);
		normals.add(currentNormal[1]);
		normals.add(currentNormal[2]);
	}
	
	public ModelData getModelData() {
		float[] positionsArray = new float[positions.size()];
		for (int i = 0; i < positions.size(); i++) {
			positionsArray[i] = positions.get(i);
		}
		float[] texturesArray = new float[textureCoords.size()];
		for (int i = 0; i < textureCoords.size(); i++) {
			texturesArray[i] = textureCoords.get(i);
		}
		float[] normalsArray = new float[normals.size()];
		for (int i = 0; i < normals.size(); i++) {
			normalsArray[i] = normals.get(i);
		}
		ModelData data = ModelLoader.loadModelFromData(positionsArray, texturesArray, normalsArray);
		return data;
	}
	
}
