/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.net.Socket;

/**
 * @author achaves
 *
 */
public class GameServerThread extends Thread {
	Socket socket;
	GameMulticastPeer server;
	Boolean running;

	public GameServerThread(Socket socket, GameMulticastPeer gameMulticastPeer) {
		this.socket = socket;
		this.server = gameMulticastPeer;
		this.running = true;
	}
	
	@Override
	public void run() {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while(running){
			try {            	
				String line = buffer.readLine();
				System.out.println(socket.getRemoteAddress()+" Says "+line);
				if ( line == null || line.equals("null") || !socket.isConnected()){//Disconnection
					System.out.println("Entreeeeeeeeeee");
					server.clientDisconnect(socket.getRemoteAddress());
					running = false;
				}
				else{
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
