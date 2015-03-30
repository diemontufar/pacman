package com.au.unimelb.comp90020.framework;

import com.au.unimelb.comp90020.actors.Pacman;
import com.au.unimelb.comp90020.actors.Pacman.Movement;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


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

	public Pacman pacman;
	public TiledMap map;
	public TiledMapTileLayer wallsLayer;
	public TiledMapTileLayer pacdotsLayer;
	public MapObjects objectsLayer;
	public int[] wallsLayerIndex = new int[]{0}, objectsLayerIndex = new int[]{1}, collectablesLayerIndex = new int[]{2};
	
	public int score;
	public int lives;
	public int game_state;

	public World(WorldListener listener) {
		this.listener = listener;
		this.map = new TmxMapLoader().load("pacman.tmx");
		this.wallsLayer = (TiledMapTileLayer) this.map.getLayers().get("Walls");
		this.pacdotsLayer = (TiledMapTileLayer) this.map.getLayers().get("Collectables");
		this.objectsLayer  = this.map.getLayers().get("Objects").getObjects();
		this.pacman = new Pacman(200,218,wallsLayer); //Create PacMan with initial position in 200,200
		this.score = 0;
		this.lives = 0;
		this.game_state = WORLD_STATE_RUNNING;
	}

	/**
	 * Updates every actor within it based on the elapsed time
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime,Movement move) {
		
		updatePacman(deltaTime,move);
		
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

	private void updatePacman(float deltaTime,Movement move) {
		pacman.update(deltaTime,move);
	}

	
}
