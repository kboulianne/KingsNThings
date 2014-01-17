package model.com;

import controller.com.Util;

public class Die extends GamePiece{
	private int value;
	
	public Die(){
		roll();
	}
	
	public int roll(){
		int randomNumber = Util.randomNumber(1, 6);
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
