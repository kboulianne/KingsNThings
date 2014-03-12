package com.model;

import javafx.scene.paint.Color;

class JungleCreature extends Creature	{

	JungleCreature(String name) {
		super(name);
		setDomain("Jungle");
		setColor(Color.rgb(160, 190, 90));
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
}
