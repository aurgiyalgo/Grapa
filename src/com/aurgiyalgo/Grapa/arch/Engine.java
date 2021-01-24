package com.aurgiyalgo.Grapa.arch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public abstract class Engine {
	
	private List<GameObject> gameObjects;
	
	public Engine() {
		gameObjects = new ArrayList<GameObject>();
	}
	
	public void update(double delta) {
		for (GameObject object : gameObjects) object.update(delta);
	}
	
	public void addGameObject(GameObject object) {
		object.setEngine(this);
		gameObjects.add(object);
	}
	
	public void removeGameObject(String name) {
		Iterator<GameObject> iterator = gameObjects.iterator();
		while (iterator.hasNext()) {
			GameObject object = iterator.next();
			if (object.getName().equals(name)) {
				iterator.remove();
				return;
			}
		}
		throw new NoSuchElementException("The object \"" + name + "\" could not be removed");
	}
	
	public void removeGameObject(UUID id) {
		Iterator<GameObject> iterator = gameObjects.iterator();
		while (iterator.hasNext()) {
			GameObject object = iterator.next();
			if (object.getInternalId().equals(id)) {
				iterator.remove();
				return;
			}
		}
		throw new NoSuchElementException("The object with Id " + id + " could not be removed");
	}

}
