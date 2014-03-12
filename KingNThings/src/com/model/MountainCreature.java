package com.model;

import javafx.scene.paint.Color;

class MountainCreature extends Creature {

	MountainCreature(String name) {
		super(name);
		setDomain("Mountain");
		setColor(Color.BROWN);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
