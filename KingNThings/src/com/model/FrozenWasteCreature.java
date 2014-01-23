package com.model;

import javafx.scene.paint.Color;

public class FrozenWasteCreature extends Creature {

	FrozenWasteCreature(String name) {
		super(name);
		setDomain("Frozen Waste");
		setColor(Color.SILVER);
	}
}
