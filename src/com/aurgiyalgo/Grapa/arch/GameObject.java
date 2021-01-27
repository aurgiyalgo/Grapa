package com.aurgiyalgo.Grapa.arch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public abstract class GameObject {
	
	public Transform transform;
	
	private Engine engine;
	
	private String name;
	private UUID internalId;
	
	private List<Component> components;
	private Map<Class<?>, Component> cache;
	
	public GameObject(String name) {
		internalId = UUID.randomUUID();
		this.name = name;
		
		components = new ArrayList<Component>();
		cache = new HashMap<Class<?>, Component>();
	}
	
	public void update(double delta) {
		for (Component c : components) c.update(delta);
	}
	
	public void addComponent(Component component) {
		components.add(component);
	}
	
	public <T> Optional<T> getComponent(Class<T> clazz) {
		Component cachedComponent = cache.get(clazz);
		if (cachedComponent != null) {
			return Optional.ofNullable(clazz.cast(cachedComponent));
		}
		
		for (Component component : components) {
			if (clazz.isInstance(component)) {
				cache.put(clazz, component);
				return Optional.ofNullable(clazz.cast(component));
			}
		}
		
		return Optional.empty();
	}
	
	public boolean hasComponent(Class<? extends Component> clazz) {
		if (cache.containsKey(clazz)) return true;
		
		for (Component component : components) {
			if (clazz.isInstance(component)) {
				cache.put(clazz, component);
				return true;
			}
		}
		
		return false;
	}
	
	protected void setEngine(Engine engine) {
		this.engine = engine;
	}
	
	public String getName() {
		return name;
	}
	
	public UUID getInternalId() {
		return internalId;
	}
	
	public Engine getEngine() {
		return engine;
	}

}
