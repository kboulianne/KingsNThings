package com.presenter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// misc. global static functions
public class Util {

	final static boolean DEBUG = true;
	final public static boolean AUTOMATE = true;
	
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

			return -1;
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
	
	
	// Sounds
	
	final static AudioClip clickSound = new AudioClip(Util.class.getResource("click.wav").toString());
	public static void playClickSound(){
		clickSound.play();
	}
	
	final static AudioClip diceRoll = new AudioClip(Util.class.getResource("dice.wav").toString());
	public static void playDiceRollSound(){
		diceRoll.play();
	}
	
	final static Media audioFile = new Media( Util.class.getResource("greensleeves.mp3").toString() );    
	final static MediaPlayer player = new MediaPlayer(audioFile);
	public static void playMusic(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				
		        player.play();
		        player.setCycleCount(MediaPlayer.INDEFINITE);
			}
		}).start();
	}
}
