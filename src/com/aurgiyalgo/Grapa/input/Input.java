package com.aurgiyalgo.Grapa.input;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

/**
 * Handles and allows to get user input (temporary).
 */
public class Input {

	private double lastMouseX = 0, lastMouseY = 0;
	private double deltaMouseX = 0, deltaMouseY = 0;
	private boolean[] keys;
	private long window;
	
	private static Input instance;
	
	private Input(long window) {
		
		this.window = window;
		keys = new boolean[349];
		
		GLFW.glfwSetKeyCallback(window, new GLFWKeyCallbackI() {
			
			@Override
			public void invoke(long window, int key, int arg2, int action, int arg4) {
				if(key != GLFW.GLFW_KEY_UNKNOWN) {
					keys[key] = (action != GLFW.GLFW_RELEASE);
				}
			}
		});
	}
	
	public static void createInstance(long window) {
		instance = new Input(window);
	}
	
	public static void update() {
		double[] mouseX = new double[1];
		double[] mouseY = new double[1];
		GLFW.glfwGetCursorPos(instance.window, mouseX, mouseY);
		mouseX[0] /= 2;
		mouseY[0] /= 2;
		instance.deltaMouseX = mouseX[0] - instance.lastMouseX;
		instance.deltaMouseY = mouseY[0] - instance.lastMouseY;
		instance.lastMouseX = mouseX[0];
		instance.lastMouseY = mouseY[0];
	}
	
	public static double getDeltaMouseX() {
		return instance.deltaMouseX;
	}
	
	public static double getDeltaMouseY() {
		return instance.deltaMouseY;
	}
	
	public static boolean getKey(int key) {
		return instance.keys[key];
	}
	
	public static Vector2f getRawInput() {
		Vector2f input = new Vector2f();
		if (getKey(GLFW.GLFW_KEY_W)) input.y++;
		if (getKey(GLFW.GLFW_KEY_S)) input.y--;
		if (getKey(GLFW.GLFW_KEY_A)) input.x--;
		if (getKey(GLFW.GLFW_KEY_D)) input.x++;
		return input;
	}
	
	public static void hideCursor() {
		GLFW.glfwSetInputMode(instance.window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
}
