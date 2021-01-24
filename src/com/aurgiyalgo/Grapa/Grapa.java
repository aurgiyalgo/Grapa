package com.aurgiyalgo.Grapa;

import org.lwjgl.glfw.GLFW;

import com.aurgiyalgo.Grapa.arch.Engine;
import com.aurgiyalgo.Grapa.graphics.display.DisplayManager;
import com.aurgiyalgo.Grapa.ui.Button;
import com.aurgiyalgo.Grapa.ui.Text;
import com.aurgiyalgo.Grapa.ui.UIEngine;

public class Grapa {
	
	private DisplayManager displayManager;
	
	private Engine uiEngine;
	
	public static void main(String[] args) {
		Grapa grapa = new Grapa();
		grapa.init();
		grapa.loop();
	}
	
	private void init() {
		displayManager = new DisplayManager();
		displayManager.createDisplay();
		
		uiEngine = new UIEngine();
		Button button = new Button();
		Text text = new Text();
		uiEngine.addGameObject(button);
		uiEngine.addGameObject(text);
	}
	
	private void loop() {
		
		while (!GLFW.glfwWindowShouldClose(displayManager.windowId)) {
			uiEngine.update(0);
			
			displayManager.updateDisplay();
		}
	}

}
