/**
 * 
 */
package com.au.unimelb.comp90020.multiplayer.networking;

import java.util.HashMap;
import java.util.Map;


/**
 * @author achaves
 *
 */
public class MultiPlayerProperties {
	private int numberOfPlayers;
	private Map<String, Integer> players;
	
	public MultiPlayerProperties(){
		this.numberOfPlayers = 1;
		this.players = new HashMap<String, Integer>();
		players.put("localhost", 1);
	}
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	public Map<String, Integer> getPlayers() {
		return players;
	}
	public void setPlayers(Map<String, Integer> players) {
		this.players = players;
	}
	
}
