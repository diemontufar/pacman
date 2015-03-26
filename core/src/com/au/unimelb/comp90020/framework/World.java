package com.au.unimelb.comp90020.framework;


/**
 * Represents the world where the game is performed. It updates the states of
 * every actor within it for each delta time.
 * 
 */
public class World {

	public final WorldListener listener;

	@SuppressWarnings("static-access")
	public World(WorldListener listener, int gameLevel) {
		
		this.listener = listener;


	}

	/**
	 * 
	 */
	private void generateLevel() {

	}

	/**
	 * Updates every actor within it based on the elapsed time
	 * 
	 * @param deltaTime
	 * @param accelX
	 * @param touchX
	 */
	public void update(float deltaTime, float accelX, float touchX) {

	}

	/**
	 * Update the state of the world to WORLD_STATE_LEVEL_END, level score,
	 * total score and rankings (which also updates the next score to beat).
	 */
	private void checkNextLevel() {

	}

	/**
	 * Update the state of the world to either WORLD_STATE_GAME_LOST_LIFE or
	 * WORLD_STATE_GAME_OVER depending on the remaining lives.
	 */
	private void checkLostLife() {
		
	}

	/**
	 * Update the state of the world to WORLD_STATE_GAME_OVER.
	 */
	private void checkGameOver() {

	}

	/**
	 * Check every possible collision event within the world.
	 */
	private void checkCollisions() {

	}

	
}
