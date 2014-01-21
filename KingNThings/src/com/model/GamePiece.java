package com.model;

import javafx.scene.image.Image;

public abstract class GamePiece {
	Image image;
	String name;
	
	GamePiece()	{	}
	
	GamePiece(String name)	{
	    setName(name);
	}
	
	// getters and setters
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setImage(String directory) {
		image = new Image(directory);
	}

	public Image getImage() {
	    return image;
	}

	public void setImage(Image i) {
	    image = i;
	}
}
