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
	private Hex startPos;
	
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


	// get special characters from hexes and players block
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
		
		ArrayList<Thing> blockChars = block.getListOfThings();
		for(Thing t: blockChars){
			if (t instanceof SpecialCharacter)
				specChars.add((SpecialCharacter) t);
		}
		return specChars;
	}

	// remove special characters from block or Hexes
	public SpecialCharacter removeSpecialCharacter(SpecialCharacter sC) {
		// TODO Auto-generated method stub
		ArrayList<Thing> blockChars = block.getListOfThings();
		for(Thing t: blockChars){
			if (sC.equals(t)){
				block.removeThing(t);
				return (SpecialCharacter) t;
			}		
		}
		
		ArrayList<Hex> hexes = (ArrayList<Hex>) GameService.getInstance().getGame().getBoard().getHexes();
		for(Hex h:hexes){
			ArrayList<Creature> creatures = h.getArmies(this);
			if(creatures!=null){ //TODO fix
				for(Creature c: creatures){
					if(sC.equals(c)){
						block.removeThing(c);
						return (SpecialCharacter) c;
					}
				}
			}
		}
		return null;
	}

	public Hex getStartPos() {
		return startPos;
	}

	public void setStartPos(Hex startPos) {
		this.startPos = startPos;
	}

	public int calculateIncome() {
		int hexGold = 0;
		int fortGold = 0;
		int counterGold = 0;
		int specCharGold = 0;
		int totalGold = 0;
		Game game = GameService.getInstance().getGame();
		
		for (Hex h : game.getBoard().getHexes()) {
			if ((h != null) && (h.getHexOwner() == this)) {
				hexGold++;
				if(h.getFort() != null)	fortGold += h.getFort().getValue();
				if(h.getSpecialCharacters() != null)	specCharGold += h.getSpecialCharacters().size();
				if(h.getCounter() != null)	counterGold += h.getCounter().getValue();
			}
		}
		
		hexGold = (int) Math.ceil(hexGold/2.0);
		totalGold += (hexGold + fortGold + counterGold + specCharGold);
		return totalGold;
	}
}
