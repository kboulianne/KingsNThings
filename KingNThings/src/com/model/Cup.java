package com.model;

import java.util.ArrayList;

import com.presenter.Util;

public class Cup extends GamePiece {
	private ArrayList<Thing> listOfThings;
	private ArrayList<IncomeCounter> listOfIncomeCounters;
	private ArrayList<SpecialCharacter> listOfSpecialCharacters;
	
	public Cup(){
		listOfThings = new ArrayList<Thing>();
		listOfIncomeCounters = new ArrayList<IncomeCounter>();
		listOfSpecialCharacters = new ArrayList<SpecialCharacter>();
	}

	public ArrayList<Thing> getListOfThings() {
		return listOfThings;
	}
	public void setListOfThings(ArrayList<Thing> listOfThings) {
		for(Thing t: listOfThings)
			addThing(t);
	}
	public void addThing(Thing t)	{
		t.setOwner("Cup");
		t.setHexLocation(-1);
		if (t instanceof IncomeCounter){
			listOfIncomeCounters.add((IncomeCounter) t);
			t.setFacedDown(false);
		}
		else if (t instanceof SpecialCharacter) {
			listOfSpecialCharacters.add((SpecialCharacter) t);
			t.setFacedDown(false);
		} else {
			listOfThings.add(t);
			t.setFacedDown(true);
		}
	}
	public Thing getThing(String name){
		for(Thing t: listOfThings){
			if (t.getName().equals(name))
				return t;
		}	
		return null;
	}
	/*public Thing removeThingWithName(String name){
		Thing t = getThing(name);	
		listOfThings.remove(t);
		return t;
	}*/
	
	public Creature getRandomCreature(){
		if(listOfThings.isEmpty())
			return null;
		Thing t = listOfThings.get(Util.randomNumber(0, listOfThings.size()-1));
		while(!(t instanceof Creature))
			t = listOfThings.get(Util.randomNumber(0, listOfThings.size()-1));
		return (Creature)t;
	}
	
	public Thing removeThing(Thing t){
		if (t instanceof IncomeCounter){
			listOfIncomeCounters.remove(t);
		}
		else if (t instanceof SpecialCharacter){
			listOfSpecialCharacters.remove(t);
		}
		else{
			listOfThings.remove(t);
		}
		return t;
	}

	public ArrayList<IncomeCounter> getListOfIncomeCounters() {
		return listOfIncomeCounters;
	}

	public ArrayList<SpecialCharacter> getListOfSpecialCharacters() {
		return listOfSpecialCharacters;
	}
}
