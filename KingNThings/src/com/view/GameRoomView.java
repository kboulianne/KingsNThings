package com.view;

import com.main.KNTAppFactory;
import com.model.GameRoom;
import com.model.Player;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.FlowPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameRoomView extends VBox {

	private Label host;
	private Label guest1;
	private Label guest2;
	private Label guest3;
	// Only available to the host.
	private Button startGame;
	// Waiting, Ready to start, Starting?
//	private Label roomStatus;
	// Only enabled to host.
	
	public GameRoomView() {
		buildView();
	}
	
	private void buildView() {
		
		Label title = new Label("Kings & Things");
		title.setPrefHeight(50);
		title.setStyle("-fx-font-size:30;-fx-font-weight: bold;");
		setAlignment(Pos.CENTER);
		
		VBox leftVBox = new VBox();
		ImageView dragonImg = new ImageView(new Image("view/com/assets/pics/dragon.png"));
		dragonImg.setFitWidth(400);
		dragonImg.setPreserveRatio(true);
		dragonImg.setSmooth(true);
		dragonImg.setCache(true);
		leftVBox.getChildren().add(dragonImg);
		
		VBox rightVBox = new VBox();
		Label players = new Label("Noble Players:");
		players.setStyle("-fx-font-weight: bold;");
		players.setGraphicTextGap(20);
		host = LabelBuilder.create().build();
		guest1 = LabelBuilder.create().build();
		guest2 = LabelBuilder.create().build();
		guest3 = LabelBuilder.create().build();
		rightVBox.setAlignment(Pos.CENTER);
		rightVBox.setSpacing(12);
		
		//TODO: Make visible only to the host once we have the screen that captures player name.
		startGame = ButtonBuilder.create()
			.onAction(START_GAME_EVENT)
			.text("Start")
		.build();
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				if (arg0.getCode() == KeyCode.ENTER)
					KNTAppFactory.getGameRoomPresenter().handleStartGameButton();
			}
		});
		
		FlowPane bottomPane = FlowPaneBuilder.create()
			.alignment(Pos.CENTER)
			.children(startGame)
		.build();
		
		
		rightVBox.getChildren().addAll(players, host, guest1, guest2, guest3, bottomPane);
		
		HBox hBox = new HBox();
		hBox.getChildren().addAll(leftVBox, rightVBox);
		
		getChildren().addAll(title, hBox);
		
		//getStylesheets().add("view/com/assets/docs/kingsnthings.css");
		//getStylesheets().add("view/com/assets/docs/connectionScreen.css");
	}
	
	public void setGameRoom(final Player owner, final GameRoom room) {
		if (room == null) return;
		
		host.setText(room.getHost().getName());
		host.setTextFill(room.getHost().getColor());
		
		if (owner.equals(room.getHost())) {
			startGame.setVisible(true);
		}
		else {
			startGame.setVisible(false);
		}
		
		if (room.getPlayers() != null && !room.getPlayers().isEmpty()) {
			
			//TODO: Put guests in array
			int i = 0;
			for (Player p : room.getPlayers()) {
				if (i == 0) {
					guest1.setText(p.getName());
					guest1.setTextFill(p.getColor());
				}
				else if (i == 1) {
					guest2.setText(p.getName());
					guest2.setTextFill(p.getColor());
				}
				else if (i == 2) {
					guest3.setText(p.getName());
					guest3.setTextFill(p.getColor());
				}
				i ++;
			}
		}
	}
	
	private static final EventHandler<ActionEvent> START_GAME_EVENT = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			KNTAppFactory.getGameRoomPresenter().handleStartGameButton();
		}
	};
}
