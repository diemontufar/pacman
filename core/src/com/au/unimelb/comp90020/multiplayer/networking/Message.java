package com.au.unimelb.comp90020.multiplayer.networking;

/**
 * Message represent a Message received or to be sent  
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public class Message {
	/**
	 * Ip Address
	 */
	private String address;
	/**
	 * Type of message
	 */
	private MessageType type;
	/**
	 * Message body
	 */
	private String body;

	/**
	 * Messages type supported
	 */
	public enum MessageType {JOIN, GHOST_MOVEMENT, PACMAN_MOVEMENT, FOOD_EATEN, PEERS, LOCK_REQUEST, LOCK_REPLY, DISCONNECT};
	
	
	/**
	 * Get the message type
	 * @return The message type
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * Set message type
	 * @param type The new type
	 */
	public void setType(MessageType type) {
		this.type = type;
	}

	/**
	 * Get message's body
	 * @return A message body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Set body message
	 * @param body THe new body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Class constructor
	 * @param address Ip Address
	 * @param body Message body
	 * @param type Message type
	 */
	public Message(String address, String body, MessageType type) {
		super();
		this.address = address;
		this.type = type;
		this.body = body;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return address+" ,"+type+","+body;
	}

	/**
	 * Format the mesage for sending it
	 * @return
	 */
	public String toProtocolString() {
		return type+","+body;
	}

	/**
	 * Get the address
	 * @return The address
	 */
	public String getAddress() {
		return address;
	}

}
