package com.model;

import com.util.Util;

public class Die extends GamePiece {
	private int value;
	
	public Die(){
	    roll();
	}
	
	public final int roll(){
		int randomNumber = Util.randomNumber(1, 6);
		setValue(randomNumber);
		this.setImage("view/com/assets/pics/die/die"+value+".png");

		return randomNumber;
	}
	
	// getters and setters
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
		this.setImage("view/com/assets/pics/die/die" + value + ".png");
	}
}
