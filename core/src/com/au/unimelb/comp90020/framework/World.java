package com.au.unimelb.comp90020.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.au.unimelb.comp90020.actors.Pacman;
import com.au.unimelb.comp90020.actors.Pacman.Movement;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
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
	public int game_state;

	public World(WorldListener listener) {
		this.listener = listener;
		this.map = new TmxMapLoader().load("pacman.tmx");
		this.pacmanTileSet =  this.map.getTileSets().getTileSet("PacMan");
		this.wallsLayer = (TiledMapTileLayer) this.map.getLayers().get("Walls");
		this.pacdotsLayer = (TiledMapTileLayer) this.map.getLayers().get("Collectables");
		this.objectsLayer  = this.map.getLayers().get("Objects").getObjects();
		this.pacman = new Pacman(225,120,wallsLayer,pacdotsLayer); //Create PacMan with initial position in 200,200
		createDots();
		createEyes(); //just for fun
		this.score = 0;
		this.lives = 0;
		this.game_state = WORLD_STATE_RUNNING;
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
	
	private void createEyes(){
		
        this.eyesTiles = new HashMap<String,TiledMapTile>();
        for(TiledMapTile tile:this.pacmanTileSet){
            Object property = tile.getProperties().get("GhostEyes");
            if(property != null) {
                eyesTiles.put((String)property,tile);
            }
        }
        
        this.eyesCellsInScene = new ArrayList<TiledMapTileLayer.Cell>();
        TiledMapTileLayer layer = this.wallsLayer;
        for(int x = 0; x < layer.getWidth();x++){
            for(int y = 0; y < layer.getHeight();y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                if (cell != null){
		            Object property = cell.getTile().getProperties().get("GhostEyes");
		            if(property != null){
		            	this.eyesCellsInScene.add(cell);
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
		updateEyes(deltaTime);
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
	
	private void updateEyes(float deltaTime) {
		
		this.elapsedSinceAnimation += deltaTime;
		if (this.elapsedSinceAnimation > 0.5f) {
			
			for (TiledMapTileLayer.Cell cell : eyesCellsInScene) {
				String property = (String) cell.getTile().getProperties()
						.get("GhostEyes");
				Integer currentAnimationFrame = Integer.parseInt(property);

				currentAnimationFrame++;
				if (currentAnimationFrame > eyesTiles.size())
					currentAnimationFrame = 1;

				TiledMapTile newTile = eyesTiles.get(currentAnimationFrame
						.toString());
				cell.setTile(newTile);
			}
			
			this.elapsedSinceAnimation = 0.0f;
		}
	}

}
