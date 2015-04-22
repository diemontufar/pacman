package com.au.unimelb.comp90020.framework.util;


public class Settings {

	public static final int TARGET_WIDTH = 448;
	public static final int TARGET_HEIGHT = 540;
	public static final int MAX_LIVES = 3;
	public static final int MAX_NUM_DOTS = 244;
	
	
	public static final int PAC_INITIAL_POS_X = 225;
	public static final int PAC_INITIAL_POS_Y = 120;
	
	/* Set here default game properties */
	public static boolean musicEnabled = true;
	public static boolean soundEnabled = true;
	
	/*Networking configurations */
	public static final int PORT = 3030;
	
	//public static final String PEER_ADDRESS = "10.9.185.51";
	public static final String PEER_ADDRESS = "localhost";
	public static final int PEER_PORT = 3031;
	public static final String PEER_ADDRESS2 = "localhost";
	public static final int PEER_PORT2 = 3032;
	public static final String GROUP_ADDRESS = "224.0.0.3";
	public static final int CONN_TIMEOUT = 1000;
	
	public static long getPID() {
	    String processName =
	      java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
	    return Long.parseLong(processName.split("@")[0]);
	  }
}
