/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.au.unimelb.comp90020.framework.util.Settings;
import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;


/**
 * Process handle the topology of the multipeer game. Is the parent class of all the Mutex implementations
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public class Process {
	
	/**
	 * Possible status regarding the critical section
	 */
	public enum ProcessState {RELEASED,WANTED,HELD};
	
	/**
	 * Number of players
	 */
	private int numberOfPlayers;
	/**
	 * A map PID-IpAddress of players
	 */
	private Map<Long, String> players;
	/**
	 * Process ID
	 */
	protected Long myId;
	/**
	 * What is the minimum PID between all peers
	 */
	private Long minId;
	/**
	 * Current status regarding the critical section
	 */
	private ProcessState state;
	/**
	 * Peer Networking Class
	 */
	GameMulticastPeer peer;
	
	/**
	 * Class constructor
	 * @param peer The networking low level handler
	 */
	public Process(GameMulticastPeer peer){
		this.numberOfPlayers = 0;
		this.players = new HashMap<Long, String>();
		this.myId = Settings.getPID();
		this.minId = 1000000L;
		this.peer = peer;
	}
	/**
	 * Get the number of players
	 * @return The number of players
	 */
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	/**
	 * @param numberOfPlayers
	 */
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	/**
	 * Get the map of players in the game
	 * @return The map of Pid-IpAddress of the players
	 */
	public Map<Long, String> getPlayers() {
		return players;
	}
	/**
	 * Get Player ip addresses
	 * @return Ip addresses of the players
	 */
	public Collection<String> getPlayerAdresses() {
		return players.values();
	}

	/**
	 * Add a player to the Game
	 * @param pid Process Id
	 * @param address Remote Address
	 */
	public void addPlayer(Long pid, String address) {
		players.put(pid, address);
		if (pid<minId){
			minId = pid;
		}
		numberOfPlayers++;
	}
	/**
	 * Get a comma separated string with all the process IDs of the peers
	 * @return A string with the PIDs
	 */
	public String getPlayerIds() {
		StringBuffer sb = new StringBuffer();
		for (Long key : this.players.keySet()){
			sb.append(players.get(key));
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	/**
	 * Test if the current process id is the lower ranked in the multiplayer group
	 * @return True if the pid is the minimum between the group, false otherwise
	 */
	public boolean isMinPlayerId() {
		return myId <= minId;
	}
	/**
	 * Ger my current PID
	 * @return Current PID
	 */
	public Long getMyId() {
		return myId;
	}
	/**
	 * Get my current Lock state
	 * @return The current state
	 */
	public ProcessState getMyState() {
		return state;
	}

	/**
	 * Change the Lock state
	 * @param state The new state
	 */
	public void setState(ProcessState state) {
		this.state = state;
	}
	/**
	 * Broadcast a message to all Peers
	 * @param type Type of message
	 * @param msg Message body
	 */
	public void broadcastMsg(MessageType type, int msg) {
		StringBuilder sb = new StringBuilder();
		sb.append(myId);
		sb.append(",");
		sb.append(msg);
		Message m = new Message("localhost", sb.toString(), type);
		peer.broadcastMessage(m);
	}
	/**
	 * Send a message to an specific Peer
	 * @param destId PID of the destination
	 * @param type Type of message
	 * @param msg Message body
	 */
	public void sendMsg(Long destId, MessageType type, int msg) {
		StringBuilder sb = new StringBuilder();
		sb.append(destId);
		sb.append(",");
		sb.append(msg);
		Message m = new Message(String.valueOf(myId), sb.toString(), type);
		
		peer.sendMessage(players.get(destId),m);
	}

	/**
	 * Block a number of milliseconds waiting for a lock reply
	 * @param milliseconds
	 */
	public synchronized void myWait(long milliseconds) {
		try {
			wait(milliseconds);
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}
	/**
	 * Remove a player from the topology. This is for fault tolerance
	 * @param key PID of the Peer in fail
	 */
	public void removePlayer(Long key) {
		players.remove(key);
		this.numberOfPlayers--;
		long minId = 0;
		for (Long pid : this.players.keySet()){
			if (minId == 0)
				minId = pid;
			else if (pid<minId)
				minId = pid;
		}
		this.minId = minId;
	}
	
}
