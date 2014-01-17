package model.com;

import javafx.scene.paint.Color;

public abstract class Hex extends GamePiece{
	
	Player owner;
	Color color;
	boolean selected;
	
	Hex[] joiningHexes;
	
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
