package model.com;

import javafx.scene.paint.Color;

public class Player {
	int id; 		// {1, 2, 3, 4}
	Color color;	// {blue, green, red, yellow}
	Block block;
	private String name;
	
	public Player(String name) {
	    this.name = name;
	}
	
	public final String getName() {
	    return name;
	}
	
	public final void setName(final String name) {
	    this.name = name;
	}
}
