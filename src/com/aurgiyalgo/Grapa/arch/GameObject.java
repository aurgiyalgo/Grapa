package com.aurgiyalgo.Grapa.arch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * General purpose object to be handled by an {@link Engine}.
 */
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
		this.transform = new Transform();
		
		components = new ArrayList<Component>();
		cache = new HashMap<Class<?>, Component>();
	}
	
	/**
	 * Called every frame
	 * @param delta Time since the last frame (in seconds)
	 */
	public void update(double delta) {
		updateComponents(delta);
	}
	
	/**
	 * Update the components of the GameObject
	 * @param delta Time since the last frame (in seconds)
	 */
	protected void updateComponents(double delta) {
		for (Component c : components) c.update(delta);
	}
	
	/**
	 * Add a component to the GameObject
	 * @param component
	 */
	public void addComponent(Component component) {
		components.add(component);
	}
	
	/**
	 * Get the component of type T
	 * @param <T> Type of the component to get
	 * @param clazz Class of the component to get
	 * @return {@link Optional} containing a {@link Component} of type <code>T</code>, 
	 * or empty if the GameObject does not contain it
	 */
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
	
	/**
	 * Checks if the GameObject contains a component
	 * @param clazz Class of the component to check
	 * @return <code>true</code> if the GameObject contains it, <code>false</code> if not
	 */
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
