package com.aurgiyalgo.Grapa.graphics.generic;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.aurgiyalgo.Grapa.input.Input;

public class Light {
	
	private Vector3f position;
	private Vector3f color;
	
	public Light(Vector3f position, Vector3f color) {
		super();
		this.position = position;
		this.color = color;
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
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public Vector3f getColor() {
		return color;
	}
	public void setColor(Vector3f color) {
		this.color = color;
	}

}
