package model.com;

import javafx.scene.paint.Color;

//TODO Use Factory Pattern?
public class Hex extends GamePiece {
	
    
	private Player owner;
	// This is in player?
	Color color;
	/** Is the owner's start position. */
	private boolean startPosition;
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
	
	protected Hex() {
	    startPosition = false;
	}
	
	public Hex(int type){
	    this();
	
	    if (type >= 0 && type <=8) 
		this.type = type;
	}

	
    // setters and getters
    public final Player getOwner() {
	return owner;
    }
    
    public final void setOwner(final Player player) {
	this.owner = player;
    }
	
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

    public final boolean isStartPosition() {
	return startPosition;
    }
	
    public void setStartPosition(boolean b) {
	this.startPosition = b;
    }
}
