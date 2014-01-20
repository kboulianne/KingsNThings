package model.com;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import controller.com.Paintable;
import controller.com.Util;

public class Block extends GamePiece implements Paintable{
	List<Thing> listOfThings;
	
	Block(){
		listOfThings = new ArrayList<Thing>();
	}
	
	public boolean addThing(Thing thing){	
		// cannot add special characters, gold counters and forts
		if(thing instanceof Fort || thing instanceof SpecialCharacter ||
				thing instanceof IncomeCounter){
			Util.log("Error: Unable to add special characters, gold counters or forts to block");
			return false;
		}
		listOfThings.add(thing);
		return true;
	}
	
	public boolean removeThing(Thing thing){
		listOfThings.remove(thing);
		return true;
	}

	@Override
	public void paint(Pane pane) {
		for(Thing thing: listOfThings)
			thing.paint(pane);
	}
}
