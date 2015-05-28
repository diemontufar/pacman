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
```
 ├── core
 │   ├── assets
 │   │   ├── backgrounds
 │   │   │   ├── completed.png                       :Background image for level completed
 │   │   │   ├── gameover.png                        :Background image for gameover state
 │   │   │   ├── pause.png                           :Background image for pause state
 │   │   │   └── ready.png                           :Background image for ready state
 │   │   ├── fonts
 │   │   │   └── font.ttf                            :Background image for gameover
 │   │   ├── PacMan2.png                             :Image map for Pacman (different color)
 │   │   ├── PacMan3.png                             :Image map for Pacman (different color)
 │   │   ├── PacMan4.png                             :Image map for Pacman (different color)
 │   │   ├── PacMan.png                              :Image map for Pacman (yellow color)
 │   │   ├── pacman.tmx
 │   │   ├── sounds
 │   │   │   ├── gameover.mp3                        :Gameover sound
 │   │   │   ├── lifelost.mp3                        :Life lost sound
 │   │   │   ├── opening.mp3                         :Opening sound
 │   │   │   └── wuaca.mp3                           :Walking sound
 │   │   └── textures
 │   │       └── ChomperSprites.png                  :Image map for ghosts
 └── src
 │   └── com.au.unimelb.comp90020
 │       ├── actors
 │       │   ├── Ghost.java                      :Ghost actor class (incl Ghost movement)
 │       |   └── Pacman.java                     :Pacman actor class
 │       ├── framework
 │       │   ├── Animation.java              :Animation class for Pacman walking effects
 │       │   ├── DynamicGameObject.java      :Dynamic game object class
 │       │   ├── GameObject.java             :Static game object class
 │       │   ├── GenericRectangle.java       :Rectangle bound class
 │       │   ├── util
 │       │   │   ├── Assets.java             :Class to handle sounds and sprites
 │       │   │   ├── LongLinkedList.java     :Queue of Longs (Queue used for RA-Mutex)
 │       │   │   ├── Settings.java           :Game Settings class
 │       │   │   └── Util.java               :Class with handy generic methods
 │       │   ├── World.java                  :Class that represents the World State
 │       │   ├── WorldListener.java          :Listener world class (for sound effects)
 │       |   └── WorldRenderer.java          :Renderer of the world state within the screen
 │       ├── multiplayer
 │       │   ├── concurrency
 │       │   │   ├── LamportClock.java       :Class that controls LamportClock ticking
 │       │   │   ├── Lock.java               :Interface for distributed locking
 │       │   │   ├── RAMutex.java            :Implementation of the RiccartAgrawal Lock
 │       │   ├── networking
 │       │   │   ├── GameMulticastPeer.java  :PeerToPeer networking class (client and server)
 │       │   │   ├── GameServerThread.java   :Socket listener thread
 │       │   │   ├── Message.java            :Message class that holds our communication protocol
 │       │   │   ├── MessageListener.java    :Listener pattern to decloupe messages from actions
 │       │   │   └── Process.java            :Class that holds the topology of the network
 │       ├── PacManGame.java                 :Main game class
 │       └── screens
 │           └── GameScreen.java             :Game Screen
 ├── desktop
 │   └── src
 │       └── com.au.unimelb.comp900200.desktop
 │           └── DesktopLauncher.java                :Launcher for desktop client (according to LibGDX)
```
##Developers:

+Andres Chaves (706801)

+Ilkan Esiyok (616394)

+Diego Montufar (661608)

______________________________________________________________________________________________________
The University of Melboure - 2015
