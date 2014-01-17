package model.com;

import javafx.scene.image.Image;

public class GamePiece {
	Image image;


	public void setImage(String directory) {
		image = new Image(directory);
	}
}
