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

import com.au.unimelb.comp90020.framework.util.Settings;
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

	private Map<MessageType, ArrayList<MessageListener>> listeners;

	public GameMulticastPeer() throws IOException{
		this.listeners = new HashMap<MessageType, ArrayList<MessageListener>>();
		socket = new MulticastSocket(Settings.PORT);
		group = InetAddress.getByName(Settings.GROUP_ADDRESS);
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
		DatagramPacket messageOut =  new DatagramPacket(m,  m.length, group, Settings.PORT);
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
