/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

/**
 * @author achaves
 *
 */
public class GameClient extends Thread{

	@Override
	public void run() {
		SocketHints socketHints = new SocketHints();
	    // Socket will time our in 4 seconds
	    socketHints.connectTimeout = 4000;
	    //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
	    Socket socket = Gdx.net.newClientSocket(Protocol.TCP, "localhost", 3030, socketHints);
	    try {
	        // write our entered message to the stream
	    	System.out.println("CLIENT > JOIN");
	        socket.getOutputStream().write("JOIN,ABC\n".getBytes());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
