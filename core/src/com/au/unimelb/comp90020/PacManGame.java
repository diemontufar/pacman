package com.au.unimelb.comp90020;

import java.io.IOException;

import com.au.unimelb.comp90020.framework.util.Assets;
import com.au.unimelb.comp90020.framework.util.Settings;
import com.au.unimelb.comp90020.multiplayer.concurrency.Lock;
import com.au.unimelb.comp90020.multiplayer.concurrency.RAMutex;
import com.au.unimelb.comp90020.multiplayer.networking.GameMulticastPeer;
import com.au.unimelb.comp90020.multiplayer.networking.Message;
import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;
import com.au.unimelb.comp90020.screens.GameScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * PacManGame is the main Game controller
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 *
 */
public class PacManGame extends Game {
	
	/**
	 * Multiplayer enumeration
	 */
	public enum MultiplayerMode {none, multicast};

	/**
	 * SpriteBatcher to draw the game
	 */
	public SpriteBatch batcher;
	/**
	 * Mode of game
	 */
	public MultiplayerMode mode;
	
	/**
	 * GameMulticastPeer object to handle networking in P2P schema
	 */
	public GameMulticastPeer peer;
	
	/**
	 * Locking object
	 */
	public Lock lock;
	
	/**
	 * Class constructor
	 * @param mode Game type
	 */
	public PacManGame(MultiplayerMode mode) {
		this.mode = mode;		
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batcher = new SpriteBatch();
		Assets.load();
		if (mode == MultiplayerMode.multicast){
			try {
				//Start the networking object with the topology defined in Settings class
				peer =  new GameMulticastPeer(Settings.SERVERPORT,Settings.NUMCLIENTS, Settings.PEER_ADDRESSES, Settings.PEER_PORTS);
				//Initialize the lock with the networking Peer
				lock = new RAMutex(peer);
				//Register the different objects to the different messages
				peer.registerListener(MessageType.LOCK_REQUEST, lock); //Lock Messages go to the Lock object
				peer.registerListener(MessageType.LOCK_REPLY, lock);
				//Instantiate the Game screen
				GameScreen gs = new GameScreen(PacManGame.this);
				
				peer.registerListener(MessageType.JOIN, gs); //JOIN, PEERS and DISCONNECT messages are handled by the Game Screen 
				peer.registerListener(MessageType.PEERS, gs);
				peer.registerListener(MessageType.DISCONNECT, gs);
				peer.registerListener(MessageType.GHOST_MOVEMENT, gs.world); //Ghost movement, pacman movement and food eaten are handled by world
				peer.registerListener(MessageType.PACMAN_MOVEMENT, gs.world);
				peer.registerListener(MessageType.FOOD_EATEN, gs.world);
				
				
				peer.start(); //Start the server
				peer.startClients(); //Start the clients

				//peer.printRouteTable(gs.mp.getMyId());
				peer.sendJoin(new Message("localhost",String.valueOf(Settings.getPID()),MessageType.JOIN));//Send first JOIN
				//All good, render the game screen!
				setScreen(gs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
