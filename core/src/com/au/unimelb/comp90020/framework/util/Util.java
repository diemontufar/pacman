package com.au.unimelb.comp90020.framework.util;

import java.util.*;

/**
 * Util methods
 * 
 * @author Andres Chaves, Diego Montufar, Ilkan Esiyok (ID’s: 706801, 661608, 616394)
 */
public class Util {

	/**
	 * Return the maximum between two numbers
	 * @param a First number
	 * @param b Second number
	 * @return The maximum number between a and b
	 */
	public static int max(int a, int b) {
		if (a > b)
			return a;
		return b;
	}

	/**
	 * Sleep the current thread time milliseconds
	 * @param time Milliseconds to sleep
	 */
	public static void mySleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Change the mode og obj to wait
	 * @param obj
	 */
	public static void myWait(Object obj) {
		println("waiting");
		try {
			obj.wait();
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Check if all the elements of an array are less than other
	 * @param A First array
	 * @param B Second array
	 * @return True if all elements of A are less tan equal B
	 */
	public static boolean lessThan(int A[], int B[]) {
		for (int j = 0; j < A.length; j++)
			if (A[j] > B[j])
				return false;
		for (int j = 0; j < A.length; j++)
			if (A[j] < B[j])
				return true;
		return false;
	}

	/**
	 * Return the maximum of an array
	 * @param A The array
	 * @return The maximum of the array
	 */
	public static int maxArray(int A[]) {
		int v = A[0];
		for (int i = 0; i < A.length; i++)
			if (A[i] > v)
				v = A[i];
		return v;
	}

	/**
	 * Convert an array of ints into string
	 * @param A The array of ints
	 * @return A string representation of the array
	 */
	public static String writeArray(int A[]) {
		StringBuffer s = new StringBuffer();
		for (int j = 0; j < A.length; j++)
			s.append(String.valueOf(A[j]) + " ");
		return new String(s.toString());
	}

	/**
	 * Converts a string with ints separated by  \t\n\r\f
	 * @param s The string
	 * @param A An array of ints
	 */
	public static void readArray(String s, int A[]) {
		StringTokenizer st = new StringTokenizer(s);
		for (int j = 0; j < A.length; j++)
			A[j] = Integer.parseInt(st.nextToken());
	}

	/**
	 * Search an integer in an array and return the position
	 * @param A The array
	 * @param x The integer
	 * @return The index where x is located in A, or -1 otherwise
	 */
	public static int searchArray(int A[], int x) {
		for (int i = 0; i < A.length; i++)
			if (A[i] == x)
				return i;
		return -1;
	}

	/**
	 * Print a message inmediatly to stdout
	 * @param s String to print
	 */
	public static void println(String s) {
		//if (Settings.DEBUG_MODE) {
			System.out.println(s);
			System.out.flush();
		//}
	}
}
