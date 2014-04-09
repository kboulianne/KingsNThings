package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.main.KNTAppFactory;

public class Block  {

	private List<Thing> listOfThings;
	
	public Block()	{
		listOfThings = new ArrayList<>();
		//capacity = 20; // block represents two racks???
	}
	
	public boolean addThing(Thing thing, String owner){	
		// cannot add special characters, gold counters and forts
		thing.setFacedDown(false);
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
		
		Cup cup = KNTAppFactory.getGamePresenter().getLocalInstance().getCup();
		
		while(listOfThings.size() > 10)	{
			index = r.nextInt(listOfThings.size());
			cup.addThing(listOfThings.get(index));
			listOfThings.remove(index);
		}
	}
}
