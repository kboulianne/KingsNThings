package model.com;

import javafx.scene.paint.Color;

public class Player {
	PlayerId id; 		// {1, 2, 3, 4}
	Color color;	// {blue, green, red, yellow}
	Block block;
	private String name;
	
	public enum PlayerId { ONE(Color.BLUE),TWO(Color.GREEN),THREE(Color.RED),FOUR(Color.YELLOW); 
		private final Color color;
		PlayerId(Color c) {
			color = c;
		}
	}
	
	public Player(PlayerId id, String name) {
	    this.name = name;
	    color = id.color;
	    block = new Block();
	}
	
	public final String getName() {
	    return name;
	}
	
	public final void setName(final String name) {
	    this.name = name;
	}
}
