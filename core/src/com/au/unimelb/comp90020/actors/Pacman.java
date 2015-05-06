package com.au.unimelb.comp90020.actors;

import com.au.unimelb.comp90020.framework.Animation;
import com.au.unimelb.comp90020.framework.DynamicGameObject;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**
 * Pacman actor represent the different Pacmans in the game
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 *
 */
public class Pacman extends DynamicGameObject {

	/**
	 * Enumeration with the possible directions
	 *
	 */
	public enum Movement {NONE,RIGTH, LEFT, UP, DOWN};
	
	/**
	 * Height of the Pacman
	 */
	public static final float PACMAN_HEIGHT = 18;
	/**
	 * Width of the Pacman
	 */
	public static final float PACMAN_WIDTH = 18;
	/**
	 * Current direction of the Paman
	 */
	public Movement currentState;
	/**
	 * Speed of the pacman
	 */
	private float speed = 60 * 2;
	/**
	 * How much time has been in the current state (for sound effect purposes)
	 */
	float stateTime;
	/**
	 * Tiled Layer with the collision objects
	 */
	private TiledMapTileLayer collisionLayer;
	/**
	 * Last Pacman position
	 */
	private float oldX, oldY;
	/**
	 * Animation to handle the Pacman movement effects
	 */
	Animation[] pacmanAnimation;
	/**
	 * Default texture when Pacman is idle
	 */
	TextureRegion defaultDirection;
	
	/**
	 * To check when pacman collides with a wall 
	 */
	boolean collisionWallX = false, collisionWallY = false;

	/** Class constructor
	 * @param x Start X position
	 * @param y Start Y position
	 * @param pacmanAnimation Pacman Animation
	 * @param defaultDirection Initial direction
	 * @param collisionLayer Object collision layer
	 */
	public Pacman(float x, float y,Animation[] pacmanAnimation,TextureRegion defaultDirection,TiledMapTileLayer collisionLayer) {

		super(x, y, PACMAN_WIDTH, PACMAN_HEIGHT);
		this.collisionLayer = collisionLayer;
		this.currentState = Movement.NONE;
		this.stateTime = 0.0f;
		this.oldX = x;
		this.oldY = y;
		this.pacmanAnimation = pacmanAnimation;
		this.defaultDirection = defaultDirection;

	}

	/**
	 * Updates the velocity, position and bounds of the pacman according to the
	 * acceleration on X axis and the elapsed time.
	 */
	public void update(float deltaTime,Movement move) {	
		
		float newX = speed * deltaTime;
		float newY = speed * deltaTime;
		
		// save old position
		this.setOldX(position.x);
		this.setOldY(position.y);
		boolean collisionX = false, collisionY = false;
		
		if (move == Movement.RIGTH){
			position.add(newX, 0f);
			collisionX = collidesRight();
		}
		if (move == Movement.LEFT){
			position.add(-newX, 0f);
			collisionX = collidesLeft();
		}
		
		// react to x collision
		if(collisionX) {
			position.x = this.getOldX();
			position.y = this.getOldY();
			stopMoving();
		}
		
		if (move == Movement.UP){
			position.add(0f, newY);
			collisionY = collidesTop();
		}
		if (move == Movement.DOWN){
			position.add(0f, -newY);
			collisionY = collidesBottom();
		}
		
		// react to y collision
		if(collisionY) {
			position.x = this.getOldX();
			position.y = this.getOldY();
			stopMoving();
		}
		
		if (move == Movement.NONE){
			stopMoving();
		}
		
		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;
		
		setCurrentState(move);
		
		stateTime += deltaTime;

	}
	
	/**
	 * Stop Pacman's movement
	 */
	private void stopMoving(){
		this.velocity.x = 0;
		this.velocity.y = 0;
	}

	/**
	 * Check if the Right position is blocked
	 * @return Whether the position is blocked or not
	 */
	public boolean collidesRight() {
		if(isCellBlocked(position.x + PACMAN_WIDTH/2, position.y))
			return true;
		return false;
	}

	/**
	 * Check if the Left position is blocked
	 * @return Whether the position is blocked or not
	 */
	public boolean collidesLeft() {
		if(isCellBlocked(position.x - PACMAN_WIDTH/2, position.y))
			return true;
		return false;
	}

	/**
	 * Check if the Top position is blocked
	 * @return Whether the position is blocked or not
	 */
	public boolean collidesTop() {
		if(isCellBlocked(position.x, position.y + PACMAN_HEIGHT/2))
			return true;
		return false;

	}

	/**
	 * Check if the Bottom position is blocked
	 * @return Whether the position is blocked or not
	 */
	public boolean collidesBottom() {
		if(isCellBlocked(position.x, position.y - PACMAN_HEIGHT/2))
			return true;
		return false;
	}
	
	/**
	 * Check whether there is a cell blocking the target position
	 * @param x X coordinate to check
	 * @param y Y coordinate to check
	 * @return Whether the new position is blocked or not
	 */
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile()!=null;
	}
	
	/**
	 * Getter method for stateTime
	 * @return The stateTime
	 */
	public float getStateTime() {
		return stateTime;
	}

	/**
	 * Setter method for the stateTime
	 * @param stateTime The new time
	 */
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	
	/**
	 * Getter method for currentState
	 * @return The currentState
	 */
	public Movement getCurrentState() {
		return currentState;
	}

	/**
	 * Setter method for the currentState
	 * @param stateTime The new state
	 */
	public void setCurrentState(Movement currentSate) {
		this.currentState = currentSate;
	}
	
	/**
	 * Getter method for speed
	 * @return The speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Setter method for speed
	 * @param stateTime The new speed
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * Getter method for oldX
	 * @return The speed
	 */
	public float getOldX() {
		return oldX;
	}

	/**
	 * Setter method for oldX
	 * @param stateTime The new oldX
	 */
	public void setOldX(float oldX) {
		this.oldX = oldX;
	}

	/**
	 * Getter method for oldY
	 * @return The oldY
	 */
	public float getOldY() {
		return oldY;
	}

	/**
	 * Setter method for oldY
	 * @param stateTime The new oldY
	 */
	public void setOldY(float oldY) {
		this.oldY = oldY;
	}

	/**
	 * Getter method for defaultDirection
	 * @return The speed
	 */
	public TextureRegion getDefaultDirection() {
		return defaultDirection;
	}

	/**
	 * Setter method for defaultDirection
	 * @param stateTime The new speed
	 */
	public void setDefaultDirection(TextureRegion defaultDirection) {
		this.defaultDirection = defaultDirection;
	}
	
	/**
	 * Getter method for pacmanAnimation
	 * @return The pacmanAnimation
	 */
	public Animation[] getPacmanAnimation() {
		return pacmanAnimation;
	}

	/**
	 * Setter method for pacmanAnimation
	 * @param stateTime The new PacmanAnimation
	 */
	public void setPacmanAnimation(Animation[] pacmanAnimation) {
		this.pacmanAnimation = pacmanAnimation;
	}
}
