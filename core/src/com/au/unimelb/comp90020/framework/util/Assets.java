package com.au.unimelb.comp90020.framework.util;

import com.au.unimelb.comp90020.framework.Animation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Handles all assets: Textures, sounds, music and Animations.
 * @author Diego
 *
 */
public class Assets {

	/* Textures - there must be less possible */
	public static TextureRegion gameBackground;
	public static Texture defaultBackground;
	public static Texture readyBackground;
	public static Texture pauseBackground;
	public static Texture gameOverBackground;
	public static Texture endOfLevelBackground;
	//public static Texture items; //Main Texture with game sprites
	public static Texture itemsPacman; //Main Texture with game sprites
	public static Texture itemsPacman2; //Main Texture with game sprites
	public static Texture itemsPacman3; //Main Texture with game sprites
	public static Texture itemsPacman4; //Main Texture with game sprites
	public static Texture itemsPacman5; //Main Texture with game sprites
	

	/*Message Screens*/
	public static TextureRegion errorMessage;
	public static TextureRegion infoMessage;
	public static TextureRegion gameOverMessage;
	public static TextureRegion readyMessage;
	public static TextureRegion pauseMessage;
	public static TextureRegion endOfLevelMessage;
	
	/* Transparent Screens*/
	public static TextureRegion defaultNotification;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion win;
	public static TextureRegion pauseMenu;
	
	/*Here declare game Textures*/
	public static TextureRegion p1_pacman_looking_right_1,p2_pacman_looking_right_1,p3_pacman_looking_right_1,p4_pacman_looking_right_1;
	public static TextureRegion p1_pacman_looking_right_2,p2_pacman_looking_right_2,p3_pacman_looking_right_2,p4_pacman_looking_right_2;
	public static TextureRegion p1_pacman_looking_right_3,p2_pacman_looking_right_3,p3_pacman_looking_right_3,p4_pacman_looking_right_3;
	public static TextureRegion p1_pacman_looking_left_1,p2_pacman_looking_left_1,p3_pacman_looking_left_1,p4_pacman_looking_left_1;
	public static TextureRegion p1_pacman_looking_left_2,p2_pacman_looking_left_2,p3_pacman_looking_left_2,p4_pacman_looking_left_2;
	public static TextureRegion p1_pacman_looking_left_3,p2_pacman_looking_left_3,p3_pacman_looking_left_3,p4_pacman_looking_left_3;
	public static TextureRegion p1_pacman_looking_up_1,p2_pacman_looking_up_1,p3_pacman_looking_up_1,p4_pacman_looking_up_1;
	public static TextureRegion p1_pacman_looking_up_2,p2_pacman_looking_up_2,p3_pacman_looking_up_2,p4_pacman_looking_up_2;
	public static TextureRegion p1_pacman_looking_up_3,p2_pacman_looking_up_3,p3_pacman_looking_up_3,p4_pacman_looking_up_3;
	public static TextureRegion p1_pacman_looking_down_1,p2_pacman_looking_down_1,p3_pacman_looking_down_1,p4_pacman_looking_down_1;
	public static TextureRegion p1_pacman_looking_down_2,p2_pacman_looking_down_2,p3_pacman_looking_down_2,p4_pacman_looking_down_2;
	public static TextureRegion p1_pacman_looking_down_3,p2_pacman_looking_down_3,p3_pacman_looking_down_3,p4_pacman_looking_down_3;
	public static TextureRegion blinky,pinky,inky,clyde;
	
	
	/*Here declare buttons*/
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion musicOn;
	public static TextureRegion musicOff;
	public static TextureRegion quit;
	public static TextureRegion back;
	public static TextureRegion pause;
	public static TextureRegion settings;
	public static TextureRegion help;
	public static TextureRegion soundGameOn;
	public static TextureRegion soundGameOff;
	public static TextureRegion pauseGame;

