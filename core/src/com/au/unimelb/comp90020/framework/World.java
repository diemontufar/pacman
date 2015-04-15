package com.au.unimelb.comp90020.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.au.unimelb.comp90020.actors.Ghost;
import com.au.unimelb.comp90020.actors.Pacman;
import com.au.unimelb.comp90020.actors.Pacman.Movement;
import com.au.unimelb.comp90020.framework.util.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;


/**
 * Represents the world where the game is performed. It updates the states of
 * every actor within it for each delta time.
 * 
 */
public class World {
	
	public final WorldListener listener;

	public Pacman pacman;
	public Ghost inky;
	public Ghost blinky;
	public Ghost pinky;
	public Ghost clyde;
	
	public TiledMap map;
	public TiledMapTileLayer wallsLayer;
	public TiledMapTileLayer pacdotsLayer;
	public MapObjects objectsLayer;
	public int[] wallsLayerIndex = new int[]{0}, objectsLayerIndex = new int[]{1}, collectablesLayerIndex = new int[]{2};
	
	TiledMapTileSet pacmanTileSet;
	ArrayList<TiledMapTileLayer.Cell> dotCellsInScene;
	ArrayList<TiledMapTileLayer.Cell> dotBonusCellsInScene;
    Map<String,TiledMapTile> dotTile;
    Map<String,TiledMapTile> dotBonusTile;
    
    ArrayList<TiledMapTileLayer.Cell> eyesCellsInScene;
    Map<String,TiledMapTile> eyesTiles;
	float elapsedSinceAnimation = 0.0f;
	
	public int score;
	public int lives;
	public int dots_eaten;
		
	public World(WorldListener listener) {
		this.listener = listener;
		this.map = new TmxMapLoader().load("pacman.tmx");
		this.pacmanTileSet =  this.map.getTileSets().getTileSet("PacMan");
		this.wallsLayer = (TiledMapTileLayer) this.map.getLayers().get("Walls");
		this.pacdotsLayer = (TiledMapTileLayer) this.map.getLayers().get("Collectables");
		this.objectsLayer  = this.map.getLayers().get("Objects").getObjects();
		this.pacman = new Pacman(Settings.PAC_INITIAL_POS_X,Settings.PAC_INITIAL_POS_Y,wallsLayer); 
		createGhosts();
		createDots();
		this.score = 0;
		this.lives = Settings.MAX_LIVES;
		this.dots_eaten = 0;
	}
	
	private void createGhosts() {
		//Find the spot and create the ghosts
		float x,y,width;
		MapObject obj;
		MapObject houseDoor = this.objectsLayer.get("HouseDoor");
		obj = this.objectsLayer.get("InkySpawnPoint");
		x = (Float)obj.getProperties().get("x");
		y = (Float)obj.getProperties().get("y");
		width = (Float)obj.getProperties().get("width");		
		inky = new Ghost( x, y, 1, 1 ,width, wallsLayer, houseDoor );	
		obj = this.objectsLayer.get("PinkySpawnPoint");
		x = (Float)obj.getProperties().get("x");
		y = (Float)obj.getProperties().get("y");
		width = (Float)obj.getProperties().get("width");
		pinky = new Ghost( x, y, 300, 1, width, wallsLayer, houseDoor);
		obj = this.objectsLayer.get("ClydeSpawnPoint");
		x = (Float)obj.getProperties().get("x");
		y = (Float)obj.getProperties().get("y");
		width = (Float)obj.getProperties().get("width");
		clyde = new Ghost( x, y, 1, 300 ,width, wallsLayer, houseDoor );
		obj = this.objectsLayer.get("BlinkySpawnPoint");
		x = (Float)obj.getProperties().get("x");
		y = (Float)obj.getProperties().get("y");
		width = (Float)obj.getProperties().get("width");
		blinky = new Ghost( x, y, 300, 300, width, wallsLayer, houseDoor );
		
	}

