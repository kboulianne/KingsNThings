package model.com;

import java.util.ArrayList;
import java.util.List;

public class Block extends GamePiece{
	List<Thing> listOfThings;
	
	Block(){
		listOfThings = new ArrayList<Thing>();
		//setImage("");
	}
	
	public boolean addThing(Thing thing){	
		// cannot add special characters, gold counters and forts
		listOfThings.add(thing);
		//paint
		return true;
	}
	
	public boolean removeThing(Thing thing){
		listOfThings.remove(thing);
		// add Thing to bag 
		//paint
		return true;
	}
}
