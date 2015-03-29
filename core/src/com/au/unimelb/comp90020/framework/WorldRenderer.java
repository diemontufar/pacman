package com.au.unimelb.comp90020.framework;

import com.au.unimelb.comp90020.actors.Button.ButtonSize;
import com.au.unimelb.comp90020.actors.Pacman;
import com.au.unimelb.comp90020.framework.util.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
	OrthogonalTiledMapRenderer mapRenderer;
	
	SpriteBatch batch;
	TiledMap map;
	TiledMapRenderer tiledMapRenderer;


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
		this.map = new TmxMapLoader().load("pacman.tmx");
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
	}

	/**
	 * Render background and world objects by separate.
	 */
	public void render() {
		
		cam.update();	
		batch.setProjectionMatrix(cam.combined);
		renderMap();
		renderObjects();
	}

	/**
	 * Render the background with blending deactivated (no transparency).
	 */
	public void renderMap() {
		batch.disableBlending();
		batch.begin();
		tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();
		batch.end();
	}

	/**
	 * Render the world objects with blending activated (transparency is
	 * needed).
	 */
	public void renderObjects() {
		batch.enableBlending();
		batch.begin();
//		renderPacdots();
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
		//See how we rendered bricks in breakbreaker
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
