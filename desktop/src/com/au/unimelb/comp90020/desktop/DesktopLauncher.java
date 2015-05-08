package com.au.unimelb.comp90020.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.au.unimelb.comp90020.PacManGame;
import com.au.unimelb.comp90020.PacManGame.MultiplayerMode;
import com.au.unimelb.comp90020.framework.util.Settings;

/**
 * Helper class to launch the game in Desktop mode  
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public class DesktopLauncher {
	/**
	 * Main method
	 * @param arg Whether the game is in multiplayer or not
	 */
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Collaborative PacMan - Distributed Algorithms";
		config.height = Settings.TARGET_HEIGHT;
		config.width = Settings.TARGET_WIDTH;
		config.backgroundFPS = 30;
		config.foregroundFPS = 30;
		
		if (arg.length == 0){
		      new LwjglApplication(new PacManGame(MultiplayerMode.none), config);
		}
		else{
			String mode = arg[0];
			if(mode.equals("multicast")){
				new LwjglApplication(new PacManGame(MultiplayerMode.multicast), config);
			}
		}
		
	}
}
