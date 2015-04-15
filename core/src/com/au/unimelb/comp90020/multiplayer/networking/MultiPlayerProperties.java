/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author achaves
 *
 */
public class MultiPlayerProperties {
	private int numberOfPlayers;
	private Map<String, Long> players;
	
	public MultiPlayerProperties(){
		this.numberOfPlayers = 1;
		this.players = new HashMap<String, Long>();
		players.put("localhost", (long) 1);
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
	
}
