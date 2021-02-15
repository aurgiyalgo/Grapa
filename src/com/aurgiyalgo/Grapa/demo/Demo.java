package com.aurgiyalgo.Grapa.demo;

import com.aurgiyalgo.Grapa.Grapa;

public class Demo {
	
	public static void main(String[] args) {
		Grapa grapa = new Grapa();
		grapa.setScene(new WorldScene());
		grapa.init();
	}

}
