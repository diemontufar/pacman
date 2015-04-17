package com.au.unimelb.comp90020.multiplayer.concurrency;

import com.au.unimelb.comp90020.framework.util.Util;


public class LamportClock {
	int c;

	public LamportClock() {
		c = 1;
	}

	public int getValue() {
		return c;
	}

	public void tick() { // on internal events
		c = c + 1;
	}

	public void sendAction() {
		// include c in message
		c = c + 1;
	}

	public void receiveAction(Long src, int sentValue) {
		c = Util.max(c, sentValue) + 1;
	}
}