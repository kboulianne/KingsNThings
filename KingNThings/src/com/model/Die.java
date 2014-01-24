package com.model;

import com.presenter.Util;
import javafx.scene.image.Image;

public class Die extends GamePiece {
	private int value;
	private Image image;
	
	public Die(){
	    roll();
	}
	
	public final int roll(){
		int randomNumber = Util.randomNumber(1, 6);
		setValue(randomNumber);

		this.setImage("view/com/assets/pics/die/die"+value+".png");
		if (image == null) {
		    image = getImage();
		}		
		return randomNumber;
	}
	
	// getters and setters
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

//    @Override
//    public void setImage(String dir) {
//	super.setImage(dir);
//	
//	// TODO makes things extremely messy
//	// Need to be invoked on FXApplication Thread.
//	Platform.runLater(new Runnable() {
//
//	    @Override
//	    public void run() {
//		imagePropery.set(getImage());
//	    }
//	});
//	
//    }
//	
//    public ObjectProperty<Image> getImageProperty() {
//	return imagePropery;
//    }
}
