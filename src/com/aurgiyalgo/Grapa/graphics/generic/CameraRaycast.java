package com.aurgiyalgo.Grapa.graphics.generic;

import org.joml.Vector3f;

import lombok.Getter;

@Deprecated
public class CameraRaycast {
	
	@Getter
	private Vector3f currentRay;
	private Camera camera;

	public CameraRaycast(Camera camera) {
		this.camera = camera;
		this.currentRay = new Vector3f();
		update();
	}

	/**
	 * Calculates the origin point and direction of the raycast
	 */
	public void update() {
		currentRay.x = (float) (Math.sin(Math.toRadians(camera.transform.rotation.y)) * Math.cos(Math.toRadians(camera.transform.rotation.x)));
		currentRay.y = (float) -Math.sin(Math.toRadians(camera.transform.rotation.x));
		currentRay.z = (float) -(Math.cos(Math.toRadians(camera.transform.rotation.y)) * Math.cos(Math.toRadians(camera.transform.rotation.x)));
		
		currentRay.normalize();
	}

}
