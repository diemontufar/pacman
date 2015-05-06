/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.util.Collection;
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
	private Map<Long, String> players;
	protected Long myId;
	private Long minId;
	private ProcessState state;
	GameMulticastPeer peer;
	
	public Process(GameMulticastPeer peer){
		this.numberOfPlayers = 0;
		this.players = new HashMap<Long, String>();
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
	public Map<Long, String> getPlayers() {
		return players;
	}
	public Collection<String> getPlayerAdresses() {
		return players.values();
	}

	public void setPlayers(Map<Long, String> players) {
		this.players = players;
	}
	public void addPlayer(Long pid, String address) {
		players.put(pid, address);
		if (pid<minId){
			minId = pid;
		}
		numberOfPlayers++;
	}
	public String getPlayerIds() {
		StringBuffer sb = new StringBuffer();
		for (Long key : this.players.keySet()){
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
		peer.broadcastMessage(m);
	}
	public void sendMsg(Long destId, MessageType type, int msg) {
		StringBuilder sb = new StringBuilder();
		sb.append(destId);
		sb.append(",");
		sb.append(msg);
		Message m = new Message(String.valueOf(myId), sb.toString(), type);
		
		peer.sendMessage(players.get(destId),m);
	}

	public synchronized void myWait(long milliseconds) {
		try {
			wait(milliseconds);
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}
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
