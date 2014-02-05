package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.game.services.GameService;
import com.presenter.Util;

public class Block extends GamePiece {
	
	private final List<Thing> listOfThings;
	
	public Block()	{
		listOfThings = new ArrayList<>();
	}
	
	public boolean addThing(Thing thing, String owner){	
		// cannot add special characters, gold counters and forts
		if(thing instanceof SpecialCharacter ||
				thing instanceof IncomeCounter){
			Util.log("Error: Unable to add special characters, gold counters or forts to block");
			return false;
		}
		thing.setOwner(owner);
		listOfThings.add(thing);
		return true;
	}
	
	public boolean removeThing(Thing thing){
		listOfThings.remove(thing);
		return true;
	}

	public List<Thing> getListOfThings() {
		return listOfThings;
	}
	
	public void trimBlock()	{
		int index = 0;
		Random r = new Random();
		Cup cup = GameService.getInstance().getGame().getCup();
		
		while(listOfThings.size() > 10)	{
			index = r.nextInt(listOfThings.size());
			cup.addThing(listOfThings.get(index));
			listOfThings.remove(index);
		}
	}
}
