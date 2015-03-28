package com.au.unimelb.comp90020.framework;

import com.au.unimelb.comp90020.actors.Pacman;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;


/**
 * Represents the world where the game is performed. It updates the states of
 * every actor within it for each delta time.
 * 
 */
public class World {

	public final WorldListener listener;
	
	Pacman pacman;
	TiledMap map;
	
	@SuppressWarnings("static-access")
	public World(WorldListener listener) {
		this.listener = listener;
		pacman = new Pacman(16, 16, 8);
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
	 */
	public void update(float deltaTime) {
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

	public void movePacmanRight() {
		float x = pacman.position.x;
		float y = pacman.position.y;
		
		
		pacman.moveRight();
	}

	public void movePacmanLeft() {
		pacman.moveLeft();
		
	}
	public void movePacmanUp() {
		pacman.moveUp();
	}
	public void movePacmanDown() {
		pacman.moveDown();
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}

	
}
