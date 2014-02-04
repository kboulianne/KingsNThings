package com.model;

/**
 * Forts are created on the fly in the construction phase.
 * 
 * A fort is not a thing but a game piece.
 */
public class Fort extends GamePiece	{

	public enum FortType {
		TOWER("tower"),
		KEEP("keep"),
		CASTLE("castle"),
		CITADEL("citadel");

		private final String type;
		
		private FortType(String s) {
			this.type = s;
		}
		
//		public final String getType() {
//			return type;
//		}
	}
	/*
	Levels:
		0: Tower
		1: Keep
		2: Castle
		3: Citadel
	*/
	private FortType fortType;
	private static final String IMAGE_DIR = "view/com/assets/pics/gamepieces/forts/";
	
	
	protected Fort(FortType type) {
		super(type.type);
		
		fortType = type;
		setImage(IMAGE_DIR + type.type + ".jpeg");
	}
	
	public void upgrade() {
//		if (level <= 3) level ++;
		switch (fortType) {
			case TOWER: 
				fortType = FortType.KEEP; 
				break;
			case KEEP:
				fortType = FortType.CASTLE;
				break;
			case CASTLE:
				fortType = FortType.CITADEL;
				break;
			default:
				break;
		}
		
		setImage(IMAGE_DIR + fortType.type + ".jpeg");
	}
	
	// Can only create FortType.TOWER. They get upgraded in game.
	public static final Fort create() {
		return new Fort(FortType.TOWER);
	}
}
