package com.au.unimelb.comp90020.framework;

import com.badlogic.gdx.math.Vector2;

/**
 * DynamicObject represents an object/actor that can move in the map and has velocity and acceleration
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 *
 */
public class DynamicGameObject extends GameObject {

	/**
	 * Vector representing the velocity in both coordinates
	 */
	public final Vector2 velocity;
	/**
	 * Acceleration vector in both coordinates
	 */
	public final Vector2 accel;

	/**
	 * Class constructor
	 * @param x Initial x position
	 * @param y Initial y position
	 * @param width Width of the object
	 * @param height Height of the object
	 */
	public DynamicGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity = new Vector2();
		accel = new Vector2();
	}
}
