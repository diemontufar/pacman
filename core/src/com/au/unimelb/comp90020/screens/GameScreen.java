package com.au.unimelb.comp90020.screens;

import java.util.Map.Entry;

import com.au.unimelb.comp90020.PacManGame;
import com.au.unimelb.comp90020.actors.Pacman.Movement;
import com.au.unimelb.comp90020.framework.World;
import com.au.unimelb.comp90020.framework.WorldListener;
import com.au.unimelb.comp90020.framework.WorldRenderer;
import com.au.unimelb.comp90020.framework.util.Assets;
import com.au.unimelb.comp90020.framework.util.Settings;
import com.au.unimelb.comp90020.multiplayer.networking.Message;
import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;
import com.au.unimelb.comp90020.multiplayer.networking.MessageListener;
import com.au.unimelb.comp90020.multiplayer.networking.Process;
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
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */

public class GameScreen extends ScreenAdapter implements TextInputListener, MessageListener {

	//Possible game states
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;

	/**
	 * Parent class
	 */
	public PacManGame game;

	/**
	 * Current state
	 */
	int state;
	/**
	 * OrtographicCamera to handle perspective 
	 */
	OrthographicCamera guiCam;
	/**
	 * 
	 */
	Vector3 touchPoint;
	/**
	 * World state object
	 */
	public World world;
	/**
	 * World listener object
	 */
	WorldListener worldListener;
	/**
	 * World renderer
	 */
	WorldRenderer renderer;
	/**
	 * Rectangles for pausing, resume, quit, continue, win and game over messages
	 */
	Rectangle resumeBounds, quitBounds, continueWin, playAgainGameOver, quitGameOver;
	/**
	 * Whether sound is active or not
	 */
	boolean toggleSound;

	/**
	 * 
	 */
	float elapsedSinceAnimation = 0.0f;
	/**
	 * Topology Process object
	 */
	public Process mp;


	/**
	 * Class constructor
	 * @param game The game running the screen
	 */
	public GameScreen(PacManGame game) {

		this.game = game;
		state = GAME_READY;

		guiCam = new OrthographicCamera(Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		guiCam.position.set(Settings.TARGET_WIDTH / 2, Settings.TARGET_HEIGHT / 2, 0);

		touchPoint = new Vector3();

		mp = (Process)game.lock;
		mp.addPlayer(Settings.getPID(),"localhost");

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

		world = new World(worldListener, this);
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

	/**
	 * Check if the game has finished 
	 */
	private void checkLevelEnd(){
		if (this.world.dots_eaten == Settings.MAX_NUM_DOTS)
			state = GAME_LEVEL_END;
	}

	/**
	 * Check if the game is over
	 */
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

	/**
	 * Draw a ready message before playing the game
	 */
	private void presentReady() {
		game.batcher.draw(Assets.readyMessage, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
	}

	/**
	 * Draw a paused message
	 */
	private void presentPaused() {
		game.batcher.draw(Assets.pauseMessage, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
	}

	/**
	 * Draw a level end message
	 */
	private void presentLevelEnd() {
		game.batcher.draw(Assets.endOfLevelMessage, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
	}

	/**
	 * Draw a game over message
	 */
	private void presentGameOver() {
		game.batcher.draw(Assets.gameOverMessage, 0, 0, Settings.TARGET_WIDTH, Settings.TARGET_HEIGHT);
		if (this.elapsedSinceAnimation > 0.5f){
			this.worldListener.playGameOver();
			this.elapsedSinceAnimation = 0.0f;
		}
	}


	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ScreenAdapter#pause()
	 */
	//Pause the game
	@Override
	public void pause() {
		if (state == GAME_RUNNING)
			state = GAME_PAUSED;
	}

	@Override
	public void input(String text) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Input.TextInputListener#canceled()
	 */
	@Override
	public void canceled() {
		if (state == GAME_RUNNING)
			state = GAME_OVER;
	}

	/* (non-Javadoc)
	 * @see com.au.unimelb.comp90020.multiplayer.networking.MessageListener#listen(com.au.unimelb.comp90020.multiplayer.networking.Message)
	 */
	@Override
	public void listen(Message m) {
		if (state == GAME_READY){				
			if (m.getType() == MessageType.JOIN ){
				String pid = m.getBody().split(",")[0];
				System.out.println("PID: "+pid);
				Long pidL = Long.valueOf(pid);
				if (pidL!=Settings.getPID() && !mp.getPlayers().containsKey(pidL)){
					world.addPacman(pidL);

					mp.addPlayer(pidL, m.getAddress());
					//Answer the JOIN with my curr address
					game.peer.sendMessage(m.getAddress(),new Message("localhost",String.valueOf(Settings.getPID()), MessageType.JOIN));
					//Multicast the current table					
					//StringBuilder sb = new StringBuilder();
					//for ( Long value : mp.getPlayers().keySet() ){
					//	sb.append(value);
					//	sb.append(",");
					//}
					//sb.deleteCharAt(sb.length()-1);
					//game.peer.broadcastMessage(new Message("localhost",sb.toString(),MessageType.PEERS));
				}
			}
			if(m.getType() == MessageType.PEERS){
				String[] pids = m.getBody().split(",");
				for(String pid : pids){
					Long pidL = Long.valueOf(pid);
					if (pidL!=Settings.getPID() && !mp.getPlayers().containsKey(pidL)){
						world.addPacman(pidL);
						mp.addPlayer(pidL,m.getAddress());
					}
				}			
			}
		}
		if(m.getType() == MessageType.DISCONNECT){
			boolean foundIt = false;
			for(Entry<Long, String> address : mp.getPlayers().entrySet()){
				if (address.getValue().equals(m.getAddress())){
					System.out.println("PID: "+address.getKey()+" Died");
					//world.removePacman(address.getKey()); //To remove the pacman if desired
					mp.removePlayer(address.getKey());
					foundIt = true;
					break;
				}
			}
			if (!foundIt)				
				System.out.println("not found!:"+m.getAddress());
		}
	}
}
