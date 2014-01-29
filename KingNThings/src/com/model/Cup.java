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
}
