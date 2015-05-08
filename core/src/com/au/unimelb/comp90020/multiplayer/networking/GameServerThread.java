/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.net.Socket;

/**
 * Thread for handling a specific Client Socket
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public class GameServerThread extends Thread {
	/**
	 * Socket
	 */
	Socket socket;
	/**
	 * Parent class
	 */
	GameMulticastPeer server;
	/**
	 * Whether the thread is running
	 */
	Boolean running;

	/**
	 * Class creator
	 * @param socket The socket to listen to
	 * @param gameMulticastPeer Parent class
	 */
	public GameServerThread(Socket socket, GameMulticastPeer gameMulticastPeer) {
		this.socket = socket;
		this.server = gameMulticastPeer;
		this.running = true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while(running){
			try {            	
				String line = buffer.readLine();
				if ( line == null || line.equals("null") || !socket.isConnected()){//Disconnection
					server.clientDisconnect(socket.getRemoteAddress());
					running = false;
				}
				else{ //Handle message
					server.processMessage(socket.getRemoteAddress(),line);	
				}
			} catch (IOException e) {
				server.clientDisconnect(socket.getRemoteAddress());
				running = false;
				e.printStackTrace();
			}
		}
	}

}
