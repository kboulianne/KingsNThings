package com.presenter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// misc. global static functions
public class Util {

	private final static boolean DEBUG = true;
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
//	private final static AudioClip clickSound = new AudioClip(Util.class.getResource("click.wav").toString());
	public static void playClickSound(){
//		clickSound.play();
	}
	
//	private final static AudioClip hexClickSound = new AudioClip(Util.class.getResource("click2.wav").toString());
	public static void playHexClickSound(){
//		hexClickSound.play();
	}
	
//	private final static AudioClip startBattleSound = new AudioClip(Util.class.getResource("battle.wav").toString());
	public static void playStartBattleSound(){
//		startBattleSound.play();
	}
	
//	private final static AudioClip diceRoll = new AudioClip(Util.class.getResource("dice.wav").toString());
	static void playDiceRollSound(){
//		diceRoll.play();
	}
	
//	private final static AudioClip swordSound = new AudioClip(Util.class.getResource("sword.wav").toString());
		static void playSwordSound(){
//			swordSound.play();
	}
	
	//TODO as a joke change later
//	private final static AudioClip deathSound = new AudioClip(Util.class.getResource("dyingmanLOL.wav").toString());
	static void playDeathSound(){
//		deathSound.play();
	}
	
//	private final static Media audioFile = new Media( Util.class.getResource("test.mp3").toString() );    
//	final static MediaPlayer player = new MediaPlayer(audioFile);
	public static void playMusic(){
//		player.setVolume(0.2);
//        player.play();
//        player.setCycleCount(MediaPlayer.INDEFINITE);
	}
	
//	private final static Media audioFile2 = new Media( Util.class.getResource("battlemusic.mp3").toString() );    
//	final static MediaPlayer player2 = new MediaPlayer(audioFile2);
	public static void playBattleMusic(){
//		player.stop();    
//		player2.play();
//		player2.setCycleCount(MediaPlayer.INDEFINITE);
	}
	public static void stopBattleMusic(){
//		player2.stop();    
//		playMusic();
	}
}
