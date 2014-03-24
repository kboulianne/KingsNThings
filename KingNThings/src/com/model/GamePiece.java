package com.model;

import java.util.Objects;
import javafx.scene.image.Image;

abstract class GamePiece {
	// Do not serialize the image, let serializer/deserializer handle this as directory string.
	//TODO: Server now has image instances which does not make sense.
	private transient Image image;
	private String imageDirectory;
	private String name;
	private String owner;
	
	public GamePiece()	{}
	
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
		this.imageDirectory = directory;
		// Null the image so it gets recreated lazily when getImage is called.
		image = null;		
//		image = new Image(directory);
	}
	
	public Image getImage() {
	    // Create the image instance if null.
		if (image == null) {
			image = new Image(imageDirectory);
		}
		
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
			
			return Objects.equals(image, gp.image)
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
