/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

/**
 * @author achaves
 *
 */
public class Message {
	private String address;
	private MessageType type;
	private String body;

	public enum MessageType {JOIN, GHOST_MOVEMENT, PACMAN_MOVEMENT, FOOD_EATEN, PEERS, LOCK_REQUEST, LOCK_REPLY, DISCONNECT};
	
	
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Message(String address, String body, MessageType type) {
		super();
		this.address = address;
		this.type = type;
		this.body = body;
	}
	
	public String toString(){
		return address+" ,"+type+","+body;
	}

	public String toProtocolString() {
		return type+","+body;
	}

	public String getAddress() {
		// TODO Auto-generated method stub
		return address;
	}

}
