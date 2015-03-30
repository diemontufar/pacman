package com.au.unimelb.comp90020.actors;

import com.au.unimelb.comp90020.framework.DynamicGameObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**
 * Paddle class... what a surprise!. However, it has a customisable width
 * through its constructor. It is particularly useful when generating levels
 * from an XML file.
 * 
 */
public class Pacman extends DynamicGameObject {

	public enum Movement {NONE,RIGTH, LEFT, UP, DOWN};
	
	public static final float PACMAN_HEIGHT = 18;
	public static final float PACMAN_WIDTH = 18;
	public Movement currentSate;
	private float speed = 60 * 2;
	float stateTime;
	private TiledMapTileLayer collisionLayer;
	
	//To check when pacman collides with a wall
	boolean collisionWallX = false, collisionWallY = false;

	public Pacman(float x, float y,TiledMapTileLayer collisionLayer) {

		super(x, y, PACMAN_WIDTH, PACMAN_HEIGHT);
		this.collisionLayer = collisionLayer;
		this.currentSate = Movement.NONE;
		this.stateTime = 0.0f;

	}

	/**
	 * Updates the velocity, position and bounds of the pacman according to the
	 * acceleration on X axis and the elapsed time.
	 */
	public void update(float deltaTime,Movement move) {	
		
		float newX = speed * deltaTime;
		float newY = speed * deltaTime;
		setCurrentSate(move);
		stateTime += deltaTime;
		
		// save old position
		float oldX = position.x, oldY = position.y;
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
//			Gdx.app.log("Info:", "Collision x");
			position.x = oldX;
			position.y = oldY;
			velocity.x = 0;
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
//			Gdx.app.log("Info:", "Collision y");
			position.x = oldX;
			position.y = oldY;
			velocity.y = 0;
		}

	}

	public boolean collidesRight() {
		if(isCellBlocked(position.x + PACMAN_WIDTH/2, position.y))
			return true;
		return false;
	}

	public boolean collidesLeft() {
		if(isCellBlocked(position.x - PACMAN_WIDTH/2 , position.y))
			return true;
		return false;
	}

	public boolean collidesTop() {
		if(isCellBlocked(position.x, position.y + PACMAN_HEIGHT/2))
			return true;
		return false;

	}

	public boolean collidesBottom() {
		if(isCellBlocked(position.x, position.y - PACMAN_HEIGHT/2))
			return true;
		return false;
	}
	
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile()!=null;
	}
	
	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	
	public Movement getCurrentSate() {
		return currentSate;
	}

	public void setCurrentSate(Movement currentSate) {
		this.currentSate = currentSate;
	}
}
