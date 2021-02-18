package com.aurgiyalgo.Grapa.arch.generic;

import org.lwjgl.glfw.GLFW;

import com.aurgiyalgo.Grapa.arch.Component;
import com.aurgiyalgo.Grapa.arch.GameObject;
import com.aurgiyalgo.Grapa.input.Input;

/**
 * Basic component to move a GameObject with the WASD keys.
 */
public class InputComponent extends Component {
	
	private Input input;
	
	private static final float SPEED = 5f;
	private static final float SENSIVITY = 10f;

	public InputComponent(GameObject object) {
		super(object);
		input = Input.getInstance();
	}

	@Override
	public void update(double delta) {
		// TODO Temporary code
		if (input.getKey(GLFW.GLFW_KEY_W)) {
			gameObject.transform.position.z -= SPEED * delta * Math.cos(Math.toRadians(gameObject.transform.rotation.y));
			gameObject.transform.position.x += SPEED * delta * Math.sin(Math.toRadians(gameObject.transform.rotation.y));
		}
		if (input.getKey(GLFW.GLFW_KEY_S)) {
			gameObject.transform.position.z += SPEED * delta * Math.cos(Math.toRadians(gameObject.transform.rotation.y));
			gameObject.transform.position.x -= SPEED * delta * Math.sin(Math.toRadians(gameObject.transform.rotation.y));
		}
		if (input.getKey(GLFW.GLFW_KEY_A)) {
			gameObject.transform.position.z -= SPEED * delta * Math.sin(Math.toRadians(gameObject.transform.rotation.y));
			gameObject.transform.position.x -= SPEED * delta * Math.cos(Math.toRadians(gameObject.transform.rotation.y));
		}
		if (input.getKey(GLFW.GLFW_KEY_D)) {
			gameObject.transform.position.z += SPEED * delta * Math.sin(Math.toRadians(gameObject.transform.rotation.y));
			gameObject.transform.position.x += SPEED * delta * Math.cos(Math.toRadians(gameObject.transform.rotation.y));
		}
		if (input.getKey(GLFW.GLFW_KEY_SPACE)) {
			gameObject.transform.position.y += SPEED * delta;
		}
		if (input.getKey(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			gameObject.transform.position.y -= SPEED * delta;
		}
		gameObject.transform.rotation.x += input.getDeltaMouseY() * SENSIVITY * delta;
		gameObject.transform.rotation.y += input.getDeltaMouseX() * SENSIVITY * delta;
		
		if (gameObject.transform.rotation.x > 90) gameObject.transform.rotation.x = 90;
		if (gameObject.transform.rotation.x < -90) gameObject.transform.rotation.x = -90;
		if (gameObject.transform.rotation.y > 360) gameObject.transform.rotation.y -= 360;
		if (gameObject.transform.rotation.y < 0) gameObject.transform.rotation.y += 360;
	}

}
