package model.com;

import javafx.scene.image.Image;

public abstract class GamePiece {
	Image image;

	public void setImage(String directory) {
		image = new Image(directory);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
