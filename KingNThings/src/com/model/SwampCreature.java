package com.model;

import javafx.scene.paint.Color;

class SwampCreature extends Creature {

	SwampCreature(String name) {
		super(name);
		setDomain("Swamp");
		setColor(Color.rgb(15, 150, 130));
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj); 
	}
	
	
}
