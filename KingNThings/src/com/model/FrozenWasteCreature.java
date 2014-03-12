package com.model;

import javafx.scene.paint.Color;

class FrozenWasteCreature extends Creature {

	FrozenWasteCreature(String name) {
		super(name);
		setDomain("Frozen Waste");
		setColor(Color.SILVER);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
}
