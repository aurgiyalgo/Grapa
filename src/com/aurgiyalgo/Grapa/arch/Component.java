package com.aurgiyalgo.Grapa.arch;

public abstract class Component {
	
	protected GameObject gameObject;
	
	private boolean active;
	
	public Component(GameObject object) {
		this.gameObject = object;
	}
	
	public abstract void update(double delta);
	
	public void activate() {
		if (isActive()) throw new IllegalStateException("Component is already activated!");
		active = true;
	}
	
	public void deactivate() {
		if (!isActive()) throw new IllegalStateException("Component is already deactivated!");
		active = false;
	}
	
	public boolean isActive() {
		return active;
	}

}
