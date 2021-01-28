package com.aurgiyalgo.Grapa.graphics.model;

import com.aurgiyalgo.Grapa.arch.Transform;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Model data with a {@link Transform} component for rendering.
 */
@AllArgsConstructor
public class Model {
	
	@Getter private ModelData data;
	@Getter private Transform transform;

}
