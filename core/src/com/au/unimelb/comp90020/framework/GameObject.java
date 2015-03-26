package com.au.unimelb.comp90020.framework;

import com.badlogic.gdx.math.Vector2;

/**
 * Every GameObject has associated position and bounds. Bounds helps to keep
 * track of collisions.
 * 
 */
public class GameObject {
	public final Vector2 position;
	public final GenericRectangle bounds;

	public GameObject(float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.bounds = new GenericRectangle(x - width / 2, y - height / 2, width, height);
	}

}
