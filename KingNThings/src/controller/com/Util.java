package controller.com;

import java.util.Random;

// misc. global static functions

public class Util {
	
	public static void log(String message){
		System.out.println(message);
	}
	
	public static double distanceBtwTwoPts(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
	}
	
	public static int randomNumber(int min, int max){
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}
}
