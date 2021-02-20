package com.aurgiyalgo.Grapa.graphics.display;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import lombok.Getter;

/**
 * Handles basic display and window functions.
 */
public class DisplayManager {
	
	private static DisplayManager instance;

	@Getter
	private static long windowId;
	
	@Getter
	private static int width, height;
	
	private Runnable onWindowResize;
	
	private DisplayManager() {
		
	}
	
	public static DisplayManager getInstance() {
		if (instance == null) {
			instance = new DisplayManager();
		}
		return instance;
	}

	/**
	 * Creates display and GL context.
	 * @param width Window width in pixels
	 * @param height Window height in pixels
	 */
	public void createDisplay(int width, int height) {
		if (!GLFW.glfwInit())
			throw new IllegalStateException("Unable to initiate GLFW");
		
		DisplayManager.width = width;
		DisplayManager.height = height;

		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		windowId = GLFW.glfwCreateWindow(width, height, "Grapa Voxel Test", 0, 0);
		if (windowId == 0)
			throw new IllegalStateException("Window not created properly");

		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(windowId, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

		GLFW.glfwShowWindow(windowId);

		GLFW.glfwMakeContextCurrent(windowId);
		
		GLFW.glfwSwapInterval(0);

		GL.createCapabilities();

		GLFW.glfwSetWindowSizeCallback(windowId, new GLFWWindowSizeCallback() {

			@Override
			public void invoke(long windowsId, int width, int height) {
				DisplayManager.width = width;
				DisplayManager.height = height;
				onWindowResize.run();
				GL11.glViewport(0, 0, width, height);
			}

		});
	}

	/**
	 * Update GLFW events.
	 * To be called at the end of every frame.
	 */
	public void updateDisplay() {
		GLFW.glfwSwapBuffers(windowId);
		GLFW.glfwPollEvents();
	}

	/**
	 * Clears memory when program ends.
	 */
	public void dispose() {
		
	}
	
	/**
	 * Clears the display with the color specified in {@link DisplayManager#setClearColor(float, float, float, float)}.
	 * To be called at the beginning of every frame.
	 */
	public void clearDisplay() {
		GL30.glClear(GL30.GL_COLOR_BUFFER_BIT|GL30.GL_DEPTH_BUFFER_BIT);
	}
	
	/**
	 * Sets the color to clear the display with.
	 * @param r Red channel value
	 * @param g Green channel value
	 * @param b Blue channel value
	 * @param a Alpha channel value
	 */
	public void setClearColor(float r, float g, float b, float a) {
		GL30.glClearColor(r, g, b, a);
	}
	
	/**
	 * Sets a {@link Runnable#run()} to be run when the window is resized.
	 * @param onResize
	 */
	public void setOnResize(Runnable onResize) {
		this.onWindowResize = onResize;
	}

}
