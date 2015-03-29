package com.au.unimelb.comp90020.screens;

import com.au.unimelb.comp90020.PacManGame;
import com.au.unimelb.comp90020.framework.World;
import com.au.unimelb.comp90020.framework.WorldListener;
import com.au.unimelb.comp90020.framework.WorldRenderer;
import com.au.unimelb.comp90020.framework.util.Assets;
import com.au.unimelb.comp90020.framework.util.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Screen where the game is performed. It receives the input from the player and
 * communicates it to the World.
 * 
 */
public class GameScreen extends ScreenAdapter implements TextInputListener {

	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	static final int GAME_LIFE_LOST = 5;

	PacManGame game;
	
	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle resumeBounds, quitBounds, continueWin, playAgainGameOver, quitGameOver;
	boolean toggleSound;
	int lastScore;
	String scoreString;

	public GameScreen(PacManGame game) {

		this.game = game;
		state = GAME_READY;

		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2, Settings.TARGET_HEIGHT / 2, 0);

		touchPoint = new Vector3();

		worldListener = new WorldListener() {

			@Override
			public void playWuaca() {
				Assets.playSound(Assets.wuacaSound);
			}

		};

		world = new World(worldListener);
		renderer = new WorldRenderer(game.batcher, world);

		scoreString = "SCORE: 0";

	}

	/**
	 * Method called each frame by OpenGL.
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime) {

		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
//			updatePaused();
			break;
		case GAME_LEVEL_END:
//			updateLevelEnd();
			break;
		case GAME_OVER:
//			updateGameOver();
			break;
		}
	}
	
	/**
	 * Method called every frame. It both updates and draw the World objects.
	 */
	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	/**
	 * Method called when the state is GAME_READY. If presses enter, the
	 * state changes to GAME_RUNNING.
	 */
	private void updateReady() {
		if (Gdx.input.isButtonPressed(Input.Keys.SPACE)) {
			Gdx.app.log("Message", "User pressed Enter");
			state = GAME_RUNNING;
		}
	}

	/**
	 * Updates the World objects based on the input of the player and the
	 * elapsed time provided that the state is GAME_RUNNING.
	 * 
	 * @param deltaTime
	 */
	private void updateRunning(float deltaTime) {

		float accel = 0;

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			world.pacman.moveRight();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			world.pacman.moveLeft();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			world.pacman.moveUp();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			world.pacman.moveDown();
		}
	}


	/**
	 * This method, in conjunction with update(), is called every frame. It
	 * draws the world depending on the state of the game.
	 */
	public void draw() {

		/* Render Objects in screen */
		GL20 gl = Gdx.gl;

//		gl.glViewport((int) game.viewport.x, (int) game.viewport.y, (int) game.viewport.width,
//				(int) game.viewport.height);

		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		
		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();

		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LIFE_LOST:
			presentLostLife();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}

		game.batcher.end();
	}

	private void presentReady() {

	}

	private void presentRunning() {

	}

	private void presentPaused() {

	}

	private void presentLostLife() {

	}

	private void presentLevelEnd() {
	}

	private void presentGameOver() {
	
	}


	@Override
	public void pause() {
		if (state == GAME_RUNNING)
			state = GAME_PAUSED;
	}

	@Override
	public void input(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}

}