package com.au.unimelb.comp90020.framework.util;

import java.util.LinkedList;

public class LongLinkedList extends LinkedList<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void add(long i) {
		super.add(i);
	}

	public boolean contains(long i) {
		return super.contains(i);
	}

	public long removeHead() {
		Long j = (Long) super.removeFirst();
		return j.longValue();
	}

	public boolean removeObject(long i) {
		return super.remove(new Long(i));
	}

	public long getEntry(int index) {
		Long j = (Long) super.get(index);
		return j.intValue();
	}
}
