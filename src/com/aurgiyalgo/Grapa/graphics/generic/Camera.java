package com.aurgiyalgo.Grapa.graphics.generic;

import org.joml.Vector3f;

import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.arch.generic.InputComponent;

/**
 * A simple camera class.
 */
public class Camera extends GameObject {
	
	public Camera() {
		super("Camera");
		transform.position = new Vector3f(4, 9.8f, 4);
		addComponent(new InputComponent(this));
	}

}
