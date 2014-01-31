package com.presenter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

// misc. global static functions
public class Util {

	final static boolean DEBUG = true;
	public static void log(String message) {
		if(DEBUG)
			System.out.println(message);
	}

	public static double distanceBtwTwoPts(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public static int randomNumber(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

	/**
	 * Integer comparator from high to low. (reverse sort)
	 */
	public static final class ReverseIntegerSortComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer i1, Integer i2) {
			if (i1 > i2) {
				return -1;
			}
			if (i1 < i2) {
				return 1;
			}

			return 0;
		}

	}

	/**
	 * creates random list
	 *
	 * @param list
	 * @return
	 */
	public static <T> ArrayList<T> getRandomList(ArrayList<T> list) {
		int rand = 0;
		int size = list.size();
		Random rnd = new Random();
		ArrayList<T> newList = new ArrayList<T>();
		for (int i = 0; i < size; i++) {
			rand = rnd.nextInt(list.size());
			newList.add(list.get(rand));
			list.remove(rand);
		}
		return newList;
	}
}
