package com.model;

import java.util.ArrayList;
import java.util.List;

import com.presenter.Util;

public class Cup extends GamePiece {
	private List<Thing> listOfThings;
	private List<SpecialCharacter> listOfSpecialCharacters;
	
	public Cup(){
		listOfThings = new ArrayList<Thing>();
		listOfSpecialCharacters = new ArrayList<SpecialCharacter>();
	}
	
	public List<SpecialCharacter> getListOfSpecialCharacters() {
		return listOfSpecialCharacters;
	}

	public List<Thing> getListOfThings() {
		return listOfThings;
	}
	
	public void setListOfThings(List<Thing> listOfThings) {
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
