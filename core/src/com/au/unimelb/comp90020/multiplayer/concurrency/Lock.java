package com.au.unimelb.comp90020.multiplayer.concurrency;

import com.au.unimelb.comp90020.multiplayer.networking.MessageListener;



public interface Lock extends MessageListener {
	public void requestCS(); // may block
	public int getClockValue();
	public void releaseCS();
}
