package com.model;

import javafx.scene.paint.Color;

public class JungleCreature extends Creature	{

	public JungleCreature(String name) {
		super(name);
		setDomain("Jungle");
		setColor(Color.rgb(160, 190, 90));
	}
}
