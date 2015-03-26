package com.au.unimelb.comp90020.framework;

import com.badlogic.gdx.math.Vector2;

/**
 * Some objects may have movement characteristics as velocity and acceleration,
 * 
 */
public class DynamicGameObject extends GameObject {

	public final Vector2 velocity;
	public final Vector2 accel;

	public DynamicGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity = new Vector2();
		accel = new Vector2();
	}
}
