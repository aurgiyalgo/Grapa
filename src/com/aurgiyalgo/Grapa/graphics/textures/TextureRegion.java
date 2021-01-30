package com.aurgiyalgo.Grapa.graphics.textures;

public class TextureRegion {
	
	public static final int TEXTURE_ATLAS_SIDE = 32;
	public static final int REGION_SIDE = 8;
	public static final int REGIONS_PER_SIDE = TEXTURE_ATLAS_SIDE / REGION_SIDE;
	private static final float TEXTURE_RELATION = REGION_SIDE / (float) TEXTURE_ATLAS_SIDE;
	
	private float u1, v1, u2, v2;
	
	public TextureRegion(int x, int y) {
		u1 = x * TEXTURE_RELATION;
		u2 = u1 + TEXTURE_RELATION;

		v1 = 1 - y * TEXTURE_RELATION - TEXTURE_RELATION;
		v2 = v1 + TEXTURE_RELATION;
	}
	
	public TextureRegion(int i) {
		int x = i % REGIONS_PER_SIDE;
		int y = i / REGIONS_PER_SIDE;
		u1 = x * TEXTURE_RELATION;
		u2 = u1 + TEXTURE_RELATION;

		v1 = 1 - y * TEXTURE_RELATION - TEXTURE_RELATION;
		v2 = v1 + TEXTURE_RELATION;
	}

	public float getU1() {
		return u1;
	}

	public float getV1() {
		return v1;
	}

	public float getU2() {
		return u2;
	}

	public float getV2() {
		return v2;
	}

}
