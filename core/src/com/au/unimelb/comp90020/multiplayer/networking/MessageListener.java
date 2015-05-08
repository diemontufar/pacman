package com.au.unimelb.comp90020.multiplayer.networking;

/**
 * This interface is for decoupling the message objects receptors from the GameMulticasPeer class
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public interface MessageListener {
	/**
	 * Listen for an arrived message
	 * @param m The message
	 */
	void listen(Message m);
}
