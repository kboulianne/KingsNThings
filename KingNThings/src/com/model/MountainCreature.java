package com.model;

import javafx.scene.paint.Color;

public class MountainCreature extends Creature {

	public MountainCreature(String name) {
		super(name);
		setDomain("Mountains");
		setColor(Color.BROWN);
	}
}