	/*Here declare Screens*/
	public static TextureRegion defaultScreen;
	public static TextureRegion helpScreen;
	public static TextureRegion menuScreen;
	public static TextureRegion optionScreen;
	public static TextureRegion scoresScreen;
	public static TextureRegion splashScreen;
	
	/* BitmapFonts */
	public static BitmapFont font;

	/* Animations */
	public static Animation p1_pacmanRight,p2_pacmanRight,p3_pacmanRight,p4_pacmanRight;
	public static Animation p1_pacmanLeft,p2_pacmanLeft,p3_pacmanLeft,p4_pacmanLeft;
	public static Animation p1_pacmanUp,p2_pacmanUp,p3_pacmanUp,p4_pacmanUp;
	public static Animation p1_pacmanDown,p2_pacmanDown,p3_pacmanDown,p4_pacmanDown;
	
	/* Sounds & Music */
	public static Music music;

	public static Sound openingSound;
	public static Sound wuacaSound;
	public static Sound lifeLostSound;
	public static Sound gameOverSound;
	public static Sound winnerSound;

	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	@SuppressWarnings("static-access")
	public static Texture loadTransparentTexture(int width, int height,
			String file) {

		Pixmap mask = new Pixmap(width, height, Pixmap.Format.Alpha);
		mask.fillRectangle(0, 0, width, height);

		Pixmap fg = new Pixmap(Gdx.files.internal(file));
		fg.drawPixmap(mask, fg.getWidth(), fg.getHeight());
		mask.setBlending(Pixmap.Blending.SourceOver);

		Texture texture = new Texture(fg);

		return texture;
	}

