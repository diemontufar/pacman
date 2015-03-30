package com.au.unimelb.comp90020.framework.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
	//public static Texture items; //Main Texture with game sprites
	public static Texture itemsPacman; //Main Texture with game sprites

	/*Message Screens*/
	public static TextureRegion errorMessage;
	public static TextureRegion infoMessage;
	
	/* Transparent Screens*/
	public static TextureRegion defaultNotification;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion win;
	public static TextureRegion pauseMenu;
	
	/*Here declare game Textures*/
	public static TextureRegion close_right_pacman;
	public static TextureRegion open_right_pacman;
	public static TextureRegion close_left_pacman;
	public static TextureRegion open_left_pacman;
	public static TextureRegion close_up_pacman;
	public static TextureRegion open_up_pacman;
	public static TextureRegion close_down_pacman;
	public static TextureRegion open_down_pacman;
	public static TextureRegion green_pacman;
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
	public static Animation pacmanRight;
	public static Animation pacmanLeft;
	public static Animation pacmanUp;
	public static Animation pacmanDown;
	
	/* Sounds & Music */
	public static Music music;

	public static Sound wuacaSound;
	public static Sound bonusSound;
	public static Sound dieSound;
	public static Sound lifeLostSound;
	public static Sound gameOverSound;
	public static Sound winnerSound;
	public static Sound lifeBonusSound;
	public static Sound coinBonusSound;
	public static Sound badBonusSound;
	public static Sound clickSound;
	public static Sound toggleSound;

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
		
		/*Here declare screen backgrounds*/
		//defaultBackground = loadTexture("backgrounds/screens/default_background.png");
		//defaultScreen = new TextureRegion(defaultBackground, 0, 0, 800, 1280);
		
//		menuBackground = loadTexture("backgrounds/screens/screen_menu.png");
//		menuScreen = new TextureRegion(menuBackground, 0, 0, 800, 1280);
//		
//		splashBackground = loadTexture("backgrounds/screens/screen_splash.png");
//		splashScreen = new TextureRegion(splashBackground, 0, 0, 800, 1280);
//		
//		background = loadTexture("backgrounds/background.png");
//		gameBackground = new TextureRegion(background, 0, 0, 800, 1280);
//		
//		errorMessageB = loadTexture("backgrounds/errorBackground.png");
//		errorMessage = new TextureRegion(errorMessageB, 0, 0, 800, 1280);
//		
//		infoMessageB = loadTexture("backgrounds/infoBackground.png");
//		infoMessage = new TextureRegion(infoMessageB, 0, 0, 800, 1280);
		
		
		/*Here you should put all textures that comes from items.png*/
//		items = loadTexture("textures/items.png");
		itemsPacman = loadTexture("textures/ChomperSprites.png");
		close_right_pacman = new TextureRegion(itemsPacman, 320, 0, 30, 30);
		open_right_pacman = new TextureRegion(itemsPacman, 350, 0, 30, 30);
		close_down_pacman = new TextureRegion(itemsPacman, 320, 30, 30, 30);
		open_down_pacman = new TextureRegion(itemsPacman, 350, 30, 30, 30);
		close_left_pacman = new TextureRegion(itemsPacman, 320, 60, 30, 30);
		open_left_pacman = new TextureRegion(itemsPacman, 350, 60, 30, 30);
		close_up_pacman = new TextureRegion(itemsPacman, 320, 90, 30, 30);
		open_up_pacman = new TextureRegion(itemsPacman, 350, 90, 30, 30);
		
		pacmanRight = new Animation(0.05f, close_right_pacman, open_right_pacman);
		pacmanLeft = new Animation(0.05f, close_left_pacman, open_left_pacman);
		pacmanUp = new Animation(0.05f, close_up_pacman, open_up_pacman);
		pacmanDown = new Animation(0.05f, close_down_pacman, open_down_pacman);
		
//		yellow_pacman = 
//		green_pacman = 
//		blinky =
//		pinky =
//		inky =
//		clyde =

		
		/*Here you should put all textures that comes from itemsButtons.png*/
		/*musicOn = new TextureRegion(items, 0, 512, 128, 128);
		musicOff = new TextureRegion(items, 128, 512, 128, 128);
		soundOn = new TextureRegion(items, 384, 512, 128, 128);
		soundOff = new TextureRegion(items, 512, 512, 128, 128);
		quit = new TextureRegion(items, 640, 512, 128, 128);
		
		settings = new TextureRegion(items, 0, 640, 128, 128);
		pause = new TextureRegion(items, 128, 640, 128, 128);
		back = new TextureRegion(items, 256, 640, 128, 128);
		help = new TextureRegion(items, 384, 640, 128, 128);

		soundGameOff = new TextureRegion(items, 128, 769, 128, 128);
		soundGameOn = new TextureRegion(items, 256, 769, 128, 128);
		pauseGame = new TextureRegion(items, 384, 769, 128, 128);*/
		
		/*Here load Transparent Textures*/
		/*Texture defaultNotificationR = loadTransparentTexture(320, 480, "backgrounds/default_notification.png");
		defaultNotification = new TextureRegion(defaultNotificationR, 0, 0, 320, 480);

		Texture readyR = loadTransparentTexture(320, 480, "backgrounds/ready.png");
		ready = new TextureRegion(readyR, 0, 0, 320, 480);
		
		Texture winR = loadTransparentTexture(800, 1280, "backgrounds/win.png");
		win = new TextureRegion(winR, 0, 0, 800, 1280);

		Texture gameOverR = loadTransparentTexture(800, 1280, "backgrounds/gameover.png");
		gameOver = new TextureRegion(gameOverR, 0, 0, 800, 1280);

		Texture pauseMenuR = loadTransparentTexture(800, 1280, "backgrounds/pausemenu.png");
		pauseMenu = new TextureRegion(pauseMenuR, 0, 0, 800, 1280);*/		
		
		/* BitmapFont*/
		/* FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
		 Gdx.files.internal("fonts/font.ttf"));
		 FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		 parameter.size = 40;
		 font = generator.generateFont(parameter); 
		 font.setColor(new Color(Color.PURPLE));
		 generator.dispose();
		
		music = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);

		if (Settings.musicEnabled){
			music.play();
		}else{
			music.pause();
		}
		
		lifeLostSound = Gdx.audio.newSound(Gdx.files.internal("sound/lifeLost.ogg"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sound/gameOverSound.ogg"));
		winnerSound = Gdx.audio.newSound(Gdx.files.internal("sound/winnerSound.wav"));
		lifeBonusSound = Gdx.audio.newSound(Gdx.files.internal("sound/getLifeBonus.ogg"));
		coinBonusSound = Gdx.audio.newSound(Gdx.files.internal("sound/coin.wav"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sound/click.wav"));
		toggleSound = Gdx.audio.newSound(Gdx.files.internal("sound/toggle.ogg"));*/
		
		wuacaSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wuaca.mp3"));
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
