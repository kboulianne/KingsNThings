package com.model;

import javafx.scene.paint.Color;

/**
 * Forts are created on the fly in the construction phase.
 * 
 * A fort is not a thing but a game piece.
 */
public class Fort extends Thing	{

	public enum FortType {
		TOWER("tower"),
		KEEP("keep"),
		CASTLE("castle"),
		CITADEL("citadel");

		public final String typeAsString;
		
		private FortType(String s) {
			typeAsString = s;
		}

	}
	/*
	Levels:
		0: Tower
		1: Keep
		2: Castle
		3: Citadel
	*/
	private int value;
	private FortType fortType;
	private transient boolean upgraded = false;
	private static final String IMAGE_DIR = "view/com/assets/pics/gamepieces/forts/";
	
	
	public Fort(FortType type) {
		super(type.typeAsString);
		setColor(Color.LIGHTGREY);
		
		if(type == Fort.FortType.TOWER)	{
			setValue(1);
		} else if(type == Fort.FortType.KEEP)	{
			setValue(2);
		} else if(type == Fort.FortType.CASTLE)	{
			setValue(3);
		} else if(type == Fort.FortType.CITADEL)	{
			setValue(4);
		}
		
		fortType = type;
		setImage(IMAGE_DIR + type.typeAsString + ".jpeg");
	}
	
	public void upgrade() {
		switch (fortType) {
			case TOWER: 
				fortType = FortType.KEEP;
				setValue(getValue() + 1);
				break;
			case KEEP:
				fortType = FortType.CASTLE;
				setValue(getValue() + 1);
				break;
			case CASTLE:
				fortType = FortType.CITADEL;
				setValue(getValue() + 1);
				break;
			default:
				break;
		}
		
		setImage(IMAGE_DIR + fortType.typeAsString + ".jpeg");
	}
	
	// Can only create FortType.TOWER. They get upgraded in game.
	public static final Fort create() {
		return new Fort(FortType.TOWER);
	}

	public FortType getFortType() {
		return fortType;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public void setUpgraded(boolean b)	{
		upgraded = b;
	}
	
	public boolean getUpgraded()	{
		return upgraded;
	}
	
	public boolean upgraded()	{
		return upgraded;
	}
}