	public static void load() {
		
		readyBackground = loadTexture("backgrounds/ready.png");
		readyMessage = new TextureRegion(readyBackground, 0, 0, 896, 1080);
		
		pauseBackground = loadTexture("backgrounds/pause.png");
		pauseMessage = new TextureRegion(pauseBackground, 0, 0, 896, 1080);
		
		endOfLevelBackground = loadTexture("backgrounds/completed.png");
		endOfLevelMessage = new TextureRegion(endOfLevelBackground, 0, 0, 896, 1080);
		
		gameOverBackground = loadTexture("backgrounds/gameover.png");
		gameOverMessage = new TextureRegion(gameOverBackground, 0, 0, 896, 1080);
		

		/*Here you should put all textures that comes from items.png*/
		itemsPacman = loadTexture("textures/ChomperSprites.png");
		itemsPacman2 = loadTexture("PacMan.png");
		itemsPacman3 = loadTexture("PacMan2.png");
		itemsPacman4 = loadTexture("PacMan3.png");
		itemsPacman5 = loadTexture("PacMan4.png");
		
		/* Player 1 */
		p1_pacman_looking_right_1 = new TextureRegion(itemsPacman2, 0, 48, 32, 32);
		p1_pacman_looking_right_2 = new TextureRegion(itemsPacman2, 32, 48, 32, 32);
		p1_pacman_looking_right_3 = new TextureRegion(itemsPacman2, 96, 48, 32, 32);
		
		p1_pacman_looking_left_1 = new TextureRegion(itemsPacman2, 0, 48, 32, 32);
		p1_pacman_looking_left_2 = new TextureRegion(itemsPacman2, 32, 48, 32, 32);
		p1_pacman_looking_left_3 = new TextureRegion(itemsPacman2, 96, 48, 32, 32);
		
		p1_pacman_looking_up_1 = new TextureRegion(itemsPacman2, 0, 48, 32, 32);
		p1_pacman_looking_up_2 = new TextureRegion(itemsPacman2, 32, 48, 32, 32);
		p1_pacman_looking_up_3 = new TextureRegion(itemsPacman2, 96, 48, 32, 32);
		
		p1_pacman_looking_down_1 = new TextureRegion(itemsPacman2, 0, 48, 32, 32);
		p1_pacman_looking_down_2 = new TextureRegion(itemsPacman2, 32, 48, 32, 32);
		p1_pacman_looking_down_3 = new TextureRegion(itemsPacman2, 96, 48, 32, 32);
		

		p1_pacmanRight = new Animation(1/3f, p1_pacman_looking_right_1, p1_pacman_looking_right_2, p1_pacman_looking_right_3);
		p1_pacmanLeft = new Animation(1/3f, p1_pacman_looking_left_1, p1_pacman_looking_left_2,p1_pacman_looking_left_3);
		p1_pacmanUp = new Animation(1/3f, p1_pacman_looking_up_1, p1_pacman_looking_up_2,p1_pacman_looking_up_3);
		p1_pacmanDown = new Animation(1/3f, p1_pacman_looking_down_1, p1_pacman_looking_down_2,p1_pacman_looking_down_3);
		
		/* Player 2 */
		p2_pacman_looking_right_1 = new TextureRegion(itemsPacman3, 0, 48, 32, 32);
		p2_pacman_looking_right_2 = new TextureRegion(itemsPacman3, 32, 48, 32, 32);
		p2_pacman_looking_right_3 = new TextureRegion(itemsPacman3, 96, 48, 32, 32);
		
		p2_pacman_looking_left_1 = new TextureRegion(itemsPacman3, 0, 48, 32, 32);
		p2_pacman_looking_left_2 = new TextureRegion(itemsPacman3, 32, 48, 32, 32);
		p2_pacman_looking_left_3 = new TextureRegion(itemsPacman3, 96, 48, 32, 32);
		
		p2_pacman_looking_up_1 = new TextureRegion(itemsPacman3, 0, 48, 32, 32);
		p2_pacman_looking_up_2 = new TextureRegion(itemsPacman3, 32, 48, 32, 32);
		p2_pacman_looking_up_3 = new TextureRegion(itemsPacman3, 96, 48, 32, 32);
		
		p2_pacman_looking_down_1 = new TextureRegion(itemsPacman3, 0, 48, 32, 32);
		p2_pacman_looking_down_2 = new TextureRegion(itemsPacman3, 32, 48, 32, 32);
		p2_pacman_looking_down_3 = new TextureRegion(itemsPacman3, 96, 48, 32, 32);
		

		p2_pacmanRight = new Animation(1/3f, p2_pacman_looking_right_1, p2_pacman_looking_right_2, p2_pacman_looking_right_3);
		p2_pacmanLeft = new Animation(1/3f, p2_pacman_looking_left_1, p2_pacman_looking_left_2,p2_pacman_looking_left_3);
		p2_pacmanUp = new Animation(1/3f, p2_pacman_looking_up_1, p2_pacman_looking_up_2,p2_pacman_looking_up_3);
		p2_pacmanDown = new Animation(1/3f, p2_pacman_looking_down_1, p2_pacman_looking_down_2,p2_pacman_looking_down_3);
		
		/* Player 3 */
		p3_pacman_looking_right_1 = new TextureRegion(itemsPacman4, 0, 48, 32, 32);
		p3_pacman_looking_right_2 = new TextureRegion(itemsPacman4, 32, 48, 32, 32);
		p3_pacman_looking_right_3 = new TextureRegion(itemsPacman4, 96, 48, 32, 32);
		
		p3_pacman_looking_left_1 = new TextureRegion(itemsPacman4, 0, 48, 32, 32);
		p3_pacman_looking_left_2 = new TextureRegion(itemsPacman4, 32, 48, 32, 32);
		p3_pacman_looking_left_3 = new TextureRegion(itemsPacman4, 96, 48, 32, 32);
		
		p3_pacman_looking_up_1 = new TextureRegion(itemsPacman4, 0, 48, 32, 32);
		p3_pacman_looking_up_2 = new TextureRegion(itemsPacman4, 32, 48, 32, 32);
		p3_pacman_looking_up_3 = new TextureRegion(itemsPacman4, 96, 48, 32, 32);
		
		p3_pacman_looking_down_1 = new TextureRegion(itemsPacman4, 0, 48, 32, 32);
		p3_pacman_looking_down_2 = new TextureRegion(itemsPacman4, 32, 48, 32, 32);
		p3_pacman_looking_down_3 = new TextureRegion(itemsPacman4, 96, 48, 32, 32);
		

		p3_pacmanRight = new Animation(1/3f, p3_pacman_looking_right_1, p3_pacman_looking_right_2, p3_pacman_looking_right_3);
		p3_pacmanLeft = new Animation(1/3f, p3_pacman_looking_left_1, p3_pacman_looking_left_2,p3_pacman_looking_left_3);
		p3_pacmanUp = new Animation(1/3f, p3_pacman_looking_up_1, p3_pacman_looking_up_2,p3_pacman_looking_up_3);
		p3_pacmanDown = new Animation(1/3f, p3_pacman_looking_down_1, p3_pacman_looking_down_2,p3_pacman_looking_down_3);
		
		/* Player 4 */
		p4_pacman_looking_right_1 = new TextureRegion(itemsPacman5, 0, 48, 32, 32);
		p4_pacman_looking_right_2 = new TextureRegion(itemsPacman5, 32, 48, 32, 32);
		p4_pacman_looking_right_3 = new TextureRegion(itemsPacman5, 96, 48, 32, 32);
		
		p4_pacman_looking_left_1 = new TextureRegion(itemsPacman5, 0, 48, 32, 32);
		p4_pacman_looking_left_2 = new TextureRegion(itemsPacman5, 32, 48, 32, 32);
		p4_pacman_looking_left_3 = new TextureRegion(itemsPacman5, 96, 48, 32, 32);
		
		p4_pacman_looking_up_1 = new TextureRegion(itemsPacman5, 0, 48, 32, 32);
		p4_pacman_looking_up_2 = new TextureRegion(itemsPacman5, 32, 48, 32, 32);
		p4_pacman_looking_up_3 = new TextureRegion(itemsPacman5, 96, 48, 32, 32);
		
		p4_pacman_looking_down_1 = new TextureRegion(itemsPacman5, 0, 48, 32, 32);
		p4_pacman_looking_down_2 = new TextureRegion(itemsPacman5, 32, 48, 32, 32);
		p4_pacman_looking_down_3 = new TextureRegion(itemsPacman5, 96, 48, 32, 32);
		

		p4_pacmanRight = new Animation(1/3f, p4_pacman_looking_right_1, p4_pacman_looking_right_2, p4_pacman_looking_right_3);
		p4_pacmanLeft = new Animation(1/3f, p4_pacman_looking_left_1, p4_pacman_looking_left_2,p4_pacman_looking_left_3);
		p4_pacmanUp = new Animation(1/3f, p4_pacman_looking_up_1, p4_pacman_looking_up_2,p4_pacman_looking_up_3);
		p4_pacmanDown = new Animation(1/3f, p4_pacman_looking_down_1, p4_pacman_looking_down_2,p4_pacman_looking_down_3);
		
		
		blinky = new TextureRegion(itemsPacman, 0, 0, 30, 30);
		pinky = new TextureRegion(itemsPacman, 60, 0, 30, 30);
		inky = new TextureRegion(itemsPacman, 125, 0, 30, 30);
		clyde = new TextureRegion(itemsPacman, 190, 0, 30, 30);	
		
		/* BitmapFont*/
		 FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
		 Gdx.files.internal("fonts/font.ttf"));
		 FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		 parameter.size = 50;
		 font = generator.generateFont(parameter); 
		 font.setColor(new Color(Color.GRAY));
		 generator.dispose();
		
		wuacaSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wuaca.mp3"));
		lifeLostSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lifelost.mp3"));
		openingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/opening.mp3"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.mp3"));
	}

	/**
	 * Play sound
	 * @param sound
	 */
	public static void playSound(Sound sound) {
		if (Settings.soundEnabled)
			sound.play(1);
	}
	
}
