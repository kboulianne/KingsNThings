package com.model;

import javafx.scene.paint.Color;

public class Player 	{
	private PlayerId id; // {1, 2, 3, 4}
	private Color color; // {blue, green, red, yellow}
	private Block block;
	private int gold = 0;
	private String name;
	private boolean citadelOwner = false;
	private int timeCitOwned = -1;

	private transient Hex startPos;
	
	public enum PlayerId { ONE(Color.YELLOW),TWO(Color.valueOf("555555")),THREE(Color.GREEN),FOUR(Color.RED); 
		private final Color color;
		PlayerId(Color c) {
			color = c;
		}
		public Color getColor() {
			return color;
		}
	}
	
	public Player(String name) {
		// Set on server
		this.name = name;
		this.block = new Block();
		setStartPos(null);
	}
	
	/**
	 * Convenience getter for the player's name.
	 * @return 
	 */
	public final String getName() {
	    return name;
	}
	
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
	
	public Color getColor() {
	    return color;
	}
	
	public PlayerId getId() {
		return id;
	}

	public void setGold(int g)	{
		gold = g;
	}
	
	public int getGold() {
	    return gold;
	}
	
	public void addGold(int g)	{
		gold += g;
	}
	
	public void removeGold(int g)	{
		gold -= g;
	}
	
	public void addThing(Thing t)	{
		t.setOwner(name);
		block.addThing(t, getName());
	}
	
	public void removeThing(Thing t)	{
		block.removeThing(t);
	}

	public void trimBlock() {
		block.trimBlock();
	}


	// remove special characters from block or Hexes
	public SpecialCharacter removeSpecialCharacter(SpecialCharacter sC) {
		throw new UnsupportedOperationException("FIXME!");
	}

	public Hex getStartPos() {
		return startPos;
	}
	
	public void setStartPos(Hex startPos) {
		this.startPos = startPos;
	}

	public void setPlayerID(PlayerId id) {
		this.id = id;
		this.color = id.color;
	}
	
	
	public int calculateIncome(Board board) {
		int hexGold = 0;
		int fortGold = 0;
		int counterGold = 0;
		int specCharGold = 0;
		int totalGold = 0;

		for (Hex h : board.getHexes()) {
			if ((h != null) && (h.getHexOwner() == this)) {
				hexGold++;
				if(h.getFort() != null)	fortGold += h.getFort().getValue();
				if(h.getCounter() != null)	counterGold += h.getCounter().getValue();
				if(h.getArmies(this) != null)	{
					for(Creature c: h.getArmies(this)) {
						if(c instanceof SpecialCharacter)	specCharGold++;
					}
				}
			}
		}

		hexGold = (int) Math.ceil(hexGold/2.0);
		totalGold += (hexGold + fortGold + counterGold + specCharGold);

		return 21;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		
		if (obj instanceof Player) {
			Player p = (Player)obj;
			boolean idEqual = (id == null ? true : id.equals(p.id));
			
			return name.equals(p.name) && idEqual; 
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean isCitadelOwner() {
		return citadelOwner;
	}

	public void setCitadelOwner(boolean citadelOwner) {
		this.citadelOwner = citadelOwner;
	}

	public int getTimeCitOwned() {
		return timeCitOwned;
	}

	public void addTimeCitOwned() {
		this.timeCitOwned++;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
