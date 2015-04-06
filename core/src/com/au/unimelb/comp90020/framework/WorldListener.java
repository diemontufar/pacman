package com.au.unimelb.comp90020.framework;

/**
 * 
 * We need it to solve a little MVC problem: when do we play sound effects? We
 * could just add invocations of Assets.playSound() to the respective simulation
 * classes.
 * 
 */

public interface WorldListener {

	public void playWuaca();
	
	public void playLifeLost();
	
	public void playOpening();
	
	public void playGameOver();
	
}
