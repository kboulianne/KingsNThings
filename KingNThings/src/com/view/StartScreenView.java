package com.view;

import com.main.KNTAppFactory;
import com.presenter.StartScreenCntrl;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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
		title.setId("title");

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
	
//	public void show() {
//		VBox container = new VBox();
//		container.setMaxWidth(478);
//		container.setSpacing(10);
//		container.getChildren().addAll(title, playersName, textField, startButton);
//
//		VBox root = new VBox();
//		root.setId("vBox");
//		root.getChildren().addAll(crownImg, container);
//		root.setAlignment(Pos.CENTER);
//
//		Scene scene = new Scene(root);// 1000 x 600
//		scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
//		scene.getStylesheets().add("view/com/assets/docs/startScreen.css");
//
//		//Stage stage = Main.getStage();
//		//stage.setScene(scene);
//		//stage.show();
//
//		//fade
//		FadeTransition ft = new FadeTransition(Duration.millis(2000), root);
//		ft.setFromValue(0.5);
//		ft.setToValue(1.0);
//		ft.play();
//
//		new StartScreenCntrl(textField, startButton);
//	}

}
