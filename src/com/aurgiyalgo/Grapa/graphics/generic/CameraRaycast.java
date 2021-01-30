package com.aurgiyalgo.Grapa.graphics.generic;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.aurgiyalgo.Grapa.graphics.display.DisplayManager;
import com.aurgiyalgo.Grapa.input.Input;
import com.aurgiyalgo.Grapa.utils.GrapaMaths;

public class CameraRaycast {

	private Vector3f currentRay;

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
		currentRay = calculateMathsRay();
		System.out.println(currentRay);
	}

	private Vector3f calculateMathsRay() {
		float mouseX = (float) Input.getMousePosition().x;
		float mouseY = (float) Input.getMousePosition().y;
		Vector2f normalizedCoords = getNormalizedScreenCoords(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
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
		System.out.println(new Vector2f(x, y));
		return new Vector2f(x, y);
	}

}
