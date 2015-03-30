package com.au.unimelb.comp90020.framework.util;


public class Settings {

	public static final float VIEWPORT_WIDTH = 5.0f;
	public static final float VIEWPORT_HEIGHT = 5.0f;
	public static final int TARGET_WIDTH = 448;
	public static final int TARGET_HEIGHT = 496;
	public static final float ASPECT_RATIO = (float) TARGET_WIDTH / (float) TARGET_HEIGHT;
	
	/* Set here default game properties */
	public static boolean musicEnabled = true;
	public static boolean soundEnabled = true;
	public static boolean accelerometerEnabled = false;
	public static String playerDataFile = "brickbreaker.data";

}
