/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;
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
	private Map<MessageType, ArrayList<MessageListener>> listeners;
	private Map<String,Socket> clients;
		
	public GameServer(){
		this.listeners = new HashMap<MessageType, ArrayList<MessageListener>>();
		this.clients = new HashMap<String,Socket>();
	}
	public void init(){
		ServerSocketHints serverSocketHint = new ServerSocketHints();
		serverSocketHint.acceptTimeout = 0;
		serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 3030, serverSocketHint);
	}
	public void registerListener(MessageType type, MessageListener ml){
		if (this.listeners.get(type)==null){
			this.listeners.put(type, new ArrayList<MessageListener>());
		}
		this.listeners.get(type).add(ml);
	}
	@Override
	public void run() {
		init();
		
		while(true){			
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
	public void sendMessage(String address, Message message){
		String msg = message.toProtocolString();
		System.out.println("SERVER>"+address+':'+msg);
		Socket socket = clients.get(address);
		try {
			socket.getOutputStream().write((msg+"\n").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void processMessage(String address, String line) {
		System.out.println("Processing message"+line);
		
		String mType = line.substring(0, line.indexOf(","));
		String body = line.substring(line.indexOf(",")+1);
		//System.out.println("Processing message"+mType+"/"+body);
		if ( mType.equals("JOIN") ){
			Message message = new Message(address, body, MessageType.JOIN);
			for ( MessageListener m : listeners.get(MessageType.JOIN) ){
				m.listen(message);
			}
		}
		if ( mType.equals("PACMAN_MOVEMENT") ){
			Message message = new Message(address, body, MessageType.PACMAN_MOVEMENT);
			for ( MessageListener m : listeners.get(MessageType.PACMAN_MOVEMENT) ){
				m.listen(message);
			}
		}
	}
	
}
