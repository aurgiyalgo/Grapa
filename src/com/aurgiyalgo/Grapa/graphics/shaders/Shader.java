package com.aurgiyalgo.Grapa.graphics.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import jdk.internal.org.jline.utils.InputStreamReader;

public abstract class Shader {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public Shader(String vertexFile, String fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL30.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL30.GL_FRAGMENT_SHADER);
		programID = GL30.glCreateProgram();
		GL30.glAttachShader(programID, vertexShaderID);
		GL30.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL30.glLinkProgram(programID);
		GL30.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	public Shader(String vertexFile, String fragmentFile, boolean isInternal) {
		vertexShaderID = loadShaderInternal(vertexFile, GL30.GL_VERTEX_SHADER);
		fragmentShaderID = loadShaderInternal(fragmentFile, GL30.GL_FRAGMENT_SHADER);
		programID = GL30.glCreateProgram();
		GL30.glAttachShader(programID, vertexShaderID);
		GL30.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL30.glLinkProgram(programID);
		GL30.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	public void start() {
		GL30.glUseProgram(programID);
	}
	
	public void stop() {
		GL30.glUseProgram(0);
	}
	
	public void dispose() {
		stop();
		GL30.glDetachShader(programID, vertexShaderID);
		GL30.glDetachShader(programID, fragmentShaderID);
		GL30.glDeleteShader(vertexShaderID);
		GL30.glDeleteShader(fragmentShaderID);
		GL30.glDeleteProgram(programID);
	}
	
	protected abstract void bindAttributes();
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName) {
		return GL30.glGetUniformLocation(programID, uniformName);
	}
	
	protected void bindAttribute(int attribute, String variableName) {
		GL30.glBindAttribLocation(programID, attribute, variableName);
	}
	
	protected void loadFloat(int location, float value) {
		GL30.glUniform1f(location, value);
	}
	
	protected void loadVector3f(int location, Vector3f vector) {
		GL30.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void loadBoolean(int location, boolean value) {
		GL30.glUniform1f(location, value ? 1 : 0);
	}
	
	protected void loadMatrix(int location, Matrix4f matrix) {
		GL30.glUniformMatrix4fv(location, false, matrix.get(matrixBuffer));
	}

	private static int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}

	private static int loadShaderInternal(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			InputStreamReader isr = new InputStreamReader(Class.class.getResourceAsStream(file));
			BufferedReader reader = new BufferedReader(isr);
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}

}
