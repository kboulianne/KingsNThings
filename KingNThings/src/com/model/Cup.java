package com.model;

import java.util.ArrayList;

public class Cup extends GamePiece {
	private ArrayList<Thing> listOfThings;
	
	/*
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
	}*/

	public ArrayList<Thing> getListOfThings() {
		return listOfThings;
	}
	public void setListOfThings(ArrayList<Thing> listOfThings) {
		this.listOfThings = listOfThings;
	}
}
