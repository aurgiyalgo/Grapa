package com.aurgiyalgo.Grapa.graphics.shaders;

import org.joml.Matrix4f;

import com.aurgiyalgo.Grapa.Grapa;
import com.aurgiyalgo.Grapa.graphics.generic.Camera;
import com.aurgiyalgo.Grapa.graphics.generic.Light;
import com.aurgiyalgo.Grapa.utils.GrapaMaths;

/**
 * Temporary shader program to test new engine functions.
 */
public class StaticShader extends Shader {
	
	private static final String VERTEX_FILE = "src/com/aurgiyalgo/Grapa/graphics/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/com/aurgiyalgo/Grapa/graphics/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColor;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(Grapa.POSITION_VERTEX_ATTRIB_INDEX, "position");
		super.bindAttribute(Grapa.TEXTURE_VERTEX_ATTRIB_INDEX, "textureCoords");
		super.bindAttribute(Grapa.NORMALS_VERTEX_ATTRIB_INDEX, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColor = super.getUniformLocation("lightColor");
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix) {
		super.loadMatrix(location_transformationMatrix, transformationMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = GrapaMaths.createViewMatrix(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z, camera.getRx(), camera.getRy());
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadLight(Light light) {
		super.loadVector3f(location_lightPosition, light.getPosition());
		super.loadVector3f(location_lightColor, light.getColor());
	}

}
