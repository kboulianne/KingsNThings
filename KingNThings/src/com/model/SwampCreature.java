package com.model;

import javafx.scene.paint.Color;

public class SwampCreature extends Creature {

	public SwampCreature(String name) {
		super(name);
		setDomain("swamp");
		setColor(Color.GREENYELLOW);
	}
}
