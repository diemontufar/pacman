package com.au.unimelb.comp90020.framework;

import com.au.unimelb.comp90020.actors.Button.ButtonSize;
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
 * We were trying to apply MVC model, thus this class is the VIEW part whereas
 * the World is a kind of CONTROLLER. libGDX concepts like
 * Orthographic camera are managed within this class. We made the dimensions of
 * the World and the dimensions of the TARGET device coincide only for reasons
 * of convenience.
 */
public class WorldRenderer {

	World world;
	OrthographicCamera cam;
	SpriteBatch batch;
	TiledMapRenderer tiledMapRenderer;
	TextureRegion oldPacmanDirection;
	

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
        this.oldPacmanDirection = Assets.pacman_looking_right_open;
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
		renderPacman();
		renderGhosts();
//		renderPacBonuses();
		renderScore();
//		renderSoundButton();
//		renderPauseButton();
		renderLives();
//		renderLevelNumber();
		batch.end();
	}
	
	private void renderPacman() {

		Pacman pacman = world.pacman;
		
		TextureRegion currentKeyFrame;

		if (pacman.getCurrentState() == Movement.RIGTH){
			if (pacman.getStateTime() > 0.5f){
				currentKeyFrame = Assets.pacmanRight.getKeyFrame(pacman.getStateTime(), Animation.ANIMATION_LOOPING);	
				batch.draw(currentKeyFrame, pacman.position.x - Pacman.PACMAN_WIDTH / 2, pacman.position.y - Pacman.PACMAN_HEIGHT / 2,
						Pacman.PACMAN_WIDTH, Pacman.PACMAN_HEIGHT);
			}
			oldPacmanDirection = Assets.pacman_looking_right_open;
		}
		
		if (pacman.getCurrentState() == Movement.LEFT){
			if (pacman.getStateTime() > 0.5f){
				currentKeyFrame = Assets.pacmanLeft.getKeyFrame(pacman.getStateTime(), Animation.ANIMATION_LOOPING);	
				batch.draw(currentKeyFrame, pacman.position.x - Pacman.PACMAN_WIDTH / 2, pacman.position.y - Pacman.PACMAN_HEIGHT / 2,
						Pacman.PACMAN_WIDTH, Pacman.PACMAN_HEIGHT);
			}
			oldPacmanDirection = Assets.pacman_looking_left_open;
		}
		
		if (pacman.getCurrentState() == Movement.UP){
			if (pacman.getStateTime() > 0.5f){
				currentKeyFrame = Assets.pacmanUp.getKeyFrame(pacman.getStateTime(), Animation.ANIMATION_LOOPING);	
				batch.draw(currentKeyFrame, pacman.position.x - Pacman.PACMAN_WIDTH / 2, pacman.position.y - Pacman.PACMAN_HEIGHT / 2,
						Pacman.PACMAN_WIDTH, Pacman.PACMAN_HEIGHT);
			}
			oldPacmanDirection = Assets.pacman_looking_up_open;
		}
		
		if (pacman.getCurrentState() == Movement.DOWN){
			if (pacman.getStateTime() > 0.5f){
				currentKeyFrame = Assets.pacmanDown.getKeyFrame(pacman.getStateTime(), Animation.ANIMATION_LOOPING);	
				batch.draw(currentKeyFrame, pacman.position.x - Pacman.PACMAN_WIDTH / 2, pacman.position.y - Pacman.PACMAN_HEIGHT / 2,
						Pacman.PACMAN_WIDTH, Pacman.PACMAN_HEIGHT);
			}
			oldPacmanDirection = Assets.pacman_looking_down_open;
		}
		
		if (pacman.getCurrentState() == Movement.NONE){
			batch.draw(oldPacmanDirection, pacman.position.x - Pacman.PACMAN_WIDTH / 2, pacman.position.y - Pacman.PACMAN_HEIGHT / 2,
					Pacman.PACMAN_WIDTH, Pacman.PACMAN_HEIGHT);
		}
		
	}

	private void renderGhosts() {
		//See how we rendered bricks in breakbreaker
		batch.draw(Assets.blinky, world.blinky.position.x - Ghost.GHOST_WIDTH / 2, world.blinky.position.y - Ghost.GHOST_HEIGHT / 2,
					Ghost.GHOST_WIDTH, Ghost.GHOST_HEIGHT);
		batch.draw(Assets.inky, world.inky.position.x - Ghost.GHOST_WIDTH / 2, world.inky.position.y - Ghost.GHOST_HEIGHT / 2,
				Ghost.GHOST_WIDTH, Ghost.GHOST_HEIGHT);
		batch.draw(Assets.clyde, world.clyde.position.x - Ghost.GHOST_WIDTH / 2, world.clyde.position.y - Ghost.GHOST_HEIGHT / 2,
				Ghost.GHOST_WIDTH, Ghost.GHOST_HEIGHT);
		batch.draw(Assets.pinky, world.pinky.position.x - Ghost.GHOST_WIDTH / 2, world.pinky.position.y - Ghost.GHOST_HEIGHT / 2,
				Ghost.GHOST_WIDTH, Ghost.GHOST_HEIGHT);
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
		Assets.font.setScale(0.5f, 0.5f);
		Assets.font.draw(batch, "SCORE: " + world.score, 10, Settings.TARGET_HEIGHT - 20);
	}

	private void renderLevelNumber() {
		//Assets.font.setScale(0.5f, 0.5f);
		// TODO: Review String object creation
		//Assets.font.draw(batch, "LEVEL: " + world.level, World.WORLD_WIDTH - 80, World.WORLD_HEIGHT - 5);
	}


	private void renderLives() {

		Assets.font.setScale(0.5f, 0.5f);
		Assets.font.draw(batch, "LIVES: " + world.lives, Settings.TARGET_WIDTH - 90, Settings.TARGET_HEIGHT - 20);

	}
}
