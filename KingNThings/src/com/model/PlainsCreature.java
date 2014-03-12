package com.model;

import javafx.scene.paint.Color;

class PlainsCreature extends Creature {

	PlainsCreature(String name) {
		super(name);
		setDomain("Plains");
		setColor(Color.CORAL);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
