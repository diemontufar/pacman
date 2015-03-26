package com.au.unimelb.comp90020.actors;

import com.au.unimelb.comp90020.framework.DynamicGameObject;

/**
 * Paddle class... what a surprise!. However, it has a customisable width
 * through its constructor. It is particularly useful when generating levels
 * from an XML file.
 * 
 */
public class Pacdot extends DynamicGameObject {
	
	public static final float PACDOT_HEIGHT = 16;
	public static final float PACDOT_WIDTH = 16;

	public Pacdot(float x, float y, float width) {

		super(x, y, PACDOT_WIDTH, PACDOT_HEIGHT);

	}

	/**
	 * Updates the velocity, position and bounds of the pacdot according to the
	 * acceleration on X axis and the elapsed time.
	 */
	public void update(float deltaTime, float accelX, float touchX) {

	}
}
