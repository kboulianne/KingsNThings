package com.view;

import com.main.KNTAppFactory;
import com.model.GameRoom;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ListView;
import javafx.scene.control.ListViewBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.FlowPaneBuilder;

public class LobbyView extends BorderPane {

	private Button refreshBtn;
	private Button hostBtn;
	private Button joinBtn;
	private ListView<GameRoom> gameRoomList;
	
	public LobbyView() {
		buildView();
	}
	
	protected void buildView() {
		// Top
		refreshBtn = ButtonBuilder.create()
			.text("Refresh")
			.onAction(REFRESH_EVENT)
		.build();
		
		Label lobbyLbl = LabelBuilder.create()
				.text("Lobby")
		.build();
		
		BorderPane topPane = BorderPaneBuilder.create()
				.padding(new Insets(0, 0, 10, 10))
				.left(lobbyLbl)
				.right(refreshBtn)
		.build();
		
		// Center
		@SuppressWarnings("rawtypes")
		ListViewBuilder<GameRoom, ? extends ListViewBuilder> builder =
				ListViewBuilder.create();
		gameRoomList = builder
		.build();
		
		// Bottom
		hostBtn = ButtonBuilder.create()
				.text("Host")
				.onAction(HOST_EVENT)
		.build();
		joinBtn = ButtonBuilder.create()
				.text("Join")
				.onAction(JOIN_EVENT)
		.build();
		
		FlowPane bottomPane = FlowPaneBuilder.create()
				.alignment(Pos.CENTER_RIGHT)
				.padding(new Insets(10, 0, 5, 10))
				.hgap(10)
				.children(hostBtn, joinBtn)
		.build();
		
		setTop(topPane);
		setCenter(gameRoomList);
		setBottom(bottomPane);
	}
	
	// Handlers
	private final EventHandler<ActionEvent> REFRESH_EVENT = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			KNTAppFactory.getLobbyPresenter().handleRefreshButton();
		}
	};
	
	private final EventHandler<ActionEvent> HOST_EVENT = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			KNTAppFactory.getLobbyPresenter().handleHostButton();
		}
	};
	
	private final EventHandler<ActionEvent> JOIN_EVENT = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			KNTAppFactory.getLobbyPresenter().handleJoinEvent();
		}
	};
}
