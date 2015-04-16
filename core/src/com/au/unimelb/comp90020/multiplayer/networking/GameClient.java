/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.au.unimelb.comp90020.framework.util.Settings;
import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

/**
 * @author achaves
 *
 */
public class GameClient extends Thread{

	Socket socket;
	private Map<MessageType, ArrayList<MessageListener>> listeners =new HashMap<MessageType, ArrayList<MessageListener>>();
	 
	public void init(){
	    //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
		SocketHints socketHints = new SocketHints();
	    // Socket will time our in 4 seconds
	    socketHints.connectTimeout = 4000;

		socket = Gdx.net.newClientSocket(Protocol.TCP, "localhost", 3030, socketHints);
	}
	@Override
	public void run() {
	    try {
	    	init();
	    	Long pid = Settings.getPID();
	        // write our entered message to the stream
	    	System.out.println("CLIENT > JOIN,"+pid);
	    	StringBuffer sb = new StringBuffer();
	    	sb.append("JOIN,");sb.append(pid);sb.append("\n");
	        socket.getOutputStream().write(sb.toString().getBytes());
	        while (true){
	        	BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        	String line = buffer.readLine();
	        	System.out.println("SERVER < "+line);
	        	processMessage(socket.getRemoteAddress(),line);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	private void processMessage(String address, String line) {
		String mType = line.substring(0, line.indexOf(","));
		String body = line.substring(line.indexOf(",")+1);
		if ( mType.equals("JOIN") ){
			Message message = new Message(address, body, MessageType.JOIN);
			for ( MessageListener m : listeners.get(MessageType.JOIN) ){
				m.listen(message);
			}
		}
		if ( mType.equals("GHOST_MOVEMENT")){
			Message message = new Message(address, body, MessageType.GHOST_MOVEMENT);
			for ( MessageListener m : listeners.get(MessageType.GHOST_MOVEMENT) ){
				m.listen(message);
			}
		}
	}
	public void registerListener(MessageType type, MessageListener ml){
		if (this.listeners.get(type)==null){
			this.listeners.put(type, new ArrayList<MessageListener>());
		}
		this.listeners.get(type).add(ml);
	}
}
