package model.com;

import java.util.ArrayList;
import java.util.List;

public class Block extends GamePiece{
	List<Thing> listOfThings;
	
	Block(){
		listOfThings = new ArrayList<Thing>();
	}
	
	public boolean addThing(Thing thing){	
		// cannot add special characters, gold counters and forts
		listOfThings.add(thing);
		return true;
	}
	
	public boolean removeThing(Thing thing){
		listOfThings.remove(thing);
		return true;
	}
	
	// paint
}
