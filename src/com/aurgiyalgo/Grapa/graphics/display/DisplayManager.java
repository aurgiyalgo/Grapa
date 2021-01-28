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
import org.lwjgl.opengl.GL30;

/**
 * Handles basic display and window functions.
 */
public class DisplayManager {

	private static int windowWidth = 1280, windowHeight = 720;

	public long windowId;
	
	private Runnable onWindowResize;

	/**
	 * Creates display and GL context.
	 */
	public void createDisplay() {
		if (!glfwInit())
			throw new IllegalStateException("Unable to initiate GLFW");

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
//		glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_FALSE);
		windowId = glfwCreateWindow(windowWidth, windowHeight, "Grapa Voxel Test", 0, 0);
		if (windowId == 0)
			throw new IllegalStateException("Window not created properly");

		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowId, (videoMode.width() - windowWidth) / 2, (videoMode.height() - windowHeight) / 2);

		glfwShowWindow(windowId);

		glfwMakeContextCurrent(windowId);
		
		GLFW.glfwSwapInterval(0);

		GL.createCapabilities();

		glfwSetWindowSizeCallback(windowId, new GLFWWindowSizeCallback() {

			@Override
			public void invoke(long windowsId, int width, int height) {
				windowWidth = width;
				windowHeight = height;
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
		glfwSwapBuffers(windowId);
		glfwPollEvents();
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
	
	/**
	 * @return Window width in pixels.
	 */
	public static int getWindowWidth() {
		return windowWidth;
	}

	/**
	 * @return Window height in pixels.
	 */
	public static int getWindowHeight() {
		return windowHeight;
	}

}
