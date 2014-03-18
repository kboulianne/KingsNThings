package com.presenter;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.main.KNTAppFactory;
import com.main.LobbyTestMain;
import com.model.GameRoom;
import com.model.Player;
import com.server.services.IGameRoomService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.view.LobbyView;

public class LobbyPresenter {
	private final LobbyView view;
	private final IGameRoomService gameRoomSvc;
	private Player player;
//	private Stage window;
	
	// Observable list that contains the gamerooms contained in the ui list.
	private final ObservableList<GameRoom> rooms;
	
	// Requires GameRoomService
	public LobbyPresenter(final LobbyView view, final IGameRoomService gameRoomSvc) {
		this.view = view;
		this.gameRoomSvc = gameRoomSvc;
		
		rooms = FXCollections.observableList(new ArrayList<GameRoom>());
		view.getGameRoomsList().setItems(rooms);
	}
	
	public final ObservableList<GameRoom> getGameRooms() {
		return rooms;
	}
	
	/**
	 * Gets the view managed by this presenter.
	 * @return The view
	 */
	public final LobbyView getView() {
		return view;
	}
	
//	public final void setWindow(Stage s) {
//		window = s;
//	}
	
	public void handleRefreshButton() {
		updateGameRoomsList();
	}

	public void handleHostButton() {
		System.out.println("Host");
		try {
			GameRoom room = gameRoomSvc.createGameRoom("Tester 1's Game Room", player);
			
			// Delegate to GameRoomPresenter
			KNTAppFactory.getGameRoomPresenter().showGameRoom(player, room);
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateGameRoomsList();
	}

	public void handleJoinButton() {
		//TODO: Testing
//		Player guest = new Player(PlayerId.ONE, "Tester 2");
		
		try {
			// Join the selected game room.
			GameRoom room = view.getGameRoomsList().getSelectionModel().getSelectedItem();
			if (room == null) return;
			
			gameRoomSvc.joinGameRoom(room.getName(), player);
			// Delegate to GameRoomPresenter
			KNTAppFactory.getGameRoomPresenter().showGameRoom(player, room);
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	public void handleWindowShown() {
//		System.out.println("Fetching GameRooms.");
//		updateGameRoomsList();
//	}
	
	private void updateGameRoomsList() {
		try {
			rooms.setAll(gameRoomSvc.getGameRooms());
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void show(Player p) {
		LobbyTestMain.setView(view);
		this.player = p;
		view.setPlayer(p);
		
		updateGameRoomsList();
	}
}
