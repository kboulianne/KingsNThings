package com.model;

import java.util.HashMap;

import javafx.scene.paint.Color;

public class Treasure extends Thing	{

	private int goldVal;
	
	private static final HashMap<String, Integer> goldInc;
	static	{
		goldInc = new HashMap<String, Integer>();
		goldInc.put("treasurechest", 20);
		goldInc.put("sapphire", 5);
		goldInc.put("ruby", 10);
		goldInc.put("pearl", 5);
		goldInc.put("emerald", 10);
		goldInc.put("diamond", 5);
	}
	
	Treasure(String name) {
		super(name);
		setImage("view/com/assets/pics/gamepieces/things/treasure/" + name + ".jpeg");
		setValue(goldInc.get(name));
		setColor(Color.rgb(160,140,160));
	}

	//Get and set methods
	public int getValue()	{
		return goldVal;
	}
	
	public void setValue(int val)	{
		goldVal = val;
	}
}
