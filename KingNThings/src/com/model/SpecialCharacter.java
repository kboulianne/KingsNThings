package com.model;

import javafx.scene.paint.Color;

import com.presenter.Util;

public class SpecialCharacter extends Thing	{
	
	String alternateName;

	public SpecialCharacter(String name1, String name2) {
		super((Util.randomNumber(1, 2) == 1?name1:name2));
		
		if(getName().equals(name1)){
			alternateName = name2;
		}else{
			alternateName = name1;
		}
		setColor(Color.rgb(170,50,110));
		
		setImage("view/com/assets/pics/gamepieces/specialcharacters/" + getName() + ".jpeg");
	}
	

}
