package com.au.unimelb.comp90020.framework;

import com.au.unimelb.comp90020.actors.Pacman;
import com.badlogic.gdx.maps.tiled.TiledMap;


/**
 * Represents the world where the game is performed. It updates the states of
 * every actor within it for each delta time.
 * 
 */
public class World {

	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_LEVEL_END = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int WORLD_STATE_GAME_LOST_LIFE = 3;
	
	public final WorldListener listener;
	public int score;
	public int lives;
	public int game_state;
	
	public Pacman pacman;
	
	public World(WorldListener listener) {
		this.listener = listener;
		this.pacman = new Pacman(0,0); //Create PacMan with initial position in 0,0
		this.score = 0;
		this.lives = 0;
		this.game_state = WORLD_STATE_RUNNING;
	}

	/**
	 * Updates every actor within it based on the elapsed time
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		
		updatePacman(deltaTime);
		
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
	
	private void updatePacman(float deltaTime) {
		pacman.update(deltaTime);
	}
	
}
