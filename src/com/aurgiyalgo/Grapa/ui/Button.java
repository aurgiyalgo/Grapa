package com.aurgiyalgo.Grapa.ui;

import com.aurgiyalgo.Grapa.arch.GameObject;

public class Button extends GameObject {

	public Button() {
		super("Button");
		addComponent(new AliveComponent(this));
	}

}
