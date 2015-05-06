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
import java.util.Map.Entry;

import com.au.unimelb.comp90020.framework.util.Settings;
import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;
import com.au.unimelb.comp90020.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * @author achaves
 *
 */
public class GameMulticastPeer extends Thread{
	ServerSocket serverSocket;
	
	InetAddress group;

	int serverPort;
	int[] peerPorts;
	int numPeers;
	String[] peerAddresses;
	
	private Map<String,Socket> serverClients;
	private Map<String,Socket> clients;
	private Map<MessageType, ArrayList<MessageListener>> listeners;

	public GameMulticastPeer(int serverPort, int numClients, String[] peerAddresses, int[] peerPorts) throws IOException{
		this.listeners = new HashMap<MessageType, ArrayList<MessageListener>>();
		this.serverClients = new HashMap<String,Socket>();
		this.clients = new HashMap<String,Socket>();
		this.serverPort = serverPort;
		this.peerPorts = peerPorts;
		this.peerAddresses = peerAddresses;
		this.numPeers = numClients;
	}
	public void init(){		
		// Socket Server 
		ServerSocketHints serverSocketHint = new ServerSocketHints();
		serverSocketHint.acceptTimeout = 0;
		serverSocket = Gdx.net.newServerSocket(Protocol.TCP,serverPort, serverSocketHint);
	}

	@Override
	public void run(){
		init();
	    	Long pid = Settings.getPID();
	        // write our entered message to the stream
	        while (true){
				Socket socket = serverSocket.accept(null);
				serverClients.put(socket.getRemoteAddress(),socket);
				
	            new GameServerThread(socket,this).start();
	        }
	}
	public void startClients(){
	    // Socket Client
		for (int i = 0; i < this.numPeers; i++){
			SocketHints socketHints = new SocketHints();
			String address = this.peerAddresses[i];
			int clientPort = this.peerPorts[i];
			socketHints.connectTimeout = Settings.CONN_TIMEOUT;
			boolean isConnected = false;
			while (!isConnected){
				Socket socket = null;
				try{	    
					System.out.println("Trying to connect to: "+address+":"+clientPort);
					socket = Gdx.net.newClientSocket(Protocol.TCP, address, clientPort, socketHints);
					isConnected = true;
					clients.put(socket.getRemoteAddress(), socket);
					new GameServerThread(socket,this).start();
				}
				catch(GdxRuntimeException e){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public void sendMessage(String address,Message message){
		if ( message.getType() == MessageType.FOOD_EATEN || message.getType() == MessageType.LOCK_REPLY || message.getType()== MessageType.LOCK_REQUEST)
			System.out.println("SEND > "+message.toProtocolString());

        try {
        	String msg = message.toProtocolString();
        	String ipAddr = address.substring(0, address.indexOf(':'));
        	for (Entry<String, Socket> e : clients.entrySet()){
        		if(e.getKey().contains(ipAddr)){
        			Socket socket = e.getValue();
        			socket.getOutputStream().write((msg+"\n").getBytes());
        		}
        	}
        		
        	//System.out.println("SEND > "+msg);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	synchronized void  processMessage(String address, String line) {
		if (line.contains("FOOD") || line.contains("LOCK"))
			System.out.println("RECV "+address+"<"+line);

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

	public void sendJoin(Message message) {
		for (Socket s : clients.values()){
			String msg = message.toProtocolString();
        	//System.out.println("SEND "+s.getRemoteAddress()+"> "+msg);
			try {
				s.getOutputStream().write((msg+"\n").getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	}
	public void broadcastMessage(Message message) {
		if (message.getType() == MessageType.FOOD_EATEN){
			try {
			    Thread.sleep(100);               
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		if ( message.getType() == MessageType.FOOD_EATEN || message.getType() == MessageType.LOCK_REPLY || message.getType()== MessageType.LOCK_REQUEST)
			System.out.println("BSEND > "+message.toProtocolString());

		for (Socket s : clients.values()){
			String msg = message.toProtocolString();
        	//System.out.println("BSEND > "+msg);
			try {
				s.getOutputStream().write((msg+"\n").getBytes());
			} catch (java.net.SocketException e){
				//Client gone
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	public void printRouteTable(Long myId) {
		System.out.println("---------------------------------------------_");
		System.out.println("Routing table: "+myId);
		System.out.println("---------------------------------------------_");
		System.out.println("Client Sockets ");
		
		for (String key:clients.keySet()){
			Socket s = clients.get(key);
			System.out.println(key+":"+s.getRemoteAddress());
		}
		System.out.println("Server Sockets ");
		for (String key:serverClients.keySet()){
			Socket s = serverClients.get(key);
			System.out.println(key+":"+s.getRemoteAddress());
		}
	}
	public void clientDisconnect(String remoteAddress) {
		//Remove the client from my hashmap
		serverClients.remove(remoteAddress);
		for ( MessageListener m : listeners.get(MessageType.DISCONNECT) ){
			m.listen(new Message(remoteAddress,"",MessageType.DISCONNECT));
		}		
	}
}
