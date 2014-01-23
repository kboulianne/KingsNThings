package com.model;

import javafx.scene.paint.Color;

public class PlainsCreature extends Creature {

	PlainsCreature(String name) {
		super(name);
		setDomain("Plains");
		setColor(Color.CORAL);
	}
}
