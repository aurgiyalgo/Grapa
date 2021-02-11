package com.aurgiyalgo.Grapa;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.opengl.GL30;

import com.aurgiyalgo.Grapa.graphics.display.DisplayManager;
import com.aurgiyalgo.Grapa.graphics.textures.Texture;
import com.aurgiyalgo.Grapa.input.Input;
import com.aurgiyalgo.Grapa.ui.UIEngine;
import com.aurgiyalgo.Grapa.world.WorldEngine;

public class Grapa {

	public static final int POSITION_VERTEX_ATTRIB_INDEX = 0;
	public static final int TEXTURE_VERTEX_ATTRIB_INDEX = 1;
	public static final int NORMALS_VERTEX_ATTRIB_INDEX = 2;

	public static Texture TEXTURE;

	private DisplayManager displayManager;

	private UIEngine uiEngine;
	private WorldEngine worldEngine;

	public static void main(String[] args) {
		Grapa grapa = new Grapa();
		grapa.init();
		grapa.loop();
	}

	/**
	 * Initializes the engine and the sub-systems.
	 */
	private void init() {
		displayManager = new DisplayManager();
		displayManager.createDisplay(1280, 720);
		displayManager.setClearColor(0.75f, 0.5f, 0.5f, 1);

		displayManager.setOnResize(new Runnable() {

			@Override
			public void run() {
				System.out.println("Window was resized!");
				worldEngine.updateProjectionMatrix();
			}

		});

		TEXTURE = Texture.loadTexture("resources/textures/tileset.png");

		Input.createInstance(displayManager.windowId);

		uiEngine = new UIEngine();

		worldEngine = new WorldEngine();

		GLFW.glfwSetMouseButtonCallback(displayManager.windowId, new GLFWMouseButtonCallbackI() {

			@Override
			public void invoke(long window, int button, int action, int mods) {
				if (action == GLFW.GLFW_PRESS) worldEngine.onClick(button);
			}

		});
	}

	/**
	 * Executes the main engine loop.
	 */
	private void loop() {
		long lastTime = System.nanoTime();
		GL30.glEnable(GL30.GL_DEPTH_TEST);
		
		GL30.glEnable(GL30.GL_CULL_FACE);
		GL30.glCullFace(GL30.GL_FRONT_FACE);
		
		Input.hideCursor();

		while (!GLFW.glfwWindowShouldClose(displayManager.windowId)) {
			long time = System.nanoTime();
			double delta = (time - lastTime) / 1000000000d;
			lastTime = time;
			System.out.println("Delta: " + delta / 1000 + "ms");
			System.out.println("FPS: " + 1.0 / delta);

			Input.update();

			displayManager.clearDisplay();

			worldEngine.update(delta);
			worldEngine.updateChunkMeshes();

			uiEngine.update(delta);

			displayManager.updateDisplay();
			
			if (Input.getKey(GLFW.GLFW_KEY_ESCAPE)) Input.showCursor();
		}
	}

}
