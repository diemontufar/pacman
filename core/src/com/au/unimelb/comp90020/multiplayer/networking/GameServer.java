/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;


/**
 * @author achaves
 *
 */
public class GameServer extends Thread{
	ServerSocket serverSocket;
	
	public GameServer(){
		
	}
	public void init(){
		ServerSocketHints serverSocketHint = new ServerSocketHints();
		serverSocketHint.acceptTimeout = 0;
		serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 3030, serverSocketHint);
	}
	@Override
	public void run() {
		init();
		
		while(true){			
			Socket socket = serverSocket.accept(null);
			 // Read data from the socket into a BufferedReader
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            
            try {
                // Read to the next newline (\n) and display that text on labelMessage
                System.out.println(buffer.readLine());    
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
	}
	
}
