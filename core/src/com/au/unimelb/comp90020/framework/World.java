package com.au.unimelb.comp90020.framework;

import com.au.unimelb.comp90020.actors.Pacman;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;


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
	TiledMap map;
	public int score;
	public int lives;
	public int game_state;
	
	private Array<Rectangle> tiles = new Array<Rectangle>();

	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};


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


	public void movePacmanRight() {
		float x = pacman.position.x;
		float y = pacman.position.y;

		Rectangle pacmanRect = rectPool.obtain();
		pacmanRect.set(x, y, Pacman.PACMAN_WIDTH, Pacman.PACMAN_HEIGHT);
		
		int startX, startY, endX, endY;
		startX = (int) x;
		startY = (int) y;
		endX = (int) (x+pacman.PACMAN_WIDTH + 2);
		endY = (int) (y + Pacman.PACMAN_HEIGHT);
		getTiles(startX, startY, endX, endY, tiles);
		
		boolean collides = false;
		
		pacmanRect.x += pacman.PACMAN_WIDTH + 2;
		
		for (Rectangle tile : tiles) {
			if (pacmanRect.overlaps(tile)) {
				collides = true;
				break;
			}
		}
		if (!collides)
		   pacman.moveRight();
	}

	public void movePacmanLeft() {
		float x = pacman.position.x;
		float y = pacman.position.y;

		Rectangle pacmanRect = rectPool.obtain();
		pacmanRect.set(x, y, Pacman.PACMAN_WIDTH, Pacman.PACMAN_HEIGHT);
		
		int startX, startY, endX, endY;
		startX = (int) (x - 2 - Pacman.PACMAN_WIDTH);
		startY = (int) y;
		endX = (int) (x);
		endY = (int) (y + Pacman.PACMAN_HEIGHT);
		getTiles(startX, startY, endX, endY, tiles);
		
		boolean collides = false;
		pacmanRect.x -= pacman.PACMAN_WIDTH - 2;
		for (Rectangle tile : tiles) {
			if (pacmanRect.overlaps(tile)) {
				collides = true;
				break;
			}
		}
		if (!collides)
		   pacman.moveLeft();
	}
	public void movePacmanUp() {
		float x = pacman.position.x;
		float y = pacman.position.y;

		Rectangle pacmanRect = rectPool.obtain();
		pacmanRect.set(x, y, Pacman.PACMAN_WIDTH, Pacman.PACMAN_HEIGHT);
		
		int startX, startY, endX, endY;
		startX = (int) (x);
		startY = (int) (y - Pacman.PACMAN_HEIGHT - 2);
		endX = (int) (x + Pacman.PACMAN_WIDTH);
		endY = (int) (y);
		getTiles(startX, startY, endX, endY, tiles);
		
		boolean collides = false;
		pacmanRect.y -= pacman.PACMAN_HEIGHT - 2;
		for (Rectangle tile : tiles) {
			if (pacmanRect.overlaps(tile)) {
				collides = true;
				break;
			}
		}
		if (!collides)
		   pacman.moveUp();
	}
	public void movePacmanDown() {
		float x = pacman.position.x;
		float y = pacman.position.y;

		Rectangle pacmanRect = rectPool.obtain();
		pacmanRect.set(x, y, Pacman.PACMAN_WIDTH, Pacman.PACMAN_HEIGHT);
		
		int startX, startY, endX, endY;
		startX = (int) (x);
		startY = (int) (y);
		endX = (int) (x + Pacman.PACMAN_WIDTH);
		endY = (int) (y + Pacman.PACMAN_HEIGHT + 2);
		getTiles(startX, startY, endX, endY, tiles);
		
		boolean collides = false;
		pacmanRect.x += pacman.PACMAN_HEIGHT + 2;
		for (Rectangle tile : tiles) {
			if (pacmanRect.overlaps(tile)) {
				collides = true;
				break;
			}
		}
		if (!collides)
		   pacman.moveDown();
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}

	private void getTiles (int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("Walls");
		rectPool.freeAll(tiles);
		tiles.clear();
		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
	}

	
	private void updatePacman(float deltaTime) {
		pacman.update(deltaTime);
	}
	
}
