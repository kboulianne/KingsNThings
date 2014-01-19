package model.com;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class Player {
	PlayerId id; 		// {1, 2, 3, 4}
	Color color;	// {blue, green, red, yellow}
	Block block;
	private StringProperty name;
	
	public enum PlayerId { ONE(Color.BLUE),TWO(Color.GREEN),THREE(Color.RED),FOUR(Color.YELLOW); 
		private final Color color;
		PlayerId(Color c) {
			color = c;
		}
	}
	
	public Player(PlayerId id, String name) {
	    this.name = new SimpleStringProperty(name);
	    color = id.color;
	    block = new Block();
	}
	
	/**
	 * Convenience getter for the player's name.
	 * @return 
	 */
	public final String getName() {
	    return name.get();
	}
	
	public final StringProperty getNameProperty() {
	    return name;
	}
	
	public final void setNameProperty(final StringProperty name) {
	    this.name = name;
	}
}
