package com.au.unimelb.comp90020.framework.util;

import java.util.LinkedList;

/**
 * A generic list of Long objects, it provides a Pile functionality
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public class LongLinkedList extends LinkedList<Object> {

	private static final long serialVersionUID = 1L;

	/**
	 * Add a long the list
	 * @param i The long number
	 */
	public void add(long i) {
		super.add(i);
	}

	/**
	 * Check if the list contains a long
	 * @param i The long to check
	 * @return True if the list contains i, false otherwise
	 */
	public boolean contains(long i) {
		return super.contains(i);
	}

	/**
	 * Remove a long from the head of the list
	 * @return A long in the head of the list
	 */
	public long removeHead() {
		Long j = (Long) super.removeFirst();
		return j.longValue();
	}

	/**
	 * Remove a specific long from the list
	 * @param i The long to remove
	 * @return True if there was a long and it was removed, false otherwise
	 */
	public boolean removeObject(long i) {
		return super.remove(new Long(i));
	}

	/**
	 * Get an entry at a specified index
	 * @param index An index
	 * @return The long value
	 */
	public long getEntry(int index) {
		Long j = (Long) super.get(index);
		return j.intValue();
	}
}
