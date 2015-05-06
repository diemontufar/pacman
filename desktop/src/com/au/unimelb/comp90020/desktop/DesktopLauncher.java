package com.au.unimelb.comp90020.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.au.unimelb.comp90020.PacManGame;
import com.au.unimelb.comp90020.PacManGame.MultiplayerMode;
import com.au.unimelb.comp90020.framework.util.Settings;

public class DesktopLauncher {
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
