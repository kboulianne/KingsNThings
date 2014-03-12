package com.model;

import javafx.scene.paint.Color;

class DesertCreature extends Creature	{

	DesertCreature(String name) {
		super(name);
		setDomain("Desert");
		setColor(Color.KHAKI);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}


	
}
