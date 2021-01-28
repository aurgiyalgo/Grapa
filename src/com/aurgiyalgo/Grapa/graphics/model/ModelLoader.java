package com.aurgiyalgo.Grapa.graphics.model;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL30;

import com.aurgiyalgo.Grapa.Grapa;

/**
 * Utility class to load model data to the GPU. Will be replaced by more flexible classes.
 */
@Deprecated
public class ModelLoader {
	
	private static List<Integer> vaos = new ArrayList<Integer>();
	private static List<Integer> vbos = new ArrayList<Integer>();
	private static List<Integer> textures = new ArrayList<Integer>();
	
	public static ModelData loadModelFromData(float[] positions, float[] textureCoords, float[] normals) {
		int vaoID = createVAO();
		storeDataInVBO(Grapa.POSITION_VERTEX_ATTRIB_INDEX, 3, positions);
		storeDataInVBO(Grapa.TEXTURE_VERTEX_ATTRIB_INDEX, 2, textureCoords);
		storeDataInVBO(Grapa.NORMALS_VERTEX_ATTRIB_INDEX, 1, normals);
		unbindVAO();
		return new ModelData(vaoID, positions.length / 3);
	}
	
	private static int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private static void storeDataInVBO(int attributeNumber, int coordsSize, float[] data) {
		int vboID = GL30.glGenBuffers();
		vbos.add(vboID);
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, data, GL30.GL_STATIC_DRAW);
		GL30.glVertexAttribPointer(attributeNumber, coordsSize, GL30.GL_FLOAT, false, 0, 0);
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
	}
	
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	public static void dispose() {
		for (int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo : vbos) {
			GL30.glDeleteBuffers(vbo);
		}
		for (int texture : textures) {
			GL30.glDeleteTextures(texture);
		}
	}

}