	/*
	 * Here we are creating pacdots and pacdotbonuses so that then you can access them by 
	 * looping over the ArrayLists dotCellsInScene and dotBonusCellsInScene and do something like
	 * layer.getCell(0, 1).setTile(null);
	 */
	public void createDots(){
		
		TiledMapTileLayer layer = this.pacdotsLayer;
		
        this.dotTile = new HashMap<String,TiledMapTile>();
        this.dotBonusTile = new HashMap<String,TiledMapTile>();
        for(TiledMapTile tile:this.pacmanTileSet){
            Object propertyPacDot = tile.getProperties().get("Pacdot");
            if(propertyPacDot != null) {
            	dotTile.put((String)propertyPacDot,tile);
            }
            Object propertyPacBonus = tile.getProperties().get("Pacbonus");
            if(propertyPacBonus != null) {
            	dotBonusTile.put((String)propertyPacBonus,tile);
            }
        }
        
        this.dotCellsInScene = new ArrayList<TiledMapTileLayer.Cell>();
        this.dotBonusCellsInScene = new ArrayList<TiledMapTileLayer.Cell>();
        for(int x = 0; x < layer.getWidth();x++){
            for(int y = 0; y < layer.getHeight();y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                if (cell != null){
		            Object propertyPacDot = cell.getTile().getProperties().get("Pacdot");
		            if(propertyPacDot != null){
		            	this.dotCellsInScene.add(cell);
		            }
		            Object propertyPacBonus = cell.getTile().getProperties().get("Pacbonus");
		            if(propertyPacBonus != null){
		            	this.dotBonusCellsInScene.add(cell);
		            }
                }
            }
        }
	}

	/**
	 * Updates every actor within it based on the elapsed time
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime,Movement move) {		
		updatePacman(deltaTime,move);
		updateGhosts(deltaTime);

		checkCollisions();
	}
	

	/**
	 * Check every possible collision event within the world.
	 */
	private void checkCollisions() {
		
		checkDotsCollisions();
		checkGhostsCollisions();

	}
	
	private void checkDotsCollisions() {
		
		Movement currentPacmanState = this.pacman.getCurrentState();
		float currentX =  this.pacman.position.x, currentY =  this.pacman.position.y;
		
		if(currentPacmanState == Movement.RIGTH || currentPacmanState == Movement.LEFT || 
				currentPacmanState == Movement.UP || currentPacmanState == Movement.DOWN){
			checkEaten(currentX,currentY);
		}

	}
	
	private void checkEaten(float currentX, float currentY) {
		if (isCellFood(currentX, currentY)){
			removeFood(currentX, currentY);
		}
	}
	
	private boolean isCellFood(float x, float y) {
		Cell cell = this.pacdotsLayer.getCell((int) (x / this.pacdotsLayer.getTileWidth()), (int) (y / this.pacdotsLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile()!=null;
	}
	
	private boolean removeFood(float x, float y) {
		Cell cell = this.pacdotsLayer.getCell((int) (x / this.pacdotsLayer.getTileWidth()), (int) (y / this.pacdotsLayer.getTileHeight()));
		cell.setTile(null);
		this.score++;
		this.dots_eaten++;
		return cell != null && cell.getTile() != null && cell.getTile()!=null;
	}
	
	private void checkGhostsCollisions() {
				
		if (this.pacman.bounds.overlaps(this.inky.bounds) 
				|| this.pacman.bounds.overlaps(this.blinky.bounds) 
				|| this.pacman.bounds.overlaps(this.pinky.bounds) 
				|| this.pacman.bounds.overlaps(this.clyde.bounds)) {
			this.lives--;
			this.listener.playLifeLost();
			resetPositions();
		}

	}
	
	private void resetPositions(){
		
		//Pacman go back to the starting point!
		this.pacman.position.x = Settings.PAC_INITIAL_POS_X;
		this.pacman.position.y = Settings.PAC_INITIAL_POS_Y;
		this.pacman.bounds.x = this.pacman.position.x - this.pacman.bounds.width / 2;
		this.pacman.bounds.y = this.pacman.position.y - this.pacman.bounds.height / 2;
		this.pacman.setCurrentState(Movement.NONE);

	}
	

	private void updatePacman(float deltaTime,Movement move) {
		   pacman.update(deltaTime,move);
	}
	
	private void updateGhosts(float deltaTime) {
		  this.blinky.update(deltaTime,pacman.position.x, pacman.position.y);
		  this.pinky.update(deltaTime,pacman.position.x, pacman.position.y);
		  this.clyde.update(deltaTime,pacman.position.x, pacman.position.y);
		  this.inky.update(deltaTime,pacman.position.x, pacman.position.y);
	}

}
