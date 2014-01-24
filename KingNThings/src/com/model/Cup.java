package com.model;

import java.util.ArrayList;

public class Cup extends GamePiece	{
	ArrayList<Thing> listOfThings;
	
	public Cup()	{
		listOfThings = Thing.createThings();
	}
	
	//Get and set methods
	public ArrayList<Thing> getThings()	{	return listOfThings;	}
	public void setThings(ArrayList<Thing> things)	{	listOfThings = things;	}
}
