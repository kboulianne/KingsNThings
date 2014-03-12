package com.view;

import com.main.KNTAppFactory;
import com.model.GameRoom;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
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
	
	protected void buildView() {
		host = LabelBuilder.create()
			
		.build();
		
		guest1 = LabelBuilder.create()
				
		.build();
		
		guest2 = LabelBuilder.create()
				
		.build();
		
		guest3 = LabelBuilder.create()
				
		.build();
		
		startGame = ButtonBuilder.create()
			.onAction(START_GAME_EVENT)
		.build();
		
		getChildren().addAll(host, guest1, guest2, guest3);
	}
	
	public void setGameRoom(final GameRoom room) {
		host.setText(room.getHost().getName());
		if (room.getPlayers() != null && !room.getPlayers().isEmpty()) {
			guest1.setText(room.getPlayers().get(0).getName());
			guest2.setText(room.getPlayers().get(1).getName());
			guest3.setText(room.getPlayers().get(2).getName());
		}
	}
	
	private static final EventHandler<ActionEvent> START_GAME_EVENT = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			KNTAppFactory.getGameRoomPresenter().handleStartGameButton();
		}
	};
}
