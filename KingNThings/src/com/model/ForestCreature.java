package com.model;

import javafx.scene.paint.Color;

class ForestCreature extends Creature {

	ForestCreature(String name) {
		super(name);
		setDomain("Forest");
		setColor(Color.rgb(100, 165, 95));//DARKGREEN.brighter());
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
}
