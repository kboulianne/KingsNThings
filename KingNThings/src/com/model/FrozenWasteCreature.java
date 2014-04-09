package com.model;

import javafx.scene.paint.Color;

public class FrozenWasteCreature extends Creature {

	public FrozenWasteCreature(String name) {
		super(name);
		setDomain("Frozen Waste");
		setColor(Color.SILVER);
	}

	public FrozenWasteCreature() {}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
}
