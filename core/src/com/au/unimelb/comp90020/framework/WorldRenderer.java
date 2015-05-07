package com.au.unimelb.comp90020.framework;

import com.au.unimelb.comp90020.actors.Ghost;
import com.au.unimelb.comp90020.actors.Pacman;
import com.au.unimelb.comp90020.actors.Pacman.Movement;
import com.au.unimelb.comp90020.framework.util.Assets;
import com.au.unimelb.comp90020.framework.util.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * WorldRenderer is the class that render the view in out MVC architectured Pacman.
 * libGDX concepts like Orthographic camera are managed within this class. We made the dimensions of
 * the World and the dimensions of the TARGET device coincide only for reasons
 * of convenience.
 */
public class WorldRenderer {

	/**
	 * World representation
	 */
	World world;
	/**
	 * Camera to manage perspective 
	 */
	OrthographicCamera cam;
	/**
	 * Batcher to draw on
	 */
	SpriteBatch batch;
	/**
	 * Renderer for the TiledMap
	 */
	TiledMapRenderer tiledMapRenderer;
	

	/**
	 * Class constructor
	 * Initialise the camera's position on the center of the FRUSTRUM.
	 * 
	 * @param batch Batcher to draw
	 * @param world World to render
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
		tiledMapRenderer.render(this.world.wallsLayerIndex);
        tiledMapRenderer.render(this.world.objectsLayerIndex);
        tiledMapRenderer.render(this.world.collectablesLayerIndex);
		batch.end();
	}
	

	/**
	 * Render the world objects with blending activated (transparency is
	 * needed).
	 */
	public void renderObjects() {
		batch.enableBlending();
		batch.begin();
		renderPacmans();
		renderGhosts();
		renderScore();
		renderLives();
		batch.end();
	}
	
	/**
	 * Render Pacman players
	 */
	private void renderPacmans() {
		for (Pacman pacman : world.pacmans.values() ){
			renderPacman(pacman);
		}
	}

	/**
	 * Render one Pacman
	 * @param pacman 
	 */
	private void renderPacman(Pacman pacman){
		TextureRegion currentKeyFrame;

		if (pacman.getCurrentState() == Movement.RIGTH){
			if (pacman.getStateTime() > 0.5f){
				currentKeyFrame = pacman.getPacmanAnimation()[3].getKeyFrame(pacman.getStateTime(), Animation.ANIMATION_LOOPING);	
				batch.draw(currentKeyFrame, pacman.position.x - Pacman.PACMAN_WIDTH / 2, pacman.position.y - Pacman.PACMAN_HEIGHT / 2,
						Pacman.PACMAN_WIDTH / 2,
						Pacman.PACMAN_HEIGHT / 2,
						Pacman.PACMAN_WIDTH, 
						Pacman.PACMAN_HEIGHT,1f,1f,90f,false); //rotate to the right
			}
		}
		
		if (pacman.getCurrentState() == Movement.LEFT){
			if (pacman.getStateTime() > 0.5f){
				currentKeyFrame = pacman.getPacmanAnimation()[2].getKeyFrame(pacman.getStateTime(), Animation.ANIMATION_LOOPING);	
				batch.draw(currentKeyFrame, pacman.position.x - Pacman.PACMAN_WIDTH / 2, pacman.position.y - Pacman.PACMAN_HEIGHT / 2,
						Pacman.PACMAN_WIDTH, 
						Pacman.PACMAN_HEIGHT);
			}
		}
		
		if (pacman.getCurrentState() == Movement.UP){
			if (pacman.getStateTime() > 0.5f){
				currentKeyFrame = pacman.getPacmanAnimation()[0].getKeyFrame(pacman.getStateTime(), Animation.ANIMATION_LOOPING);	
				batch.draw(currentKeyFrame, pacman.position.x - Pacman.PACMAN_WIDTH / 2, pacman.position.y - Pacman.PACMAN_HEIGHT / 2,
						Pacman.PACMAN_WIDTH / 2,
						Pacman.PACMAN_HEIGHT / 2,
						Pacman.PACMAN_WIDTH, 
						Pacman.PACMAN_HEIGHT,1f,1f,180f,false);
			}
		}
		
		if (pacman.getCurrentState() == Movement.DOWN){
			if (pacman.getStateTime() > 0.5f){
				currentKeyFrame = pacman.getPacmanAnimation()[1].getKeyFrame(pacman.getStateTime(), Animation.ANIMATION_LOOPING);	
				batch.draw(currentKeyFrame, pacman.position.x - Pacman.PACMAN_WIDTH / 2, pacman.position.y - Pacman.PACMAN_HEIGHT / 2,
						Pacman.PACMAN_WIDTH / 2,
						Pacman.PACMAN_HEIGHT / 2,
						Pacman.PACMAN_WIDTH, 
						Pacman.PACMAN_HEIGHT,1f,1f,180,true);
			}
		}
		
		if (pacman.getCurrentState() == Movement.NONE){
			batch.draw(pacman.getDefaultDirection(), pacman.position.x - Pacman.PACMAN_WIDTH / 2, pacman.position.y - Pacman.PACMAN_HEIGHT / 2,
					Pacman.PACMAN_WIDTH, 
					Pacman.PACMAN_HEIGHT);
		}
	}
	/**
	 * Draw the ghosts
	 */
	private void renderGhosts() {
		batch.draw(Assets.blinky, world.blinky.position.x - Ghost.GHOST_WIDTH / 2, world.blinky.position.y - Ghost.GHOST_HEIGHT / 2,
					Ghost.GHOST_WIDTH, Ghost.GHOST_HEIGHT);
		batch.draw(Assets.inky, world.inky.position.x - Ghost.GHOST_WIDTH / 2, world.inky.position.y - Ghost.GHOST_HEIGHT / 2,
				Ghost.GHOST_WIDTH, Ghost.GHOST_HEIGHT);
		batch.draw(Assets.clyde, world.clyde.position.x - Ghost.GHOST_WIDTH / 2, world.clyde.position.y - Ghost.GHOST_HEIGHT / 2,
				Ghost.GHOST_WIDTH, Ghost.GHOST_HEIGHT);
		batch.draw(Assets.pinky, world.pinky.position.x - Ghost.GHOST_WIDTH / 2, world.pinky.position.y - Ghost.GHOST_HEIGHT / 2,
				Ghost.GHOST_WIDTH, Ghost.GHOST_HEIGHT);
	}

	/**
	 * Draw the score
	 */
	private void renderScore() {
		Assets.font.setScale(0.5f, 0.5f);
		Assets.font.draw(batch, "SCORE: " + world.score, 10, Settings.TARGET_HEIGHT - 20);
	}

	/**
	 * Draw available lives
	 */
	private void renderLives() {

		Assets.font.setScale(0.5f, 0.5f);
		Assets.font.draw(batch, "LIVES: " + world.lives, Settings.TARGET_WIDTH - 100, Settings.TARGET_HEIGHT - 20);

	}
}
