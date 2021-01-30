package com.aurgiyalgo.Grapa.arch.generic;

import org.lwjgl.glfw.GLFW;

import com.aurgiyalgo.Grapa.arch.Component;
import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.input.Input;

/**
 * Basic component to move a GameObject with the WASD keys.
 */
public class InputComponent extends Component {

	public InputComponent(GameObject object) {
		super(object);
	}

	@Override
	public void update(double delta) {
		if (Input.getKey(GLFW.GLFW_KEY_W)) {
			gameObject.transform.position.z -= 0.01f * delta;
		}
		if (Input.getKey(GLFW.GLFW_KEY_S)) {
			gameObject.transform.position.z += 0.01f * delta;
		}
		if (Input.getKey(GLFW.GLFW_KEY_A)) {
			gameObject.transform.position.x -= 0.01f * delta;
		}
		if (Input.getKey(GLFW.GLFW_KEY_D)) {
			gameObject.transform.position.x += 0.01f * delta;
		}
	}

}
