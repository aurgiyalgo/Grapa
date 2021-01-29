package com.aurgiyalgo.Grapa.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import com.aurgiyalgo.Grapa.arch.Transform;

/**
 * Utility math class for graphics.
 */
public class GrapaMaths {
	
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(translation);
		matrix.rotate((float)Math.toRadians(rx), new Vector3f(1, 0, 0));
		matrix.rotate((float)Math.toRadians(ry), new Vector3f(0, 1, 0));
		matrix.rotate((float)Math.toRadians(rz), new Vector3f(0, 0, 1));
		matrix.scale(scale);
		return matrix;
	}
	
	public static Matrix4f createTransformationMatrix(Vector3f translation) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(translation);
		return matrix;
	}
	
	public static Matrix4f createTransformationMatrix(Vector3i translation) {
		Vector3f newTranslation = new Vector3f(translation.x, translation.y, translation.z);
		
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(newTranslation);
		return matrix;
	}

	public static Matrix4f createViewMatrix(float x, float y, float z, float rx, float ry, float rz) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.rotate((float) Math.toRadians(rx), 1, 0, 0);
		matrix.rotate((float) Math.toRadians(ry), 0, 1, 0);
		matrix.translate(-x, -y, -z);
		return matrix;
	}

	public static Matrix4f createViewMatrix(Transform transform) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.rotate((float) Math.toRadians(transform.rotation.x), 1, 0, 0);
		matrix.rotate((float) Math.toRadians(transform.rotation.y), 0, 1, 0);
		matrix.translate(-transform.position.x, -transform.position.y, -transform.position.z);
		return matrix;
	}

}
