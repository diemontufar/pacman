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

 ├── core
 │   ├── assets
 │   │   ├── backgrounds
 │   │   │   ├── completed.png
 │   │   │   ├── gameover.png
 │   │   │   ├── pause.png
 │   │   │   └── ready.png
 │   │   ├── fonts
 │   │   │   └── font.ttf
 │   │   ├── PacMan2.png
 │   │   ├── PacMan3.png
 │   │   ├── PacMan4.png
 │   │   ├── PacMan.png
 │   │   ├── pacman.tmx
 │   │   ├── sounds
 │   │   │   ├── gameover.mp3
 │   │   │   ├── lifelost.mp3
 │   │   │   ├── opening.mp3
 │   │   │   └── wuaca.mp3
 │   │   └── textures
 │   │       └── ChomperSprites.png
 └── src
 │       └── com
 │           └── au
 │               └── unimelb
 │                   └── comp90020
 │                       ├── actors
 │                       │   ├── Ghost.java
 │                       │   └── Pacman.java
 │                       ├── framework
 │                       │   ├── Animation.java
 │                       │   ├── DynamicGameObject.java
 │                       │   ├── GameObject.java
 │                       │   ├── GenericRectangle.java
 │                       │   ├── util
 │                       │   │   ├── Assets.java
 │                       │   │   ├── LongLinkedList.java
 │                       │   │   ├── Settings.java
 │                       │   │   └── Util.java
 │                       │   ├── World.java
 │                       │   ├── WorldListener.java
 │                       │   └── WorldRenderer.java
 │                       ├── multiplayer
 │                       │   ├── concurrency
 │                       │   │   ├── LamportClock.java
 │                       │   │   ├── Lock.java
 │                       │   │   └── RAMutex.java
 │                       │   └── networking
 │                       │       ├── GameMulticastPeer.java
 │                       │       ├── GameServerThread.java
 │                       │       ├── Message.java
 │                       │       ├── MessageListener.java
 │                       │       └── Process.java
 │                       ├── PacManGame.java
 │                       └── screens
 │                           └── GameScreen.java
 ├── desktop
 │   └── src
 │       └── com
 │           └── au
 │               └── unimelb
 │                   └── comp90020
 │                       └── desktop
 │                           └── DesktopLauncher.java

##Developers:

+Andres Chaves (706801)

+Ilkan Esiyok (616394)

+Diego Montufar (661608)

______________________________________________________________________________________________________
The University of Melboure - 2015
