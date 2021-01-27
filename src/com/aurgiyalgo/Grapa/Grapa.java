package com.aurgiyalgo.Grapa;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import com.aurgiyalgo.Grapa.arch.Engine;
import com.aurgiyalgo.Grapa.graphics.display.DisplayManager;
import com.aurgiyalgo.Grapa.graphics.textures.Texture;
import com.aurgiyalgo.Grapa.ui.UIEngine;
import com.aurgiyalgo.Grapa.world.WorldEngine;

public class Grapa {
	
	public static final int POSITION_VERTEX_ATTRIB_INDEX = 0;
	public static final int TEXTURE_VERTEX_ATTRIB_INDEX = 1;
	public static final int NORMALS_VERTEX_ATTRIB_INDEX = 2;
	
	public static Texture TEXTURE;
	
	private DisplayManager displayManager;
	
	private Engine uiEngine;
	private Engine worldEngine;
	
	public static void main(String[] args) {
		Grapa grapa = new Grapa();
		grapa.init();
		grapa.loop();
	}
	
	private void init() {
		displayManager = new DisplayManager();
		displayManager.createDisplay();
		displayManager.setClearColor(0.75f, 0.5f, 0.5f, 1);
		
		displayManager.setOnResize(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Window was resized!");
			}
			
		});
		
		TEXTURE = Texture.loadTexture("resources/res/BLOCKS.png");
		
		uiEngine = new UIEngine();
//		Button button = new Button();
//		Text text = new Text();
//		uiEngine.addGameObject(button);
//		uiEngine.addGameObject(text);
		
		worldEngine = new WorldEngine();
	}
	
	private void loop() {
		long lastTime = System.nanoTime();
		GL30.glClearColor(0.75f, 0.5f, 0.5f, 1);
		
		while (!GLFW.glfwWindowShouldClose(displayManager.windowId)) {
			long time = System.nanoTime();
			double delta = (time - lastTime) / 1000000000d;
			lastTime = time;
			System.out.println("Delta: " + delta + "s");
			System.out.println("FPS: " + 1.0 / delta);
			
			displayManager.clearDisplay();
			
			worldEngine.update(delta);
			
			uiEngine.update(delta);
			
			displayManager.updateDisplay();
		}
	}

}
