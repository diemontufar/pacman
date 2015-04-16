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
	GameServer server;

	public GameServerThread(Socket socket, GameServer gs) {
		this.socket = socket;
		this.server = gs;
	}

	@Override
	public void run() {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while(true){
			try {            	
				String line = buffer.readLine();
				//System.out.println(socket.getRemoteAddress()+" Says "+line);
				server.processMessage(socket.getRemoteAddress(),line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
