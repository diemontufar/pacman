package com.au.unimelb.comp90020.multiplayer.concurrency;

import com.au.unimelb.comp90020.multiplayer.networking.MessageListener;



/**
 * Lock interface represents a generical critical section (zone)
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public interface Lock extends MessageListener {
	/**
	 * Request the access to critical zone
	 */
	public void requestCS(); // may block
	/**
	 * Get the value of the Lamport lock (used for debug)
	 * @return Value of the Lamport clock
	 */
	public int getClockValue();
	/**
	 * Release the critical section
	 */
	public void releaseCS();
}
