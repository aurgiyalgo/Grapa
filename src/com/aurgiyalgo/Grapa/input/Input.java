package com.aurgiyalgo.Grapa.input;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import com.aurgiyalgo.Grapa.graphics.display.DisplayManager;

import lombok.Getter;

/**
 * Handles and allows to get user input (temporary).
 */
public class Input {

	private double lastMouseX = 0, lastMouseY = 0;
	private double deltaMouseX = 0, deltaMouseY = 0;
	private boolean[] keys;
	private boolean[] mouse;
	private long windowId;
	
	@Getter
	private boolean isCursorHidden;
	
	private static Input instance;
	
	private Input(long windowId) {
		
		this.windowId = windowId;
		this.keys = new boolean[349];
		this.mouse = new boolean[5];
		
		GLFW.glfwSetKeyCallback(windowId, new GLFWKeyCallbackI() {
			
			@Override
			public void invoke(long window, int key, int arg2, int action, int arg4) {
				if(key != GLFW.GLFW_KEY_UNKNOWN) {
					keys[key] = (action != GLFW.GLFW_RELEASE);
				}
			}
		});
		
		GLFW.glfwSetMouseButtonCallback(windowId, new GLFWMouseButtonCallbackI() {
			
			@Override
			public void invoke(long window, int button, int action, int mods) {
				mouse[button] = (action == GLFW.GLFW_PRESS);
			}
			
		});
	}
	
	public static Input getInstance() {
		if (instance == null) {
			instance = new Input(DisplayManager.getWindowId());
		}
		return instance;
	}
	
	public void update() {
		double[] mouseX = new double[1];
		double[] mouseY = new double[1];
		GLFW.glfwGetCursorPos(windowId, mouseX, mouseY);
		mouseX[0] /= 2;
		mouseY[0] /= 2;
		deltaMouseX = mouseX[0] - lastMouseX;
		deltaMouseY = mouseY[0] - lastMouseY;
		lastMouseX = mouseX[0];
		lastMouseY = mouseY[0];
	}
	
	public Vector2d getMousePosition() {
		double[] mouseX = new double[1];
		double[] mouseY = new double[1];
		GLFW.glfwGetCursorPos(windowId, mouseX, mouseY);
		return new Vector2d(mouseX[0], mouseY[0]);
	}
	
	public double getDeltaMouseX() {
		if (!instance.isCursorHidden) return 0;
		return instance.deltaMouseX;
	}
	
	public double getDeltaMouseY() {
		if (!instance.isCursorHidden) return 0;
		return instance.deltaMouseY;
	}
	
	public boolean getKey(int key) {
		return keys[key];
	}
	
	public void toggleCursor() {
		if (isCursorHidden) {
			showCursor();
			return;
		}
		hideCursor();
	}
	
	public void hideCursor() {
		isCursorHidden = true;
		GLFW.glfwSetInputMode(instance.windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	public void showCursor() {
		isCursorHidden = false;
		GLFW.glfwSetInputMode(instance.windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
	}
	
}
