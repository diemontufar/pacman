package com.au.unimelb.comp90020.framework;


/**
 * Interface to solve listen to changes in the world and reproduce sound effects
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public interface WorldListener {

	/**
	 * Play Wuaca sound
	 */
	public void playWuaca();
	
	/**
	 * Play Life Lost
	 */
	public void playLifeLost();
	
	/**
	 * Play Game Opening
	 */
	public void playOpening();
	
	/**
	 * Play Game Over sound
	 */
	public void playGameOver();
	
}
