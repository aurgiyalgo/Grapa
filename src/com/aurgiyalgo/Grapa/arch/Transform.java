package com.aurgiyalgo.Grapa.arch;

import org.joml.Vector3f;

public class Transform {
	
	public Vector3f position;
	public Vector3f rotation;
	public float scale;
	
	public Transform() {
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scale = 1f;
	}

	public Transform(Vector3f position, Vector3f rotation, float scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

}
