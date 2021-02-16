package com.aurgiyalgo.Grapa.graphics.generic;

import org.joml.Vector3f;

import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.arch.generic.InputComponent;

import lombok.Getter;
import lombok.Setter;

/**
 * A simple camera class.
 */
public class Camera extends GameObject {
	
	@Getter
	@Setter
	private Vector3f direction;
	
	public Camera() {
		super("Camera");
		transform.position = new Vector3f(4, 9.8f, 4);
		direction = new Vector3f();
		addComponent(new InputComponent(this));
	}
	
	@Override
	public void update(double delta) {
		updateComponents(delta);
		updateDirection();
	}
	
	public void updateDirection() {
		direction.x = (float) (Math.sin(Math.toRadians(transform.rotation.y)) * Math.cos(Math.toRadians(transform.rotation.x)));
		direction.y = (float) -Math.sin(Math.toRadians(transform.rotation.x));
		direction.z = (float) -(Math.cos(Math.toRadians(transform.rotation.y)) * Math.cos(Math.toRadians(transform.rotation.x)));
		
		direction.normalize();
	}

}
