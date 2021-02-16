package com.aurgiyalgo.Grapa.scenes;

import com.aurgiyalgo.Grapa.Grapa;

public interface IScene {
	
	/**
	 * Called when the scene is loaded through {@link Grapa#setScene(Scene)}
	 */
	public void onShow();
	
	public void onHide();
	
	public void update(double delta);
	
	public void onResize();

}
