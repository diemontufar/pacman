package com.au.unimelb.comp90020;

import com.au.unimelb.comp90020.framework.util.Assets;
import com.au.unimelb.comp90020.screens.GameScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PacManGame extends Game {
	
	public SpriteBatch batcher;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batcher = new SpriteBatch();
		Assets.load();
		setScreen(new GameScreen(PacManGame.this));
	}
}
