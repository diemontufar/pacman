package com.au.unimelb.comp90020.multiplayer.concurrency;

import com.au.unimelb.comp90020.framework.util.Util;


/**
 * LamportClock represents a logical clock for the 
 * Riccart-Agrawal lock
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public class LamportClock {
	/**
	 * Clock value
	 */
	int c;

	/**
	 * Class constructor, initialize the clock in 1
	 */
	public LamportClock() {
		c = 1;
	}

	/**
	 * Get the clock value
	 * @return The clock value
	 */
	public int getValue() {
		return c;
	}

	/**
	 * Advances the clock one tick
	 */
	public void tick() { // on internal events
		c = c + 1;
	}


	/**
	 * Process the reception of a message, updates clock
	 * @param src Source PID
	 * @param sentValue Remote clock value
	 */
	public void receiveAction(Long src, int sentValue) {
		c = Util.max(c, sentValue) + 1;
		//c = Util.max(c, sentValue);
	}
}