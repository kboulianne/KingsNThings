package model.com;

import javafx.scene.paint.Color;

//TODO Use Factory Pattern?
public class Hex extends GamePiece {
	
    
	Player owner;
	// This is in player?
	Color color;
	boolean selected;
	int type;
	int movementWeight = 1;
	
	Hex[] joiningHexes;

	public static final int
		JUNGLE_HEX = 1,
		FROZEN_WASTE_HEX = 2,
		FOREST = 3,
		PLAINS = 4,
		SWAMP = 5,
		MOUNTAIN = 6,
		DESERT = 7,
		SEA = 8;
	
	protected Hex() {}
	
	public Hex(int type){
	    if (type >= 0 && type <=8) 
		this.type = type;
	    
	    
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
	
	public final int getType() {
	    return type;
	}
}
