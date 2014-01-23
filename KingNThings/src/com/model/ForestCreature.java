package com.model;

import javafx.scene.paint.Color;

public class ForestCreature extends Creature {

	ForestCreature(String name) {
		super(name);
		setDomain("Forest");
		setColor(Color.DARKGREEN);
	}
}
