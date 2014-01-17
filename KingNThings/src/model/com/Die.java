package model.com;

import java.util.Random;

public class Die extends GamePiece{
	private int value;
	
	Die(){
		roll();
	}
	
	public int roll(){
		Random rand = new Random();
		int randomNumber = rand.nextInt((6 - 1) + 1) + 1;
		setValue(randomNumber);
		//setImage("..//dieFace"+value+".jpg");
		return randomNumber;
	}
	
	// getters and setters
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

}
