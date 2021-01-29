package com.aurgiyalgo.Grapa.arch.generic;

import org.lwjgl.glfw.GLFW;

import com.aurgiyalgo.Grapa.arch.Component;
import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.input.Input;

public class InputComponent extends Component {

	public InputComponent(GameObject object) {
		super(object);
	}

	@Override
	public void update(double delta) {
		if (Input.getKey(GLFW.GLFW_KEY_W)) {
			gameObject.transform.position.y -= 0.01f;
		}
		if (Input.getKey(GLFW.GLFW_KEY_S)) {
			gameObject.transform.position.y += 0.01f;
		}
		if (Input.getKey(GLFW.GLFW_KEY_A)) {
			gameObject.transform.position.x -= 0.01f;
		}
		if (Input.getKey(GLFW.GLFW_KEY_D)) {
			gameObject.transform.position.x += 0.01f;
		}
		gameObject.transform.rotation.x += (float) Input.getDeltaMouseX() * 0.005f;
		gameObject.transform.rotation.y += (float) Input.getDeltaMouseY() * 0.005f;
		System.out.println(gameObject.transform.position);
	}

}
