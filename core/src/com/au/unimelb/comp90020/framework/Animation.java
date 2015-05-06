package com.au.unimelb.comp90020.framework;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Animation represent a transition between different Texture Regions through time
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 *
 */
public class Animation {
	/**
	 * Loop Animation Mode 
	 */
	public static final int ANIMATION_LOOPING = 0;
	/**
	 * Non Looping Animation Mode
	 */
	public static final int ANIMATION_NONLOOPING = 1;

	/**
	 * Texture Regions to transition
	 */
	final TextureRegion[] keyFrames;
	/**
	 * Duration of each transition
	 */
	final float frameDuration;

	/**
	 * Class constructor
	 * @param frameDuration Duration of each transition
	 * @param keyFrames TextureRegions to loop trhough
	 */
	public Animation (float frameDuration, TextureRegion... keyFrames) {
		this.frameDuration = frameDuration;
		this.keyFrames = keyFrames;
	}

	/**
	 * Get the next region to display
	 * @param stateTime Time
	 * @param mode Animation mode
	 * @return The next texture region to render
	 */
	public TextureRegion getKeyFrame (float stateTime, int mode) {
		int frameNumber = (int)(stateTime / frameDuration);

		if (mode == ANIMATION_NONLOOPING) {
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
		} else {
			frameNumber = frameNumber % keyFrames.length;
		}
		return keyFrames[frameNumber];
	}
}
