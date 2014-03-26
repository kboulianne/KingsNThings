package com.view;

import com.main.KNTAppFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class StartScreenView extends VBox {

	public StartScreenView() {
		buildView();
	}
	
	protected void buildView() {
		ImageView crownImg = new ImageView(new Image("view/com/assets/pics/crown.png"));
		crownImg.setFitWidth(500); //600
		crownImg.setPreserveRatio(true);
		crownImg.setSmooth(true);
		crownImg.setCache(true);

		Label title = new Label("Kings & Things");
		title.setStyle("-fx-font-size:30;-fx-font-weight: bold;");

		Label playersName = new Label("Enter your Noble's name:");
		final TextField textField = new TextField();
		textField.setId("startTF");
		textField.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				KNTAppFactory.getStartScreenPresenter().handlePlay(textField.getText());
			}
		});

		Button startButton = new Button("Play");
		startButton.setId("startButton");
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				KNTAppFactory.getStartScreenPresenter().handlePlay(textField.getText());				
			}
		});

		setMaxWidth(478);
		setSpacing(10);
		getChildren().addAll(crownImg, title, playersName, textField, startButton);
		setAlignment(Pos.CENTER);
	}
}
