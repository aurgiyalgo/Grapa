package com.aurgiyalgo.Grapa;

import org.lwjgl.glfw.GLFW;

import com.aurgiyalgo.Grapa.graphics.display.DisplayManager;
import com.aurgiyalgo.Grapa.graphics.textures.Texture;
import com.aurgiyalgo.Grapa.input.Input;
import com.aurgiyalgo.Grapa.scenes.IScene;

public class Grapa {

	public static final int POSITION_VERTEX_ATTRIB_INDEX = 0;
	public static final int TEXTURE_VERTEX_ATTRIB_INDEX = 1;
	public static final int NORMALS_VERTEX_ATTRIB_INDEX = 2;

	public static Texture TEXTURE;

	private DisplayManager displayManager;
	
	private Input input;
	
	private IScene currentScene;
	
	public Grapa() {
		setup();
	}

	/**
	 * Initializes the engine and the sub-systems.
	 */
	private void setup() {
		displayManager = DisplayManager.getInstance();
		displayManager.createDisplay(1280, 720);
		displayManager.setClearColor(0.75f, 0.5f, 0.5f, 1);

		displayManager.setOnResize(new Runnable() {

			@Override
			public void run() {
				currentScene.onResize();
			}

		});

		//TODO Dynamic texture loading
		TEXTURE = Texture.loadTexture("resources/textures/tileset.png");
		
		input = Input.getInstance();

//		GLFW.glfwSetMouseButtonCallback(DisplayManager.getWindowId(), new GLFWMouseButtonCallbackI() {
//
//			@Override
//			public void invoke(long window, int button, int action, int mods) {
//				if (action == GLFW.GLFW_PRESS) worldEngine.onClick(button);
//			}
//
//		});
	}

	/**
	 * Executes the main engine loop.
	 */
	public void init() {
		long lastTime = System.nanoTime();
		long time;
		double delta;

		while (!GLFW.glfwWindowShouldClose(DisplayManager.getWindowId())) {
			time = System.nanoTime();
			delta = (time - lastTime) / 1000000000d;
			lastTime = time;
//			System.out.println("Delta: " + delta / 1000 + "ms");
//			System.out.println("FPS: " + 1.0 / delta);
			
			GLFW.glfwSetWindowTitle(DisplayManager.getWindowId(), "Grapa Voxel Test (FPS: " + (int) (1.0 / delta) + ")");

			input.update();

			displayManager.clearDisplay();
			
			if (currentScene != null) currentScene.update(delta);

			displayManager.updateDisplay();
		}
		
		currentScene.onHide();
	}
	
	public void setScene(IScene scene) {
		if (currentScene != null) currentScene.onHide();
		this.currentScene = scene;
		currentScene.onShow();
	}

}
