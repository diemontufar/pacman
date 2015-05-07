package com.au.unimelb.comp90020.framework.util;


/**
 * Class that represents all the Game settings
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public class Settings {

	/**
	 * Initial Game width 
	 */
	public static final int TARGET_WIDTH = 448;
	/**
	 * Initial Game height
	 */
	public static final int TARGET_HEIGHT = 540;
	/**
	 * Maximum lives of the player 
	 */
	public static final int MAX_LIVES = 3;
	/**
	 * Maximum number of dots in the game
	 */
	public static final int MAX_NUM_DOTS = 236;
	
	
	/**
	 * Initial X position of Pacman
	 */
	public static final int PAC_INITIAL_POS_X = 225;
	/**
	 * Initial Y position of Pacman
	 */
	public static final int PAC_INITIAL_POS_Y = 120;
	
	/**
	 * If music is enabled by default 
	 */
	public static boolean musicEnabled = true;
	/**
	 * If sound is enabled by default
	 */
	public static boolean soundEnabled = true;
	
	/**
	 * Game listening server port 
	 */
	public static final int SERVERPORT = 3030;
	/**
	 * Number of Peers in the game
	 */
	public static final int NUMCLIENTS = 2;
	
	/**
	 * Array with Peer's ips, one for each number of clients
	 */
	public static final String[] PEER_ADDRESSES = {"10.9.204.144","10.9.190.147"};
	//public static final String[] PEER_ADDRESSES = {"localhost","localhost"};
	/**
	 * Array with Peer's ports, one for each number of clients
	 */
	public static final int[] PEER_PORTS = {3031,3032};
	
	/**
	 * Maximum timeout to declare a Peer dead
	 */
	public static final int CONN_TIMEOUT = 1000;
	
	/**
	 * Get the process ID assigned by the operating system
	 * @return A long with the process ID
	 */
	public static long getPID() {
	    String processName =
	      java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
	    return Long.parseLong(processName.split("@")[0]);
	  }
}
