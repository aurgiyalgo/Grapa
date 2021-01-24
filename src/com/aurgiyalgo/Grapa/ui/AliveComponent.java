package com.aurgiyalgo.Grapa.ui;

import com.aurgiyalgo.Grapa.arch.Component;
import com.aurgiyalgo.Grapa.arch.GameObject;

public class AliveComponent extends Component {

	public AliveComponent(GameObject object) {
		super(object);
	}

	@Override
	public void update(double delta) {
		System.out.println("Object alive: " + gameObject.getName());
	}

}
