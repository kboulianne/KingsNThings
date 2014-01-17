package model.com;

import java.util.Random;

public class Die extends GamePiece{
	private int value;
	
	public Die(){
		roll();
	}
	
	public int roll(){
		Random rand = new Random();
		int randomNumber = rand.nextInt((6 - 1) + 1) + 1;
		setValue(randomNumber);
		setImage("view/com/assets/pics/die/die"+value+".png");
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
