package com.au.unimelb.comp90020.actors;

import com.au.unimelb.comp90020.actors.Pacman.Movement;
import com.au.unimelb.comp90020.framework.DynamicGameObject;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

/**
 * Paddle class... what a surprise!. However, it has a customisable width
 * through its constructor. It is particularly useful when generating levels
 * from an XML file.
 * 
 */
public class Ghost extends DynamicGameObject {

	public static final float GHOST_HEIGHT = 16;
	public static final float GHOST_WIDTH = 16;

	private float speed = 60 ;
	
	private boolean isInHouse;
	
	private enum MODE {MINIMIZE, MAXIMIZE, CHASE}
	private MODE mode;
	
	public Movement currentDirection;
	public Movement lastDirection;
	
	private TiledMapTileLayer collisionLayer;
	
	float startX, startY;
	float targetX, targetY;
	float lastX, lastY;
	MapObject houseDoor;
	Rectangle house;

	public Ghost(float x, float y, float targetX, float targetY, float width, TiledMapTileLayer collisionLayer, MapObject houseDoor) {
		super(x, y, GHOST_WIDTH, GHOST_HEIGHT);
		this.collisionLayer = collisionLayer;
		this.houseDoor = houseDoor;
		startX = x;
		startY = y;
		isInHouse = true;
		this.targetX = targetX;
		this.targetY = targetY;
		this.mode = MODE.MINIMIZE;
		float xh = (Float)houseDoor.getProperties().get("x");
		float yh = (Float)houseDoor.getProperties().get("y");
		float hh = (Float)houseDoor.getProperties().get("height");
		float wh = (Float)houseDoor.getProperties().get("width");
		this.house = new Rectangle(xh, yh, wh, hh);
	}
	public Ghost(float x, float y, float targetX, float targetY, float width, TiledMapTileLayer collisionLayer, MapObject houseDoor, boolean isInHouse) {
		this(x,y,targetX,targetY,width,collisionLayer, houseDoor);
		this.isInHouse = false;
	}
	
