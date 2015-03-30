package com.au.unimelb.comp90020.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.au.unimelb.comp90020.actors.Button.ButtonSize;
import com.au.unimelb.comp90020.actors.Pacman;
import com.au.unimelb.comp90020.framework.util.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

/**
 * We were trying to apply MVC model, thus this class is the VIEW part whereas
 * the World is a kind of CONTROLLER. libGDX concepts like
 * Orthographic camera are managed within this class. We made the dimensions of
 * the World and the dimensions of the TARGET device coincide only for reasons
 * of convenience.
 */
public class WorldRenderer {

	World world;
	OrthographicCamera cam;
	OrthogonalTiledMapRenderer mapRenderer;
	
	SpriteBatch batch;
	TiledMapRenderer tiledMapRenderer;
	
	TiledMapTileSet tileset;
	ArrayList<TiledMapTileLayer.Cell> eyesCellsInScene;
    Map<String,TiledMapTile> eyesTiles;
	float elapsedSinceAnimation = 0.0f;

	/**
	 * Initialise the camera's position on the center of the FRUSTRUM.
	 * 
	 * @param batch
	 * @param world
	 */
	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false,w,h);
        this.cam.update();
		this.batch = batch;
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(this.world.map);
        
        
        // We created a second set of tiles for Water animations
        // For the record, this is bad for performance, use a single tileset if you can help it
        // Get a reference to the tileset named "PacMan"
        this.tileset =  this.world.map.getTileSets().getTileSet("PacMan");
        
        this.eyesTiles = new HashMap<String,TiledMapTile>();
        for(TiledMapTile tile:tileset){
            Object property = tile.getProperties().get("GhostEyes");
            if(property != null) {
                eyesTiles.put((String)property,tile);
            }
        }
        
        this.eyesCellsInScene = new ArrayList<TiledMapTileLayer.Cell>();
        TiledMapTileLayer layer = (TiledMapTileLayer) this.world.map.getLayers().get("Walls");
        for(int x = 0; x < layer.getWidth();x++){
            for(int y = 0; y < layer.getHeight();y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                Gdx.app.log("Current Position", "X: " + x + "; Y: " + y );
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
	 * Render background and world objects by separate.
	 */
	public void render() {
		
		cam.update();	
		batch.setProjectionMatrix(cam.combined);
		renderLayers();
		renderObjects();
	}
	
	/**
	 * Render the background with blending deactivated (no transparency).
	 */
	public void renderLayers() {
		batch.disableBlending();
		batch.begin();
		tiledMapRenderer.setView(cam);
//        tiledMapRenderer.render();
		tiledMapRenderer.render(this.world.wallsLayerIndex);
        tiledMapRenderer.render(this.world.objectsLayerIndex);
        tiledMapRenderer.render(this.world.collectablesLayerIndex);
        
        // Wait for half a second to elapse then call updateWaterAnimations
        // This could certainly be handled using an Action if you are using Scene2D
        elapsedSinceAnimation += Gdx.graphics.getDeltaTime();
        if(elapsedSinceAnimation > 0.5f){
            updateWaterAnimations();
            elapsedSinceAnimation = 0.0f;
        }
        
		batch.end();
	}
	
	// This is the function called every half a second to update the animated water tiles
    // Loop through all of the cells containing water.  Find the current frame and increment it
    // then update the cell's tile accordingly
    // NOTE!  This code depends on WaterFrame values being sequential in Tiled
    private void updateWaterAnimations(){
        for(TiledMapTileLayer.Cell cell : eyesCellsInScene){
            String property = (String) cell.getTile().getProperties().get("GhostEyes");
            Integer currentAnimationFrame = Integer.parseInt(property);
 
            currentAnimationFrame++;
            if(currentAnimationFrame > eyesTiles.size())
                currentAnimationFrame = 1;
 
            TiledMapTile newTile = eyesTiles.get(currentAnimationFrame.toString());
            cell.setTile(newTile);
        }
    }

	/**
	 * Render the world objects with blending activated (transparency is
	 * needed).
	 */
	public void renderObjects() {
		batch.enableBlending();
		batch.begin();
		renderPacdots();
		renderPacman();
//		renderGhosts();
//		renderPacBonuses();
//		renderScore();
//		renderSoundButton();
//		renderPauseButton();
//		renderLives();
//		renderLevelNumber();
		batch.end();
	}

	/**
	 * By using flags, the renderer draws the bonuses.
	 */
	private void renderPacBonuses() {

//		if (world.showBonus) {
//
//			Pacbonus bonus = world.coin;
//			batch.draw(Assets.coin, bonus.position.x - bonus.getType().getBonusWidth() / 2, bonus.position.y
//					- bonus.getType().getBonusHeight() / 2, bonus.getType().getBonusWidth(), bonus.getType()
//					.getBonusHeight());
//
//		}

	}
	
	private void renderPacdots() {
		
	}

	private void renderPacman() {

		Pacman pacman = world.pacman;
		batch.draw(Assets.close_right_pacman, pacman.position.x - Pacman.PACMAN_WIDTH / 2, pacman.position.y - Pacman.PACMAN_HEIGHT / 2,
				Pacman.PACMAN_WIDTH, Pacman.PACMAN_HEIGHT);
	}

	private void renderGhosts() {
		//See how we rendered bricks in breakbreaker
	}


	private void renderPauseButton() {

		int buttonWidth = ButtonSize.MEDIUM_SQUARE.getButtonWidth();
		int buttonHeight = ButtonSize.MEDIUM_SQUARE.getButtonHeight();

//		Button pauseButton = world.pauseButton;
//		batch.draw(Assets.pauseGame, pauseButton.position.x - buttonWidth / 2, pauseButton.position.y - buttonHeight
//				/ 2, buttonWidth, buttonHeight);

	}

	private void renderSoundButton() {

		int buttonWidth = ButtonSize.MEDIUM_SQUARE.getButtonWidth();
		int buttonHeight = ButtonSize.MEDIUM_SQUARE.getButtonHeight();
//		Button musicButton = world.soundButton;

//		if (!Settings.musicEnabled) {
//
//			batch.draw(Assets.soundGameOff, musicButton.position.x - buttonWidth / 2, musicButton.position.y
//					- buttonHeight / 2, buttonWidth, buttonHeight);
//		} else {
//			batch.draw(Assets.soundGameOn, musicButton.position.x - buttonWidth / 2, musicButton.position.y
//					- buttonHeight / 2, buttonWidth, buttonHeight);
//		}
	}

	private void renderScore() {
		//Assets.font.setScale(0.5f, 0.5f);
		//Assets.font.draw(batch, "SCORE: " + world.score, 5, World.WORLD_HEIGHT - 5);
	}

	private void renderLevelNumber() {
		//Assets.font.setScale(0.5f, 0.5f);
		// TODO: Review String object creation
		//Assets.font.draw(batch, "LEVEL: " + world.level, World.WORLD_WIDTH - 80, World.WORLD_HEIGHT - 5);
	}


	private void renderLives() {

		int buttonWidth = ButtonSize.SMALL_SQUARE.getButtonWidth();
		int buttonHeight = ButtonSize.SMALL_SQUARE.getButtonHeight();

		//Assets.font.setScale(0.5f, 0.5f);
//		Assets.font.draw(batch, "LIVES: ", 5, World.WORLD_HEIGHT - 20);
//
//		List<Button> lives = world.lives;
//		int len = lives.size();
//
//		for (int i = 0; i < len; i++) {
//			batch.draw(Assets.lives, lives.get(i).position.x - buttonWidth / 2, lives.get(i).position.y - buttonHeight
//					/ 2, buttonWidth, buttonHeight);
//		}
	}
}
