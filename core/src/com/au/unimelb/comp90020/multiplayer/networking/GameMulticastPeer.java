/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.au.unimelb.comp90020.framework.util.Settings;
import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * GameMulticasPeer handles all the networking low level socket handling
 * Receives messages and using an Observer pattern notifies the relevant objects of message arrivals
 * This class acts both as a Server and as Client in orer to implement a P2P architecture
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public class GameMulticastPeer extends Thread{
	/**
	 * Server socket
	 */
	ServerSocket serverSocket;
		
	/**
	 * Server port
	 */
	int serverPort;
	/**
	 * Peer client ports
	 */
	int[] peerPorts;
	/**
	 * Number of peers
	 */
	int numPeers;
	/**
	 * Ip addresses of the peers
	 */
	String[] peerAddresses;
	
	/**
	 * ServerClientSockets (when the Peer acts as Server)
	 */
	private Map<String,Socket> serverClients;
	/**
	 * ClientSockets to other Peers (when the Peer acts as Client)
	 */
	private Map<String,Socket> clients;
	/**
	 * Listener no notify on arrival of messages
	 */
	private Map<MessageType, ArrayList<MessageListener>> listeners;

	/**
	 * Class constructor
	 * @param serverPort Server Port
	 * @param numClients Number of peers
	 * @param peerAddresses Ip Addresses of the Peers
	 * @param peerPorts Ports of the Peers
	 * @throws IOException
	 */
	public GameMulticastPeer(int serverPort, int numClients, String[] peerAddresses, int[] peerPorts) throws IOException{
		this.listeners = new HashMap<MessageType, ArrayList<MessageListener>>();
		this.serverClients = new HashMap<String,Socket>();
		this.clients = new HashMap<String,Socket>();
		this.serverPort = serverPort;
		this.peerPorts = peerPorts;
		this.peerAddresses = peerAddresses;
		this.numPeers = numClients;
	}
	/**
	 * Initialize the server socket
	 */
	public void init(){		
		// Socket Server 
		ServerSocketHints serverSocketHint = new ServerSocketHints();
		serverSocketHint.acceptTimeout = 0;
		serverSocket = Gdx.net.newServerSocket(Protocol.TCP,serverPort, serverSocketHint);
	}

	//Start the server socket and begin to listen messages
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run(){
		init();
	        while (true){
				Socket socket = serverSocket.accept(null);
				serverClients.put(socket.getRemoteAddress(),socket);
				//Create a new thread per Client
	            new GameServerThread(socket,this).start();
	        }
	}
	/**
	 * Initialize and block until the Game succesfully establish connection with the Peers
	 */
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
						Thread.sleep(500); //Try again in 0.5 seconds
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Send a message to a specific Peer
	 * @param address Ip Address
	 * @param message Message to send
	 */
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
	/**
	 * Process and incoming message 
	 * @param address Remote Address
	 * @param line String with the raw data
	 */
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
			}
		}

	}

	/**
	 * Register a listener for a specific type of message
	 * @param type MessageType
	 * @param ml An object implementing MessageListener interface
	 */
	public void registerListener(MessageType type, MessageListener ml){
		if (this.listeners.get(type)==null){
			this.listeners.put(type, new ArrayList<MessageListener>());
		}
		this.listeners.get(type).add(ml);
	}

	/**
	 * Send the initial Join message
	 * @param message
	 */
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
	/**
	 * Broadcast a message to All peers
	 * @param message
	 */
	public void broadcastMessage(Message message) {
		if (message.getType() == MessageType.FOOD_EATEN){ //This is a delay used for simulating the race conditions
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
	/**
	 * Print the topology
	 * @param myId ProcessID
	 */
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
	/**
	 * Method to handle disconnection detection
	 * @param remoteAddress RemoteAddress of the Peer that failed
	 */
	public void clientDisconnect(String remoteAddress) {
		//Remove the client from my hashmap
		serverClients.remove(remoteAddress);
		for ( MessageListener m : listeners.get(MessageType.DISCONNECT) ){
			m.listen(new Message(remoteAddress,"",MessageType.DISCONNECT));
		}		
	}
}
