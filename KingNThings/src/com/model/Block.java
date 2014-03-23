package com.model;

import java.util.ArrayList;
import java.util.Random;

public class Block extends GamePiece {
	
	// Note each player has two racks of capicity 10 items
	//int capacity;
	
	private final ArrayList<Thing> listOfThings;
	
	public Block()	{
		listOfThings = new ArrayList<>();
		//capacity = 20; // block represents two racks???
	}
	
	boolean addThing(Thing thing, String owner){	
		// cannot add special characters, gold counters and forts
		
		/*if(thing instanceof SpecialCharacter ||
				thing instanceof IncomeCounter){
			Util.log("Error: Unable to add special characters, gold counters or forts to block");
			return false;
		}*/
		thing.setFacedDown(false);
		thing.setOwner(owner);
		listOfThings.add(thing);
		return true;
	}
	
	public boolean removeThing(Thing thing){
		listOfThings.remove(thing);
		return true;
	}

	public ArrayList<Thing> getListOfThings() {
		return listOfThings;
	}
	
	void trimBlock()	{
		int index = 0;
		Random r = new Random();
		//FIXME: Can't be here.
//		Cup cup = GameService.getInstance().getGame().getCup();
//		
//		while(listOfThings.size() > 10)	{
//			index = r.nextInt(listOfThings.size());
//			cup.addThing(listOfThings.get(index));
//			listOfThings.remove(index);
//		}
	}
}
