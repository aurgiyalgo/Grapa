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
	
	private static final String VERTEX_FILE = "src/com/aurgiyalgo/Grapa/graphics/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/com/aurgiyalgo/Grapa/graphics/shaders/fragmentShader.glsl";
	
	private int locationTransformationMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	private int locationLightPosition;
	private int locationLightColor;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(Grapa.POSITION_VERTEX_ATTRIB_INDEX, "position");
		super.bindAttribute(Grapa.TEXTURE_VERTEX_ATTRIB_INDEX, "textureCoords");
		super.bindAttribute(Grapa.NORMALS_VERTEX_ATTRIB_INDEX, "normals");
	}

	@Override
	protected void getAllUniformLocations() {
		locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
		locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
		locationLightPosition = super.getUniformLocation("lightPosition");
		locationLightColor = super.getUniformLocation("lightColor");
	}
	
	public void loadTransformationMatrix(Matrix4f transformationMatrix) {
		super.loadMatrix(locationTransformationMatrix, transformationMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(locationProjectionMatrix, projectionMatrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = GrapaMaths.createViewMatrix(camera.transform);
		super.loadMatrix(locationViewMatrix, viewMatrix);
	}
	
	public void loadLight(Light light) {
		super.loadVector3f(locationLightPosition, light.getPosition());
		super.loadVector3f(locationLightColor, light.getColor());
	}

}
