/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.au.unimelb.comp90020.framework.util.Settings;


/**
 * @author achaves
 *
 */
public class MultiPlayerProperties {
	private int numberOfPlayers;
	private Map<String, Long> players;
	private Long myId;
	private Long minId;
	
	public MultiPlayerProperties(){
		this.numberOfPlayers = 1;
		this.players = new HashMap<String, Long>();
		this.myId = Settings.getPID();
		this.minId = 1000000L;
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
	
}
