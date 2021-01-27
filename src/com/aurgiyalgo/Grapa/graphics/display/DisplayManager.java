package com.aurgiyalgo.Grapa.graphics.display;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class DisplayManager {

	private static int windowWidth = 1280, _windowHeight = 720;

	public long windowId;
	
	private Runnable onWindowResize;

	// Create display and GL context
	public void createDisplay() {
		if (!glfwInit())
			throw new IllegalStateException("Unable to initiate GLFW");

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
//		glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_FALSE);
		windowId = glfwCreateWindow(windowWidth, _windowHeight, "Grapa Voxel Test", 0, 0);
		if (windowId == 0)
			throw new IllegalStateException("Window not created properly");

		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowId, (videoMode.width() - windowWidth) / 2, (videoMode.height() - _windowHeight) / 2);

		glfwShowWindow(windowId);

		glfwMakeContextCurrent(windowId);
		
		GLFW.glfwSwapInterval(0);

		GL.createCapabilities();

		glfwSetWindowSizeCallback(windowId, new GLFWWindowSizeCallback() {

			@Override
			public void invoke(long windowsId, int width, int height) {
				windowWidth = width;
				_windowHeight = height;
				onWindowResize.run();
				GL11.glViewport(0, 0, width, height);
			}

		});
	}

	// Update GLFW events (called at the end of every frame)
	public void updateDisplay() {
		glfwSwapBuffers(windowId);
		glfwPollEvents();
	}

	// Dispose window when program ends
	public void dispose() {

	}
	
	public void setOnResize(Runnable onResize) {
		this.onWindowResize = onResize;
	}

	public static int getWindowWidth() {
		return windowWidth;
	}

	public static int getWindowHeight() {
		return _windowHeight;
	}

}
