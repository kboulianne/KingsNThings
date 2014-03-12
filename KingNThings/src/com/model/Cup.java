package com.model;

import java.util.ArrayList;

import com.presenter.Util;

public class Cup extends GamePiece {
	private ArrayList<Thing> listOfThings;
	private ArrayList<SpecialCharacter> listOfSpecialCharacters;
	
	public Cup(){
		listOfThings = new ArrayList<Thing>();
		listOfSpecialCharacters = new ArrayList<SpecialCharacter>();
	}
	
	public ArrayList<SpecialCharacter> getListOfSpecialCharacters() {
		return listOfSpecialCharacters;
	}

	public ArrayList<Thing> getListOfThings() {
		return listOfThings;
	}
	
	public void setListOfThings(ArrayList<Thing> listOfThings) {
		for(Thing t: listOfThings)
			addThing(t);
	}
	
	public Thing getRandomThing(){
		if(listOfThings.isEmpty())
			return null;
		Thing t = listOfThings.get(Util.randomNumber(0, listOfThings.size()-1));
		return t;
	}
	
	public void addThing(Thing t)	{
		t.setOwner("Cup");
		t.setSelected(true);
		t.setHexLocation(-1);
		if (t instanceof SpecialCharacter) {
			listOfSpecialCharacters.add((SpecialCharacter) t);
			t.setFacedDown(false);
		} else {
			listOfThings.add(t);
			t.setFacedDown(true);
		}
	}
	
	public Thing removeThing(Thing t){
		t.setSelected(false);
		if (t instanceof SpecialCharacter){
			listOfSpecialCharacters.remove(t);
		} else {
			listOfThings.remove(t);
		}
		return t;
	}
}
