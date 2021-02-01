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
		transform.position = new Vector3f(-24, 32, -24);
		transform.rotation.x = (float) Math.toDegrees(Math.atan(1d/Math.sqrt(2)));
		transform.rotation.y = 135;
//		transform.rotation.z = 45;
		addComponent(new InputComponent(this));
	}

}
