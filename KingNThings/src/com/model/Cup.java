package com.model;

import java.util.ArrayList;

<<<<<<< HEAD
public class Cup extends GamePiece	{
	ArrayList<Thing> listOfThings;
	
	public Cup()	{
		listOfThings = Thing.createThings();
	}
	
	//Get and set methods
	public ArrayList<Thing> getThings()	{	return listOfThings;	}
	public void setThings(ArrayList<Thing> things)	{	listOfThings = things;	}
=======
import view.com.GameScreen;

import com.presenter.Paintable;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Cup extends GamePiece implements Paintable{
	ArrayList<Thing> listOfThings;
	
	
	public void paint(Pane pane){
		for(final Thing t: listOfThings){
			ImageView img = t.paintThingRectangle(55, pane);	
			img.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					GameScreen.dismissPopup();
					t.paintThingInDetails(GameScreen.detailsBox);
				}
			});
		}
	}

	public ArrayList<Thing> getListOfThings() {
		return listOfThings;
	}


	public void setListOfThings(ArrayList<Thing> listOfThings) {
		this.listOfThings = listOfThings;
	}
>>>>>>> 14a41e787637a3d31617e0d160fb5480c8faf70c
}
