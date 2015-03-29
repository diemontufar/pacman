package com.au.unimelb.comp90020.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.au.unimelb.comp90020.PacManGame;
import com.au.unimelb.comp90020.framework.util.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Collaborative PacMan - Distributed Algorithms";
		config.height = Settings.TARGET_HEIGHT;
		config.width = Settings.TARGET_WIDTH;
		new LwjglApplication(new PacManGame(), config);
	}
}
