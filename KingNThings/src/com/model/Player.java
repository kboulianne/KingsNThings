package com.model;

import javafx.scene.paint.Color;

public class Player 	{
	private PlayerId id; 		// {1, 2, 3, 4}
	private Color color;	// {blue, green, red, yellow}
	private Block block;
	private int gold;
	private String name;
	
	public enum PlayerId { ONE(Color.YELLOW),TWO(Color.GRAY),THREE(Color.GREEN),FOUR(Color.RED); 
		private final Color color;
		PlayerId(Color c) {
			color = c;
		}
	}
	
	public Player(PlayerId i, String name) {
	    this.name = name;
	    color = i.color;
	    id = i;
	    block = new Block();
	    gold = 0;
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
}
