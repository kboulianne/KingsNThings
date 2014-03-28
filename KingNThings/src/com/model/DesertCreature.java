package com.model;

import javafx.scene.paint.Color;

public class DesertCreature extends Creature	{

	public DesertCreature(String name) {
		super(name);
		setDomain("Desert");
		setColor(Color.KHAKI);
	}

	public DesertCreature() {}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}


	
}
