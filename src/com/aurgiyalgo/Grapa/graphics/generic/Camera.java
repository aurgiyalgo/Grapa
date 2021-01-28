package com.aurgiyalgo.Grapa.graphics.generic;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.aurgiyalgo.Grapa.input.Input;

/**
 * A simple camera class (temporary).
 */
public class Camera {
	
	private Vector3f position;
	private float rx, ry, rz;
	
	public Camera() {
		position = new Vector3f(-24, 24, -24);
		this.rx = 30;
		this.ry = 135;
		this.rz = 0;
	}
	
	public void move() {
		if (Input.getKey(GLFW.GLFW_KEY_W)) {
			position.z -= 0.01f;
		}
		if (Input.getKey(GLFW.GLFW_KEY_S)) {
			position.z += 0.01f;
		}
		if (Input.getKey(GLFW.GLFW_KEY_A)) {
			position.x -= 0.01f;
		}
		if (Input.getKey(GLFW.GLFW_KEY_D)) {
			position.x += 0.01f;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRx() {
		return rx;
	}

	public float getRy() {
		return ry;
	}

	public float getRz() {
		return rz;
	}

}
