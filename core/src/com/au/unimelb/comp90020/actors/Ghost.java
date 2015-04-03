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
public class Ghost extends DynamicGameObject {
	
	public static final float GHOST_HEIGHT = 16;
	public static final float GHOST_WIDTH = 16;
	private float speed = 60 * 2;
	private TiledMapTileLayer collisionLayer;
	
	float startX, startY;
	float targetX, targetY;

	public Ghost(float x, float y, float targetX, float targetY, float width, TiledMapTileLayer collisionLayer) {
		super(x, y, GHOST_WIDTH, GHOST_HEIGHT);
		this.collisionLayer = collisionLayer;
		startX = x;
		startY = y;
	}

	/**
	 * Updates the velocity, position and bounds of the ghost according to the
	 * acceleration on X axis and the elapsed time.
	 */
	public void update(float deltaTime) {
		double r = Math.random();
		float newX = speed * deltaTime;
		float newY = speed * deltaTime;
		//Try to move randomly first
		float oldX = position.x, oldY = position.y;
		boolean collisionX = false, collisionY = false;

		if (r < 0.25){
			position.add(0f, newY);
			collisionY = collidesTop();
		}
		else if (r < 0.5){
			position.add(0f, -newY);
			collisionY = collidesTop();			
		}
		else if (r < 0.75){
			position.add(newX, 0f);
			collisionX = collidesRight();
		}
		else{
			position.add(-newX, 0f);
			collisionX = collidesLeft();
		}
		if ( collisionX || collisionY ){
			position.x = oldX;
			position.y = oldY;
		}
	}
	
	public boolean collidesRight() {
		if(isCellBlocked(position.x + GHOST_WIDTH/2, position.y))
			return true;
		return false;
	}

	public boolean collidesLeft() {
		if(isCellBlocked(position.x - GHOST_WIDTH/2 , position.y))
			return true;
		return false;
	}

	public boolean collidesTop() {
		if(isCellBlocked(position.x, position.y + GHOST_HEIGHT/2))
			return true;
		return false;

	}

	public boolean collidesBottom() {
		if(isCellBlocked(position.x, position.y - GHOST_HEIGHT/2))
			return true;
		return false;
	}

	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile()!=null;
	}
}

