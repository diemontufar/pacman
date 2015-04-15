package com.au.unimelb.comp90020.screens;

import com.au.unimelb.comp90020.PacManGame;
import com.au.unimelb.comp90020.actors.Pacman.Movement;
import com.au.unimelb.comp90020.framework.World;
import com.au.unimelb.comp90020.framework.WorldListener;
import com.au.unimelb.comp90020.framework.WorldRenderer;
import com.au.unimelb.comp90020.framework.util.Assets;
import com.au.unimelb.comp90020.framework.util.Settings;
import com.au.unimelb.comp90020.multiplayer.networking.Message;
import com.au.unimelb.comp90020.multiplayer.networking.MessageListener;
import com.au.unimelb.comp90020.multiplayer.networking.MultiPlayerProperties;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
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
public class GameScreen extends ScreenAdapter implements TextInputListener, MessageListener {

	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;

	PacManGame game;
	
	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle resumeBounds, quitBounds, continueWin, playAgainGameOver, quitGameOver;
	boolean toggleSound;
	
	float elapsedSinceAnimation = 0.0f;
	///
	MultiPlayerProperties mp;
	///

	public GameScreen(PacManGame game) {

		this.game = game;
		state = GAME_READY;

		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2, Settings.TARGET_HEIGHT / 2, 0);

		touchPoint = new Vector3();
		
		///
		mp = new MultiPlayerProperties();
		///

		worldListener = new WorldListener() {

			@Override
			public void playWuaca() {
				Assets.playSound(Assets.wuacaSound);
			}

			@Override
			public void playLifeLost() {
				Assets.playSound(Assets.lifeLostSound);
			}

			@Override
			public void playOpening() {
				Assets.playSound(Assets.openingSound);
			}

			@Override
			public void playGameOver() {
				Assets.playSound(Assets.gameOverSound);
			}

		};

		world = new World(worldListener);
		renderer = new WorldRenderer(game.batcher, world);

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
			updatePaused();
			break;
		case GAME_LEVEL_END:
			checkLevelEnd();
			break;
		case GAME_OVER:
			checkGameOver();
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
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)){
			this.worldListener.playOpening();
			state = GAME_RUNNING;
		}
	}
	
	/**
	 * Method called when the state of the game is GAME_PAUSED. Resumes the game
	 * or returns to the MenuScreen depending on which button was pressed.
	 */
	private void updatePaused() {
		if (Gdx.input.isKeyJustPressed(Keys.ENTER))
			state = GAME_RUNNING;
	}
	
	private void checkLevelEnd(){
		if (this.world.dots_eaten == Settings.MAX_NUM_DOTS)
		state = GAME_LEVEL_END;
	}
	
	private void checkGameOver(){
		if (this.world.lives == 0){
			state = GAME_OVER;
		}
	}


	/**
	 * Updates the World objects based on the input of the player and the
	 * elapsed time provided that the state is GAME_RUNNING.
	 * 
	 * @param deltaTime
	 */
	private void updateRunning(float deltaTime) {
		System.out.println("Starting game with: "+mp.getNumberOfPlayers()+" players");

		Movement move = Movement.NONE; //If you want pacman to move alone do not initialize move variable :)
		this.elapsedSinceAnimation += deltaTime;

		//Check Pacman movement
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			move = Movement.RIGTH;
			if (this.elapsedSinceAnimation > 0.7f){
				this.worldListener.playWuaca();
				this.elapsedSinceAnimation = 0.0f;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			move = Movement.LEFT;
			if (this.elapsedSinceAnimation > 0.7f){
				this.worldListener.playWuaca();
				this.elapsedSinceAnimation = 0.0f;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			move = Movement.UP;
			if (this.elapsedSinceAnimation > 0.7f){
				this.worldListener.playWuaca();
				this.elapsedSinceAnimation = 0.0f;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			move = Movement.DOWN;
			if (this.elapsedSinceAnimation > 0.7f){
				this.worldListener.playWuaca();
				this.elapsedSinceAnimation = 0.0f;
			}
		}
		
		world.update(deltaTime,move);
		
		checkLevelEnd();
		checkGameOver();
	}


	/**
	 * This method, in conjunction with update(), is called every frame. It
	 * draws the world depending on the state of the game.
	 */
	public void draw() {

		/* Render Objects in screen */
		GL20 gl = Gdx.gl;
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
		case GAME_PAUSED:
			presentPaused();
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
		game.batcher.draw(Assets.readyMessage, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
	}

	private void presentPaused() {
		game.batcher.draw(Assets.pauseMessage, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
	}

	private void presentLevelEnd() {
		game.batcher.draw(Assets.endOfLevelMessage, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
	}

	private void presentGameOver() {
		game.batcher.draw(Assets.gameOverMessage, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		if (this.elapsedSinceAnimation > 0.5f){
			this.worldListener.playGameOver();
			this.elapsedSinceAnimation = 0.0f;
		}
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
		if (state == GAME_RUNNING)
			state = GAME_OVER;
	}

	@Override
	public void listen(Message m) {
		if (state == GAME_READY){
			mp.setNumberOfPlayers(mp.getNumberOfPlayers()+1);
		}
	}
}