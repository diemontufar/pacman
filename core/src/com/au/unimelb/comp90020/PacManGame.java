package com.au.unimelb.comp90020;

import com.au.unimelb.comp90020.framework.World;
import com.au.unimelb.comp90020.framework.WorldListener;
import com.au.unimelb.comp90020.framework.WorldRenderer;
import com.au.unimelb.comp90020.framework.util.Assets;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PacManGame extends Game {
	SpriteBatch batch;
	Texture texture;
	Sprite sprite;
	
	World worldController;
	WorldRenderer worldRenderer;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batch = new SpriteBatch();
		Assets.load();
		WorldListener listener = new WorldListener(){};
		worldController = new World(listener);
		
		
		worldRenderer= new WorldRenderer(batch, worldController);
	}

	@Override
	public void render () {
		worldController.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(139, 139, 137, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		worldRenderer.render();
	}

}
