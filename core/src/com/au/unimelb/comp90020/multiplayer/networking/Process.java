/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.au.unimelb.comp90020.framework.util.Settings;
import com.au.unimelb.comp90020.multiplayer.networking.Message.MessageType;


/**
 * @author achaves
 *
 */
public class Process {
	
	public enum ProcessState {RELEASED,WANTED,HELD};
	
	private int numberOfPlayers;
	private Map<String, Long> players;
	protected Long myId;
	private Long minId;
	private ProcessState state;
	GameMulticastPeer peer;
	
	public Process(GameMulticastPeer peer){
		this.numberOfPlayers = 0;
		this.players = new HashMap<String, Long>();
		this.myId = Settings.getPID();
		this.minId = 1000000L;
		this.peer = peer;
	}
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	public Map<String, Long> getPlayers() {
		return players;
	}
	public Set<String> getPlayerAdresses() {
		return players.keySet();
	}

	public void setPlayers(Map<String, Long> players) {
		this.players = players;
	}
	public void addPlayer(String address, Long pid) {
		players.put(address, pid);
		if (pid<minId){
			minId = pid;
		}
		numberOfPlayers++;
	}
	public String getPlayerIds() {
		StringBuffer sb = new StringBuffer();
		for (String key : this.players.keySet()){
			sb.append(players.get(key));
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	public boolean isMinPlayerId() {
		return myId <= minId;
	}
	public Long getMyId() {
		// TODO Auto-generated method stub
		return myId;
	}
	public ProcessState getMyState() {
		return state;
	}

	public void setState(ProcessState state) {
		this.state = state;
	}
	public void broadcastMsg(MessageType type, int msg) {
		StringBuilder sb = new StringBuilder();
		sb.append(myId);
		sb.append(",");
		sb.append(msg);
		Message m = new Message("localhost", sb.toString(), type);
		peer.sendMessage(m);
	}
	public void sendMsg(Long destId, MessageType type, int msg) {
		StringBuilder sb = new StringBuilder();
		sb.append(destId);
		sb.append(",");
		sb.append(msg);
		Message m = new Message(String.valueOf(myId), sb.toString(), type);
		peer.sendMessage(m);
	}

	public synchronized void myWait() {
		try {
			wait();
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}
	
}
