/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.au.unimelb.comp90020.framework.util.Settings;
import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;
import com.au.unimelb.comp90020.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

/**
 * @author achaves
 *
 */
public class GameMulticastPeer extends Thread{
	Socket socket;
	ServerSocket serverSocket;
	
	InetAddress group;

	private Map<String,Socket> clients;
	private Map<MessageType, ArrayList<MessageListener>> listeners;

	public GameMulticastPeer() throws IOException{
		this.listeners = new HashMap<MessageType, ArrayList<MessageListener>>();
		this.clients = new HashMap<String,Socket>();
	}
	public void init(){		
		SocketHints socketHints = new SocketHints();
		// Socket Server 
		ServerSocketHints serverSocketHint = new ServerSocketHints();
		serverSocketHint.acceptTimeout = 0;
		serverSocket = Gdx.net.newServerSocket(Protocol.TCP, Settings.PORT, serverSocketHint);
	    // Socket Client
	    socketHints.connectTimeout = Settings.CONN_TIMEOUT;
		socket = Gdx.net.newClientSocket(Protocol.TCP, Settings.PEER_ADDRESS, Settings.PORT, socketHints);
	}

	@Override
	public void run(){
		init();
	    	Long pid = Settings.getPID();
	        // write our entered message to the stream
	        while (true){
				Socket socket = serverSocket.accept(null);
				 // Read data from the socket into a BufferedReader
	            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
	            clients.put(socket.getRemoteAddress(),socket);
	            try {            	
	            	String line = buffer.readLine();
	                System.out.println(socket.getRemoteAddress()+" Says "+line);
	                processMessage(socket.getRemoteAddress(),line);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            new GameServerThread(socket,this).start();
	        }
	}
	public void sendMessage(Message message){
        try {
        	String msg = message.toProtocolString();
        	System.out.println("SEND > "+msg);
			socket.getOutputStream().write((msg+"\n").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void processMessage(String address, String line) {
      System.out.println("RECV<"+line);

		String mType = line.substring(0, line.indexOf(","));
		String body = line.substring(line.indexOf(",")+1);
		//System.out.println("Processing message"+mType+"/"+body);
		Message message = null;
		if ( mType.equals("JOIN") ){
			message = new Message(address, body, MessageType.JOIN);
		}
		if ( mType.equals("PEERS") ){
			message = new Message(address, body, MessageType.PEERS);
		}
		if ( mType.equals("GHOST_MOVEMENT") ){
			message = new Message(address, body, MessageType.GHOST_MOVEMENT);
		}
		if ( mType.equals("PACMAN_MOVEMENT") ){
			message = new Message(address, body, MessageType.PACMAN_MOVEMENT);
		}
		if ( mType.equals("FOOD_EATEN") ){
			message = new Message(address, body, MessageType.FOOD_EATEN);
		}
		if ( mType.equals("LOCK_REQUEST") ){
			message = new Message(address, body, MessageType.LOCK_REQUEST);
		}
		if ( mType.equals("LOCK_REPLY") ){
			message = new Message(address, body, MessageType.LOCK_REPLY);
		}
		if (message!=null && listeners.get(message.getType())!=null){
			for ( MessageListener m : listeners.get(message.getType()) ){
				m.listen(message);
				//m.notify();
			}
		}

	}

	public void registerListener(MessageType type, MessageListener ml){
		if (this.listeners.get(type)==null){
			this.listeners.put(type, new ArrayList<MessageListener>());
		}
		this.listeners.get(type).add(ml);
	}

	public void sendJoin() {
		// TODO Auto-generated method stub
		
	}
}
