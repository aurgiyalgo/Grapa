package com.aurgiyalgo.Grapa.graphics.generic;

import org.joml.Vector2i;
import org.joml.Vector3f;

/**
 * Class to hold basic vertex data.
 */
@Deprecated
public class Vertex {
	
	public Vector3f position;
	public Vector2i uv;
	public Vector3f normals;
	
	public Vertex(Vector3f position, Vector2i uv, Vector3f normals) {
		this.position = position;
		this.uv = uv;
		this.normals = normals;
	}

}
