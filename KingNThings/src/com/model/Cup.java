package com.model;

import java.util.ArrayList;

public class Cup extends GamePiece {
	private ArrayList<Thing> listOfThings;

	public ArrayList<Thing> getListOfThings() {
		return listOfThings;
	}
	public void setListOfThings(ArrayList<Thing> listOfThings) {
		this.listOfThings = listOfThings;
	}
	public void addThing(Thing t)	{
		t.setOwner("Cup");
		listOfThings.add(t);
	}
	public Thing getThing(String name){
		for(Thing t: listOfThings){
			if (t.getName().equals(name))
				return t;
		}	
		return null;
	}
	public Thing removeThingWithName(String name){
		Thing t = getThing(name);	
		listOfThings.remove(t);
		return t;
	}
	
	protected void removeThing(Thing t){
		listOfThings.remove(t);
	}
}
