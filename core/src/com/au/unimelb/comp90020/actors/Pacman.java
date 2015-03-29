package com.au.unimelb.comp90020.actors;

import com.au.unimelb.comp90020.framework.DynamicGameObject;

/**
 * Paddle class... what a surprise!. However, it has a customisable width
 * through its constructor. It is particularly useful when generating levels
 * from an XML file.
 * 
 */
public class Pacman extends DynamicGameObject {
	
	public static final float PACMAN_HEIGHT = 16;
	public static final float PACMAN_WIDTH = 16;

	public Pacman(float x, float y) {

		super(x, y, PACMAN_WIDTH, PACMAN_HEIGHT);

	}

	/**
	 * Updates the velocity, position and bounds of the pacman according to the
	 * acceleration on X axis and the elapsed time.
	 */
	public void update(float deltaTime) {		

	}

	public void moveRight() {
		position.add(2f, 0f);
	}
	public void moveLeft() {
		position.add(-2f, 0f);
	}
	public void moveUp() {
		position.add(0f, 2f);
	}
	public void moveDown() {
		position.add(0f, -2f);
	}

}
