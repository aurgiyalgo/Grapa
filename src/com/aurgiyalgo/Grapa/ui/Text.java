package com.aurgiyalgo.Grapa.ui;

import com.aurgiyalgo.Grapa.arch.GameObject;

public class Text extends GameObject {

	public Text() {
		super("Text");
		addComponent(new AliveComponent(this));
	}

}
