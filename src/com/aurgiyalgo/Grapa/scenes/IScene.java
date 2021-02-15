package com.aurgiyalgo.Grapa.scenes;

import com.aurgiyalgo.Grapa.Grapa;

public interface IScene {
	
	/**
	 * Called when the scene is loaded through {@link Grapa#setScene(Scene)}
	 */
	public abstract void onShow();
	
	public abstract void onHide();
	
	public abstract void update(float delta);
	
	public abstract void onResize();

}
