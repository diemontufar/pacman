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
	
	private enum MODE {SCATTER, CHASE}
	private MODE mode;
	private long timeLastChange;
	
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
		timeLastChange = System.currentTimeMillis();
		this.collisionLayer = collisionLayer;
		this.houseDoor = houseDoor;
		startX = x;
		startY = y;
		isInHouse = true;
		this.targetX = targetX;
		this.targetY = targetY;
		this.mode = MODE.SCATTER;
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
	 * @param pacmanY 
	 * @param pacmanX 
	 */
	public void update(float deltaTime, float pacmanX, float pacmanY) {
		float targetX, targetY;
		
		//Check if the ghost is out of the house
		if(isInHouse && !this.house.contains(position.x, position.y)){
			isInHouse = false;
			currentDirection = Movement.RIGTH;
		}
		else if(!isInHouse && System.currentTimeMillis()-timeLastChange>10000){
			this.mode = this.mode==MODE.CHASE?MODE.SCATTER:MODE.CHASE;
			System.out.println("CHANGING MODE: "+this.mode);
			timeLastChange = System.currentTimeMillis();			
		}

		if (isInHouse){//Get the ghost out of the house
			targetX = (Float) this.houseDoor.getProperties().get("x")+(Float) this.houseDoor.getProperties().get("width")/2;
			targetY = (Float) this.houseDoor.getProperties().get("y") + (Float) this.houseDoor.getProperties().get("height") + 4*Ghost.GHOST_HEIGHT;
		}
		else if (mode == MODE.SCATTER){
			speed = 60;
			targetX = this.targetX;
			targetY = this.targetY;
		}
		else{//Pacmannnn
			speed = 120;
			targetX = pacmanX;
			targetY = pacmanY;			
		}

		float newX = speed * deltaTime;
		float newY = speed * deltaTime;
		float oldX = position.x, oldY = position.y;
		boolean collisionX = false, collisionY = false;
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
		int isIntersection = (!collision[0]?1:0)+(!collision[1]?1:0)+(!collision[2]?1:0)+(!collision[3]?1:0);
		
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

			
			int step = -1;
			step = getNextStep(collision, distance, step);
			
			
			if ( step!=-1 ){
				Movement nextDirection = Movement.UP;
				switch(step){
				case 0: nextDirection = Movement.UP;break;
				case 1: nextDirection = Movement.DOWN;break;
				case 2: nextDirection = Movement.RIGTH;break;
				case 3: nextDirection = Movement.LEFT;break;
				}

				if (isInHouse || nextDirection==Movement.UP && currentDirection!=Movement.DOWN ||
						nextDirection==Movement.DOWN && currentDirection!=Movement.UP ||
						nextDirection==Movement.RIGTH && currentDirection!=Movement.LEFT ||
						nextDirection==Movement.LEFT && currentDirection!=Movement.RIGTH){
				   position.x = x[step];
				   position.y = y[step];
				   currentDirection = nextDirection;
				}
				else{
					for (int i = 0; i < 4;i++){
						nextDirection = Movement.UP;
						switch(i){
						case 0: nextDirection = Movement.UP;break;
						case 1: nextDirection = Movement.DOWN;break;
						case 2: nextDirection = Movement.RIGTH;break;
						case 3: nextDirection = Movement.LEFT;break;
						}

						if (!collision[i] && step!=i && (nextDirection==Movement.UP && currentDirection!=Movement.DOWN ||
								nextDirection==Movement.DOWN && currentDirection!=Movement.UP ||
								nextDirection==Movement.RIGTH && currentDirection!=Movement.LEFT ||
								nextDirection==Movement.LEFT && currentDirection!=Movement.RIGTH)){
							position.x = x[i];
							position.y = y[i];
							switch(i){
							case 0: this.currentDirection = Movement.UP;break;
							case 1: this.currentDirection = Movement.DOWN;break;
							case 2: this.currentDirection = Movement.RIGTH;break;
							case 3: this.currentDirection = Movement.LEFT;break;
							}
							i = 4;
						}
					}
					
				}
			}
			
		}
	}

	private int getNextStep(boolean[] collision, double[] distance, int step) {
		double oldDistance = 10000;
		for (int i = 0; i < 4; i++){

				
				if ( !collision[i] && oldDistance > distance[i] ){
					step = i;
					oldDistance = distance[i];
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
		if(isCellBlocked(position.x, position.y - GHOST_HEIGHT/2) || (!this.isInHouse && this.house.contains(position.x, position.y-20)))
			return true;
		return false;
	}

	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile()!=null;
	}
}

