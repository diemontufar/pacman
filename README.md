# Multiplayer PacMan
COMP90020: Distributed Algorithms project

##1. Clone

Clone this respository in your local machine.

[Tutorial](https://help.github.com/articles/importing-a-git-repository-using-the-command-line/)

##2. Install

Install the Gradle plugin in eclipse (In this case we assume Eclipse Luna):
If you are working with another eclipse version, please search for the corresponding gradle version as well.

[Graddle plugin](http://marketplace.eclipse.org/content/gradle-integration-eclipse-44#.VB_gPGOMl40)

##3. Configure

Once you have gradle installed, now let's import the project to your eclipse workspace:

Go to File -> Import -> Gradle -> Gradle Project, click Browse and navigate to the root folder of your project, 
then click Build Model. After a while, you'll see a root project and subprojects (pacman, pacman-core and pacman-desktop). 
Select all the projects and click Finish.

##4. Run

Right click the desktop project, Run As -> Java Application. Select the desktop starter class (e.g. DesktopLauncher.java).

if you have a problem loading assets, follow this walkthrough:

[Link](http://stackoverflow.com/questions/22822767/new-libgdx-setup-receive-file-not-found)

### Folder structure:

    └── core            
        └── 
            ├── AndroidLauncher.java 			: Main android activity
            ├── BrickBreaker.java 				: Game Activity that starts the main thread
            ├── actors
            │   ├── Ghost.java 					  : 
            │   ├── Pacman.java 					: Object Bonus, could be a coin, extra life, etc.
            ├── framework
            │   ├── DynamicGameObject.java 		: Generic class for dynamic objects with accel and velocity
            │   ├── GameObject.java 			: Every GameObject has associated position and bounds. Bounds helps to keep track of collisions.
            │   ├── Rectangle2.java 			: Extension of Rectangle class in order to control which side(s) of the object were hit
            │   ├── World.java 					: Represents the world where the game is performed. It updates the states of every actor within it for each delta time.
            │   ├── WorldListener.java 			: Basic listeners while the game is running
            │   ├── WorldRenderer.java 			: We were trying to apply MVC model, thus this class is the VIEW part whereas the World is a kind of CONTROLLER.
            │   ├── network
            │   │   └── LevelDownloader.java 	: Manage access to the network, level and high score downlading and uploading and ile system reading. Manages also XML parsing.
            │   └── util
            │       ├── Assets.java 			: This class handles all assests like textures, sounds, animations and music.
            │       ├── Player.java 			: Creates and manages a file (brickbreaker.data) that contains player name, level score and status of each level.
            │       ├── Settings.java 			: Static class with some configuration parameters like sound enable, accelerometer, etc.
            │       └── TextureRegionSet.java 	: Used for asign textures to Objects
            └── screens
                ├── CreateUserScreen.java 		: It let us create the user with an input (actually deprecated) 
                ├── GameScreen.java 			: It loads the main game
                ├── HelpScreen.java 			: It show us the how-to-play guide
                ├── LevelScreen.java 			: It let us select the levels unlocked
                ├── MenuScreen.java 			: It show us the main menu option
                ├── MessageScreen.java 			: Message Screen to display errors
                ├── MultiplayerScreen.java 		: It lets select from server or client (currently disabled).
                ├── OptionScreen.java 			: This screen let us activate/unactivate sound, music and accelerometer.
                ├── ScoreScreen.java 			: It shows the top ten players, this top ten players are loaded from the remote server.
                ├── SelectScreen.java 			:  It let us choose multiplayer/singleplayer (actually deprecated) 
                └── SplashScreen.java 			: This is the first screen that appears, downdload level runs on background.

##Developers:

+Andres Chaves (706801)

+Ilkan Esiyok (616394)

+Diego Montufar (661608)

______________________________________________________________________________________________________
The University of Melboure - 2015
