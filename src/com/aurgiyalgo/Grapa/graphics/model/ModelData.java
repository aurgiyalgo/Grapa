package com.aurgiyalgo.Grapa.graphics.model;

/**
 * Basic data to load a render a model from GPU.
 */
public class ModelData {
	
	private int vaoID;
	private int vertexCount;
//	private int textureID;
	
	public ModelData(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
//	public void setTextureID(int textureID) {
//		this.textureID = textureID;
//	}

	public int getVaoId() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
//	public int getTextureID() {
//		return textureID;
//	}

}
