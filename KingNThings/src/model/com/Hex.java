package model.com;

import javafx.scene.paint.Color;

//TODO Use Factory Pattern?
public class Hex extends GamePiece {
	
    
	Player owner;
	// This is in player?
	Color color;
	boolean selected;
	int movementWeight = 1;
	
	Hex[] joiningHexes;

//	public static final int 
//		JUNGLE_HEX,
//		FROZEN_WASTE_HEX,
		
	
	
	Hex(){
		
	}

	
	// setters and getters
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
