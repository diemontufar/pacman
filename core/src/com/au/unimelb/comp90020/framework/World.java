package com.au.unimelb.comp90020.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.au.unimelb.comp90020.PacManGame.MultiplayerMode;
import com.au.unimelb.comp90020.actors.Ghost;
import com.au.unimelb.comp90020.actors.Pacman;
import com.au.unimelb.comp90020.actors.Pacman.Movement;
import com.au.unimelb.comp90020.framework.util.Settings;
import com.au.unimelb.comp90020.multiplayer.networking.Message;
import com.au.unimelb.comp90020.multiplayer.networking.MessageListener;
import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;
import com.au.unimelb.comp90020.screens.GameScreen;
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
public class World implements MessageListener {

	public final WorldListener listener;

	public List<Pacman> pacmans;
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

	private int controlledPacman;
	private GameScreen screen;

	public World(WorldListener listener, GameScreen screen) {
		this.listener = listener;
		this.screen = screen;
		this.map = new TmxMapLoader().load("pacman.tmx");
		this.pacmanTileSet =  this.map.getTileSets().getTileSet("PacMan");
		this.wallsLayer = (TiledMapTileLayer) this.map.getLayers().get("Walls");
		this.pacdotsLayer = (TiledMapTileLayer) this.map.getLayers().get("Collectables");
		this.objectsLayer  = this.map.getLayers().get("Objects").getObjects();
		this.pacmans = new ArrayList<Pacman>();
		this.pacmans.add(new Pacman(Settings.PAC_INITIAL_POS_X,Settings.PAC_INITIAL_POS_Y,wallsLayer)); 
		createGhosts();
		createDots();
		this.score = 0;
		this.lives = Settings.MAX_LIVES;
		this.dots_eaten = 0;
		this.controlledPacman = 0;
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
		Pacman pacman = this.pacmans.get(this.controlledPacman);
		Movement currentPacmanState = pacman.getCurrentState();
		float currentX = pacman.position.x, currentY =  pacman.position.y;

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
		Pacman pacman = this.pacmans.get(this.controlledPacman);
		if (pacman.bounds.overlaps(this.inky.bounds) 
				|| pacman.bounds.overlaps(this.blinky.bounds) 
				|| pacman.bounds.overlaps(this.pinky.bounds) 
				|| pacman.bounds.overlaps(this.clyde.bounds)) {
			this.lives--;
			this.listener.playLifeLost();
			resetPositions();
		}

	}

	private void resetPositions(){
		Pacman pacman = this.pacmans.get(this.controlledPacman);
		//Pacman go back to the starting point!
		pacman.position.x = Settings.PAC_INITIAL_POS_X;
		pacman.position.y = Settings.PAC_INITIAL_POS_Y;
		pacman.bounds.x = pacman.position.x - pacman.bounds.width / 2;
		pacman.bounds.y = pacman.position.y - pacman.bounds.height / 2;
		pacman.setCurrentState(Movement.NONE);

	}


	private void updatePacman(float deltaTime,Movement move) {
		Pacman pacman = this.pacmans.get(this.controlledPacman);
		pacman.update(deltaTime,move);
	}

	private void updateGhosts(float deltaTime) {
		if (this.screen.game.mode == MultiplayerMode.server || this.screen.game.mode == MultiplayerMode.none){
			Pacman pacman = this.pacmans.get(this.controlledPacman);
			this.blinky.update(deltaTime,pacman.position.x, pacman.position.y);
			this.pinky.update(deltaTime,pacman.position.x, pacman.position.y);
			this.clyde.update(deltaTime,pacman.position.x, pacman.position.y);
			this.inky.update(deltaTime,pacman.position.x, pacman.position.y);
		}
		//Update Ghost positions
		if (this.screen.game.mode == MultiplayerMode.server){			  
			StringBuilder sb = new StringBuilder();
			toGhostPositionString(sb,"BLINKY",this.blinky);
			sb.append(",");
			toGhostPositionString(sb,"PINKY",this.pinky);
			sb.append(",");
			toGhostPositionString(sb,"CLYDE",this.clyde);
			sb.append(",");
			toGhostPositionString(sb,"INKY",this.inky);
			Message m = new Message("localhost", sb.toString(), MessageType.GHOST_MOVEMENT);
			for ( String address:this.screen.mp.getPlayerAdresses() ){
				if (!address.equals("localhost"))
					this.screen.game.server.sendMessage(address,m);
			}
		}
	}

	private void toGhostPositionString(StringBuilder sb, String name, Ghost ghost) {
		sb.append(name);
		sb.append(",");
		sb.append(ghost.position.x);
		sb.append(",");
		sb.append(ghost.position.y);
	}

	public void addPacman() {
		int x = (this.pacmans.size())*20;
		this.pacmans.add(new Pacman(Settings.PAC_INITIAL_POS_X+x,Settings.PAC_INITIAL_POS_Y,wallsLayer)); 
	}

	public void setControlledPacman(int pacmanIdx) {
		this.controlledPacman = pacmanIdx;

	}

	@Override
	public void listen(Message m) {
		String body = m.getBody();
		String[] movements = body.split(",");
		for (int i = 0; i < movements.length; i+=3){
			String name = movements[i];
			float x = Float.valueOf(movements[i+1]);
			float y = Float.valueOf(movements[i+2]);
			Ghost g = null;
			if(name.equals("BLINKY"))
				g = this.blinky;
			if(name.equals("PINKY"))
				g = this.pinky;
			if(name.equals("INKY"))
				g = this.inky;
			if(name.equals("CLYDE"))
				g = this.clyde;

			g.position.x = x;
			g.position.y = y;
		}
	}

}