	/**
	 * Updates the velocity, position and bounds of the ghost according to the
	 * acceleration on X axis and the elapsed time.
	 */
	public void update(float deltaTime) {
		float targetX, targetY;
		
		//Check if the ghost is out of the house
		if(isInHouse && !this.house.contains(position.x, position.y)){
			isInHouse = false;
			currentDirection = Movement.RIGTH;
		}

		if (isInHouse){//Get the ghost out of the house
			targetX = (Float) this.houseDoor.getProperties().get("x")+(Float) this.houseDoor.getProperties().get("width")/2;
			targetY = (Float) this.houseDoor.getProperties().get("y") + (Float) this.houseDoor.getProperties().get("height") + 4*Ghost.GHOST_HEIGHT;
		}
		else{
			targetX = this.targetX;
			targetY = this.targetY;
		}
		float newX = speed * deltaTime;
		float newY = speed * deltaTime;
		float oldX = position.x, oldY = position.y;
		boolean collisionX = false, collisionY = false;
		if (!isInHouse && this.currentDirection == Movement.UP){
			position.add(0f, newY);
			collisionY = collidesTop();
		}
		if (!isInHouse && this.currentDirection == Movement.DOWN){
			position.add(0f, -newY);
			collisionY = collidesBottom();
		}
		if (!isInHouse && this.currentDirection == Movement.RIGTH){
			position.add(newX, 0f);
			collisionX = collidesRight();		
		}
		if (!isInHouse && this.currentDirection == Movement.LEFT){
			position.add(-newX, 0f);
			collisionX = collidesLeft();		
		}
		if ( isInHouse || collisionX || collisionY ){
			position.x = oldX;
			position.y = oldY;
			boolean collision[] = new boolean[4];
			//double currDistance = Math.sqrt(Math.pow(targetX-position.x,2)+Math.pow(targetY-position.y,2));
			double distance[] = new double[4];
			float x[] = new float[4];
			float y[] = new float[4];

			//Try to move toward the target direction
			position.add(0f, newY);
			collision[0] = collidesTop();
			//distance[0] = Math.sqrt(Math.pow(targetX-position.x,2)+Math.pow(targetY-position.y,2));
			distance[0] = Math.abs(position.x - targetX)+Math.abs(position.y-targetY);
			x[0] = position.x;
			y[0] = position.y;
			position.x = oldX;
			position.y = oldY;

			position.add(0f, -newY);
			collision[1] = collidesBottom();			
			distance[1] = Math.abs(position.x - targetX)+Math.abs(position.y-targetY);
			x[1] = position.x;
			y[1] = position.y;
			position.x = oldX;
			position.y = oldY;

			position.add(newX, 0f);
			collision[2] = collidesRight();
			distance[2] = Math.abs(position.x - targetX)+Math.abs(position.y-targetY);
			x[2] = position.x;
			y[2] = position.y;		
			position.x = oldX;
			position.y = oldY;

			position.add(-newX, 0f);
			collision[3] = collidesLeft();
			distance[3] = Math.abs(position.x - targetX)+Math.abs(position.y-targetY);
			x[3] = position.x;
			y[3] = position.y;
			position.x = oldX;
			position.y = oldY;

			int step = -1;
			this.mode = MODE.MINIMIZE;
			step = getNextStep(collision, distance, step);
			
			
			if ( step!=-1 ){
				if ((lastX!=x[step] && lastY!=y[step])||isInHouse){
			    lastX = position.x;
				lastY = position.y;
				position.x = x[step];
				position.y = y[step];
				switch(step){
				case 0: this.currentDirection = Movement.UP;break;
				case 1: this.currentDirection = Movement.DOWN;break;
				case 2: this.currentDirection = Movement.RIGTH;break;
				case 3: this.currentDirection = Movement.LEFT;break;
				}
				}
				else{
					for (int i = 0; i < 4;i++){
						if (!collision[i] && step!=i){
						    lastX = position.x;
							lastY = position.y;
							position.x = x[i];
							position.y = y[i];
							switch(i){
							case 0: this.currentDirection = Movement.UP;break;
							case 1: this.currentDirection = Movement.DOWN;break;
							case 2: this.currentDirection = Movement.RIGTH;break;
							case 3: this.currentDirection = Movement.LEFT;break;
							}					
						}
					}
					
				}
			}
			
		}
		/*
		double r = Math.random();

		boolean collision[] = new boolean[4];
		//double currDistance = Math.sqrt(Math.pow(targetX-position.x,2)+Math.pow(targetY-position.y,2));
		double distance[] = new double[4];
		float x[] = new float[4];
		float y[] = new float[4];

		//Try to move toward the target direction
		position.add(0f, newY);
		collision[0] = collidesTop();
		//distance[0] = Math.sqrt(Math.pow(targetX-position.x,2)+Math.pow(targetY-position.y,2));
		distance[0] = Math.abs(position.x - targetX)+Math.abs(position.y-targetY);
		x[0] = position.x;
		y[0] = position.y;
		position.x = oldX;
		position.y = oldY;

		position.add(0f, -newY);
		collision[1] = collidesBottom();			
		distance[1] = Math.abs(position.x - targetX)+Math.abs(position.y-targetY);
		x[1] = position.x;
		y[1] = position.y;
		position.x = oldX;
		position.y = oldY;

		position.add(newX, 0f);
		collision[2] = collidesRight();
		distance[2] = Math.abs(position.x - targetX)+Math.abs(position.y-targetY);
		x[2] = position.x;
		y[2] = position.y;		
		position.x = oldX;
		position.y = oldY;

		position.add(-newX, 0f);
		collision[3] = collidesLeft();
		distance[3] = Math.abs(position.x - targetX)+Math.abs(position.y-targetY);
		x[3] = position.x;
		y[3] = position.y;
		position.x = oldX;
		position.y = oldY;

		int step = -1;
		this.mode = MODE.MINIMIZE;
		step = getNextStep(collision, distance, step);
		
		
		if ( step!=-1 && (lastX!=x[step] || lastY!=y[step])){
		    lastX = position.x;
			lastY = position.y;
			position.x = x[step];
			position.y = y[step];
		}
		else if (step!=-1){
			
			for (int i = 0; i < 4; i++){			
					if ( !collision[i] && i!=step ){
					    lastX = position.x;
						lastY = position.y;

						position.x = x[i];
						position.y = y[i];
						break;
					}
				}			
		}
		else{			//Try to move randomly then		
			this.mode = this.mode==MODE.MINIMIZE?MODE.MAXIMIZE:MODE.MINIMIZE;
			if (r < 0.25){
				position.add(0f, newY);
				collisionY = collidesTop();
			}
			else if (r < 0.5){
				position.add(0f, -newY);
				collisionY = collidesBottom();			
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
		}*/
	}

	private int getNextStep(boolean[] collision, double[] distance, int step) {
		double oldDistance = 10000;
		for (int i = 0; i < 4; i++){
			if (mode == MODE.MINIMIZE){
				
				if ( !collision[i] && oldDistance > distance[i] ){
					step = i;
					oldDistance = distance[i];
				}
			}
			if (mode == MODE.MAXIMIZE){
				if ( !collision[i] && oldDistance < distance[i] ){
					step = i;
					oldDistance = distance[i];
				}				
			}
		}
		return step;
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
		if(isCellBlocked(position.x, position.y + GHOST_HEIGHT/2) || (!this.isInHouse && this.house.contains(position.x, position.y)))
			return true;
		return false;

	}

	public boolean collidesBottom() {
		if(isCellBlocked(position.x, position.y - GHOST_HEIGHT/2) || (!this.isInHouse && this.house.contains(position.x, position.y)))
			return true;
		return false;
	}

	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile()!=null;
	}
}

