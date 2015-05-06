package com.au.unimelb.comp90020.framework;

import com.badlogic.gdx.math.Vector2;

/**
 * GameObject represents a static object/actor
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 *
 */
public class GameObject {
	/**
	 * Position vector 
	 */
	public final Vector2 position;
	/**
	 * Rectangle bounds surrounding the object
	 */
	public final GenericRectangle bounds;

	/**
	 * Class constructor
	 * @param x X position
	 * @param y Y position
	 * @param width Width of the object
	 * @param height Height of the object
	 */
	public GameObject(float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.bounds = new GenericRectangle(x - width / 2, y - height / 2, width, height);
	}

}
