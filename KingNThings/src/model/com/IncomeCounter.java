package model.com;

import java.util.HashMap;

public class IncomeCounter extends Thing {

		private static final HashMap<String, String> domains;
		static	{
			domains = new HashMap<String, String>();
			domains.put("timberland", "forest");
			domains.put("silvermine", "mountains");
			domains.put("peatbog", "swamp");
			domains.put("oilfield", "frozen waste");
			domains.put("goldmine", "mountains");
			domains.put("farmlands", "plains");
			domains.put("elephantsgraveyard", "jungle");
			domains.put("diamondfield", "desert");
			domains.put("coppermine", "mountains");
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
		}
		private String domain;
		private int goldVal;
		
	IncomeCounter(String name) {
		super(name);
		setImage("view/com/assets/pics/gamepieces/things/incomecounters/" + name + ".jpeg");
		setDomain(domains.get(name));
		setValue(goldInc.get(name));
	}
	
	//Get and set methods
	public String getDomain()	{
		return domain;
	}
	
	public void setDomain(String str)	{
		domain = str;
	}
	
	public int getValue()	{
		return goldVal;
	}
	
	public void setValue(int val)	{
		goldVal = val;
	}
}
