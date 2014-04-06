package com.model;

import java.util.HashMap;

import javafx.scene.paint.Color;

public class IncomeCounter extends Thing {

	private String domain;
	private int goldVal;
	private static final HashMap<String, String> domains;
	static	{
		domains = new HashMap<String, String>();
		domains.put("timberland", "Forest");
		domains.put("silvermine", "Mountains");
		domains.put("peatbog", "Swamp");
		domains.put("oilfield", "Frozen Waste");
		domains.put("goldmine", "Mountains");
		domains.put("farmlands", "Plains");
		domains.put("elephantsgraveyard", "Jungle");
		domains.put("diamondfield", "Desert");
		domains.put("coppermine", "Mountains");
		domains.put("village", "none");
		domains.put("city", "none");
	}
	private static final HashMap<String, Integer> goldInc;
	static	{
		goldInc = new HashMap<String, Integer>();
		goldInc.put("timberland", 1);
		goldInc.put("silvermine", 2);
		goldInc.put("peatbog", 1);
		goldInc.put("oilfield", 3);
		goldInc.put("goldmine", 3);
		goldInc.put("farmlands", 1);
		goldInc.put("elephantsgraveyard", 3);
		goldInc.put("diamondfield", 1);
		goldInc.put("coppermine", 1);
		goldInc.put("village", 1);
		goldInc.put("city", 2);
	}
			
	public IncomeCounter(String name) {
		super(name);
		setImage("view/com/assets/pics/gamepieces/things/incomecounters/" + name + ".jpeg");
		setDomain(domains.get(name));
		setValue(goldInc.get(name));
		if(name.equals("village") || name.equals("city"))
			setColor(Color.KHAKI);
		else
			setColor(Color.rgb(160,140,160));
	}
	
	public IncomeCounter() {
	}

	//Get and set methods
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String str){
		domain = str;
	}
	
	public int getValue(){
		return goldVal;
	}
	
	public void setValue(int val){
		goldVal = val;
	}
}
