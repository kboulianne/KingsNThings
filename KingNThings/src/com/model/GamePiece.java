package com.model;

import java.util.Objects;
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GamePiece) {
			GamePiece gp = (GamePiece)obj;
			
			return image.equals(gp.image)
					&& name.equals(gp.name)
					&& owner.equals(gp.owner);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + Objects.hashCode(this.image);
		hash = 97 * hash + Objects.hashCode(this.name);
		hash = 97 * hash + Objects.hashCode(this.owner);
		return hash;
	}
	
	
}
