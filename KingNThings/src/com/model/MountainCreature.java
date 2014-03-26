package com.model;

import javafx.scene.paint.Color;

public class MountainCreature extends Creature {

	public MountainCreature(String name) {
		super(name);
		setDomain("Mountain");
		setColor(Color.BROWN);
	}

	public MountainCreature() {
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
