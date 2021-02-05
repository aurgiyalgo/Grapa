package com.aurgiyalgo.Grapa.graphics.generic;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.aurgiyalgo.Grapa.graphics.display.DisplayManager;
import com.aurgiyalgo.Grapa.input.Input;
import com.aurgiyalgo.Grapa.utils.GrapaMaths;
import com.aurgiyalgo.Grapa.world.components.ChunkRenderer;

import lombok.Getter;

public class CameraRaycast {

	private Vector3f currentRay;
	@Getter
	private Vector3f startPosition;

	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Camera camera;

	public CameraRaycast(Camera camera, Matrix4f projectionMatrix) {
		this.camera = camera;
		this.projectionMatrix = projectionMatrix;
		this.viewMatrix = GrapaMaths.createViewMatrix(camera.transform);
	}

	public Vector3f getCurrentRay() {
		return currentRay;
	}

	public void update() {
		viewMatrix = GrapaMaths.createViewMatrix(camera.transform);
		currentRay = calculateRay();
//		System.out.println(currentRay);
	}

	private Vector3f calculateRay() {
		float mouseX = (float) Input.getMousePosition().x;
		float mouseY = (float) Input.getMousePosition().y;
		Vector2f screenCoords = getNormalizedScreenCoords(mouseX, mouseY);
//		Vector2f rotatedScreenCoords = new Vector2f();
//		float rotation = camera.transform.rotation.z;
//		rotatedScreenCoords.x = (float) (Math.cos(Math.toRadians(rotation)) * screenCoords.x - Math.sin(Math.toRadians(rotation)) * screenCoords.y);
//		rotatedScreenCoords.y = (float) (Math.sin(Math.toRadians(rotation)) * screenCoords.x + Math.cos(Math.toRadians(rotation)) * screenCoords.y);
////		System.out.println(screenCoords);
////		System.out.println(rotatedScreenCoords);
		
		startPosition = new Vector3f();
		startPosition.x = (float) (camera.transform.position.x + screenCoords.x * Math.cos(Math.toRadians(camera.transform.rotation.y)) * ChunkRenderer.SCREEN_LIMIT/2);
		startPosition.y = (float) (camera.transform.position.y - screenCoords.y * Math.sin(Math.toRadians(camera.transform.rotation.x)) * ChunkRenderer.SCREEN_LIMIT/2);
		startPosition.z = (float) (camera.transform.position.z + screenCoords.x * Math.sin(Math.toRadians(camera.transform.rotation.y)) * ChunkRenderer.SCREEN_LIMIT/2);
//		startPosition = camera.transform.position;
//		startPosition = new Vector3f(3f, 12, 3f);
		
//		System.out.println("Cam pos: " + camera.transform.position + " | Point pos: " + startPosition);
		
//		Vector3f direction = new Vector3f();
//		direction.x = (float) (Math.cos(Math.toRadians(camera.transform.rotation.y)) * Math.cos(Math.toRadians(camera.transform.rotation.x)));
//		direction.y = (float) Math.sin(Math.toRadians(camera.transform.rotation.x));
//		direction.z = (float) (Math.sin(Math.toRadians(camera.transform.rotation.y)) * Math.cos(Math.toRadians(camera.transform.rotation.x)));
//		
//		direction.normalize();
		
//		direction = new Vector3f(0, -1, 0);
		
//		return direction;
		Vector4f clipCoords = new Vector4f(0, 0, -1f, 1f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}

	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = new Matrix4f(viewMatrix).invert();
		Vector4f rayWorld = invertedView.transform(eyeCoords);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalize();
		return mouseRay;
	}

	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = new Matrix4f();
		projectionMatrix.invert(invertedProjection);
		Vector4f eyeCoords = projectionMatrix.transform(clipCoords);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	private Vector2f getNormalizedScreenCoords(float mouseX, float mouseY) {
		float x = (float) ((2.0f * mouseX) / DisplayManager.getWindowWidth() - 1.0f);
		float y = (float) ((2.0f * mouseY) / DisplayManager.getWindowHeight() - 1.0f);
//		System.out.println(new Vector2f(x, y));
		return new Vector2f(x, y);
	}

}
