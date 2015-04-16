/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;
import com.au.unimelb.comp90020.screens.GameScreen;
import com.badlogic.gdx.net.Socket;

/**
 * @author achaves
 *
 */
public class GameMulticastPeer extends Thread{
	MulticastSocket socket = null;
	
	InetAddress group;
	int port = 3030;
	
	private Map<MessageType, ArrayList<MessageListener>> listeners;

	public GameMulticastPeer() throws IOException{
		this.listeners = new HashMap<MessageType, ArrayList<MessageListener>>();
		socket = new MulticastSocket(3030);
		group = InetAddress.getByName("224.0.0.3");
		socket.joinGroup(group);
	}
	
	@Override
	public void run(){

	    try {
		
		while (true){
			byte[] buffer = new byte[1000];
			DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
			socket.receive(messageIn);
			String msg = new String(messageIn.getData()).trim();
			//System.out.println("Received:" + messageIn.getAddress() + " - " + msg);
			processMessage(messageIn.getAddress().toString(),msg);
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void sendMessage(Message message){
		String msg = message.toProtocolString();
		System.out.println("SEND>"+msg);
		byte[] m = (msg).getBytes();
		DatagramPacket messageOut =  new DatagramPacket(m,  m.length, group, port);
		try {
			socket.send(messageOut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void processMessage(String address, String line) {
		System.out.println("RECV<"+line);
		
		String mType = line.substring(0, line.indexOf(","));
		String body = line.substring(line.indexOf(",")+1);
		//System.out.println("Processing message"+mType+"/"+body);
		if ( mType.equals("JOIN") ){
			Message message = new Message(address, body, MessageType.JOIN);
			for ( MessageListener m : listeners.get(MessageType.JOIN) ){
				m.listen(message);
			}
		}
		if ( mType.equals("PEERS") ){
			Message message = new Message(address, body, MessageType.PEERS);
			for ( MessageListener m : listeners.get(MessageType.PEERS) ){
				m.listen(message);
			}
		}
		if ( mType.equals("GHOST_MOVEMENT") ){
			Message message = new Message(address, body, MessageType.GHOST_MOVEMENT);
			for ( MessageListener m : listeners.get(MessageType.GHOST_MOVEMENT) ){
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

	public void registerListener(MessageType type, MessageListener ml){
		if (this.listeners.get(type)==null){
			this.listeners.put(type, new ArrayList<MessageListener>());
		}
		this.listeners.get(type).add(ml);
	}
}
