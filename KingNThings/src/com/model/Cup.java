package com.model;

import java.util.ArrayList;
import java.util.List;

import com.util.Util;

public class Cup {
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
	

	public ArrayList<Creature> getListOfCreatures() {
		ArrayList<Creature> creatures = new ArrayList<Creature>();
		for(Thing t: listOfThings){
			if(t instanceof Creature){
				creatures.add((Creature) t);
			}
		}
		return creatures;
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
	
	public Creature getRandomCreature(){
		ArrayList<Creature> creatures = getListOfCreatures();
		if(creatures.isEmpty())
			return null;
		Creature c = creatures.get(Util.randomNumber(0, creatures.size()-1));
		c.setFacedDown(false);
		return c;
	}
	
	public void addThing(Thing t)	{
		t.setOwner("Cup");
		t.setSelected(false);
		t.setHexLocation(-1);
		if (t instanceof SpecialCharacter) {
			listOfSpecialCharacters.add((SpecialCharacter) t);
			t.setFacedDown(false);
		} else {
			listOfThings.add(t);
			t.setFacedDown(false);
		}
	}
	
	public Thing removeThing(Thing t){
		t.setSelected(false);
		if (t instanceof SpecialCharacter){
			listOfSpecialCharacters.remove(t);
		} else {
			listOfThings.remove(t);
		}
		t.setFacedDown(false);
		return t;
	}
}
