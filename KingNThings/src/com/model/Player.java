package com.model;

import java.util.ArrayList;

import com.game.services.GameService;
import com.model.game.Game;

import javafx.scene.paint.Color;

public class Player 	{
	private PlayerId id; 		// {1, 2, 3, 4}
	private Color color;	// {blue, green, red, yellow}
	private Block block;
	private int gold;
	private String name;
	
	public enum PlayerId { ONE(Color.YELLOW),TWO(Color.valueOf("555555")),THREE(Color.GREEN),FOUR(Color.RED); 
		private final Color color;
		PlayerId(Color c) {
			color = c;
		}
		public Color getColor() {
			return color;
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

	//TODO maybe move?
	public ArrayList<SpecialCharacter> getAllOwnedSpecialChar() {
		ArrayList<Hex> hexes = (ArrayList<Hex>) GameService.getInstance().getGame().getBoard().getHexes();
		ArrayList<SpecialCharacter> specChars = new ArrayList<SpecialCharacter>();
		for(Hex h:hexes){
			ArrayList<Creature> creatures = h.getArmies(this);
			if(creatures!=null){ //TODO fix
				for(Creature c: creatures){
					if(c instanceof SpecialCharacter)
						specChars.add((SpecialCharacter) c);
				}
			}
		}
		return specChars;
	}
}
