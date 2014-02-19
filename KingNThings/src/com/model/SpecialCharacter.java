package com.model;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.paint.Color;

import com.presenter.Util;

public class SpecialCharacter extends Creature	{
	
	private String alternateName;
	private boolean specialAbility;
	
	private static final Set<String> specialCreat;
	static	{
		specialCreat = new HashSet<>();
		specialCreat.add("icelord");
		specialCreat.add("assassinprimus");
		specialCreat.add("baronmunchausen");
		specialCreat.add("deerhunter");
		specialCreat.add("desertmaster");
		specialCreat.add("dwarfking");
		specialCreat.add("forestking");
		specialCreat.add("grandduke");
		specialCreat.add("junglelord");
		specialCreat.add("warlord");
		specialCreat.add("masterthief");
		specialCreat.add("mountainking");
		specialCreat.add("swampking");
		specialCreat.add("plainslord");
	}

	public SpecialCharacter(String name1, String name2) {
		super((Util.randomNumber(1, 2) == 1?name1:name2));
		
		if(getName().equals(name1)){
			setAlternateName(name2);
		}else{
			setAlternateName(name1);
		}
		setColor(Color.rgb(170,50,110));
		
		//Set special ability
		setSpecialAbility(specialCreat.contains(getName()));
		
		setImage("view/com/assets/pics/gamepieces/specialcharacters/" + getName() + ".jpeg");
	}

	public String getAlternateName() {
		return alternateName;
	}

	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}
	
	
	
	public String getAbilitiesString(){
		String abilities = "";
	    
	    if (getFly()) {
	    	abilities += " Flying";
	    }
	    if (isRanged()) {
	    	abilities += " Ranged";
	    }
	    if (getCharge()) {
	    	abilities += " Charging";
	    }
	    if(isMagic()){
	    	abilities += " Magic";
	    }
	    if(isSpecialAbility()){
	    	abilities += " Special-Ability";
	    }
	    if(abilities.isEmpty()){
	    	abilities = " None";
	    }
	    return abilities;
	}

	public boolean isSpecialAbility() {
		return specialAbility;
	}

	public void setSpecialAbility(boolean specialAbility) {
		this.specialAbility = specialAbility;
	}

}
