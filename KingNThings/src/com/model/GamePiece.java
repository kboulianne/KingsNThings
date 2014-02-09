package com.model;

import javafx.scene.image.Image;

public abstract class GamePiece {
	private Image image;
	private String name;
	private String owner;
	
	public GamePiece()	{	}
	
	public GamePiece(String name)	{
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
