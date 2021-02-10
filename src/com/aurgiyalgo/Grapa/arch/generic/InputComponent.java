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
		// TODO Temporary code
		if (Input.getKey(GLFW.GLFW_KEY_W)) {
			gameObject.transform.position.z -= 5f * delta * Math.cos(Math.toRadians(gameObject.transform.rotation.y));
			gameObject.transform.position.x += 5f * delta * Math.sin(Math.toRadians(gameObject.transform.rotation.y));
		}
		if (Input.getKey(GLFW.GLFW_KEY_S)) {
			gameObject.transform.position.z += 5f * delta * Math.cos(Math.toRadians(gameObject.transform.rotation.y));
			gameObject.transform.position.x -= 5f * delta * Math.sin(Math.toRadians(gameObject.transform.rotation.y));
		}
		if (Input.getKey(GLFW.GLFW_KEY_A)) {
			gameObject.transform.position.z -= 5f * delta * Math.sin(Math.toRadians(gameObject.transform.rotation.y));
			gameObject.transform.position.x -= 5f * delta * Math.cos(Math.toRadians(gameObject.transform.rotation.y));
		}
		if (Input.getKey(GLFW.GLFW_KEY_D)) {
			gameObject.transform.position.z += 5f * delta * Math.sin(Math.toRadians(gameObject.transform.rotation.y));
			gameObject.transform.position.x += 5f * delta * Math.cos(Math.toRadians(gameObject.transform.rotation.y));
		}
		if (Input.getKey(GLFW.GLFW_KEY_SPACE)) {
			gameObject.transform.position.y += 5f * delta;
		}
		if (Input.getKey(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			gameObject.transform.position.y -= 5 * delta;
		}
		gameObject.transform.rotation.x += Input.getDeltaMouseY() * 10f * delta;
		gameObject.transform.rotation.y += Input.getDeltaMouseX() * 10f * delta;
	}

}
