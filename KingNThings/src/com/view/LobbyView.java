package com.view;

import com.main.KNTAppFactory;
import com.model.GameRoom;
import com.model.Player;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ListViewBuilder;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.FlowPaneBuilder;
import javafx.util.Callback;

public class LobbyView extends BorderPane {

	private Button refreshBtn;
	private Button hostBtn;
	private Button joinBtn;
	private ListView<GameRoom> gameRoomList;
	
	private Label playerLabel;
	
	public LobbyView() {
		buildView();
	}
	
	
	@SuppressWarnings("unchecked")
	protected void buildView() {
		// Top
		refreshBtn = ButtonBuilder.create()
			.text("Refresh")
			.onAction(REFRESH_EVENT)
		.build();
		
		playerLabel = LabelBuilder.create()
				.text("Lobby")
		.build();
		
		BorderPane topPane = BorderPaneBuilder.create()
				.padding(new Insets(0, 0, 10, 10))
				.left(playerLabel)
				.right(refreshBtn)
		.build();
		
		// Center
		@SuppressWarnings("rawtypes")
		ListViewBuilder<GameRoom, ? extends ListViewBuilder> builder =
				ListViewBuilder.create();
		gameRoomList = builder
			.onKeyPressed(LIST_ENTER_EVENT)
			.cellFactory(new Callback<ListView<GameRoom>, ListCell<GameRoom>>() {
				@Override
				public ListCell<GameRoom> call(ListView<GameRoom> param) {
					return new GameRoomListCell();
				}
			})
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
	
	public final void setPlayer(Player p) {
		playerLabel.setText("Welcome " + p.getName());
	}
	
	public final ListView<GameRoom> getGameRoomsList() {
		return gameRoomList;
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
			KNTAppFactory.getLobbyPresenter().handleJoinButton();
		}
	};
	
	private final EventHandler<KeyEvent> LIST_ENTER_EVENT = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER) {
				// If nothing selected in list, host
				if (gameRoomList.getSelectionModel().isEmpty()) {
					KNTAppFactory.getLobbyPresenter().handleHostButton();
				}
				else {
					KNTAppFactory.getLobbyPresenter().handleJoinButton();
				}
			}
		}
	};
	
	private static final class GameRoomListCell extends ListCell<GameRoom> {
		@Override
		protected void updateItem(GameRoom item, boolean empty) {
			super.updateItem(item, empty);
			
			if (empty) {
				setText(null);
				setGraphic(null);
			}
			else {
				// TODO: Add more info here. i.e. Number of players etc.
				setText(item.getName());
				setGraphic(null);
			}
		}
	}
}
