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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.FlowPaneBuilder;
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
		
		//TODO: Make visible only to the host once we have the screen that captures player name.
		startGame = ButtonBuilder.create()
			.onAction(START_GAME_EVENT)
			.text("Start")
		.build();
		
		FlowPane bottomPane = FlowPaneBuilder.create()
			.alignment(Pos.CENTER_RIGHT)
			.children(startGame)
		.build();
		
		getChildren().addAll(host, guest1, guest2, guest3, bottomPane);
	}
	
	public void setGameRoom(final GameRoom room) {
		if (room == null) return;
		
		host.setText(room.getHost().getName());
		if (room.getPlayers() != null && !room.getPlayers().isEmpty()) {
			
			//TODO: Put guests in list?
			int i = 0;
			for (Player p : room.getPlayers()) {
				if (i == 0) guest1.setText(p.getName()); 
				else if (i == 1) guest2.setText(p.getName());
				else if (i == 2) guest3.setText(p.getName());
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
