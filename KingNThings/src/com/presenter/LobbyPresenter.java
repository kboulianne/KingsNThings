package com.presenter;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.main.KNTAppFactory;
import com.main.NetworkedMain;
import com.model.GameRoom;
import com.server.services.IGameRoomService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.view.LobbyView;

public class LobbyPresenter {
	private final LobbyView view;
	private final IGameRoomService gameRoomSvc;
	
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
	
	public void handleRefreshButton() {
		updateGameRoomsList();
	}

	public void handleHostButton(String roomName) {
		if (roomName == null || roomName.isEmpty()) return;
		
		try {
			GameRoom room = gameRoomSvc.createGameRoom(roomName, NetworkedMain.getPlayer());
			// Delegate to GameRoomPresenter
			KNTAppFactory.getGameRoomPresenter().showGameRoom(room);
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
		updateGameRoomsList();
	}

	public void handleJoinButton() {
		try {
			// Join the selected game room.
			GameRoom room = view.getGameRoomsList().getSelectionModel().getSelectedItem();
			if (room == null) return;
			
			gameRoomSvc.joinGameRoom(room.getName(), NetworkedMain.getPlayer());

			// Delegate to GameRoomPresenter
			KNTAppFactory.getGameRoomPresenter().showGameRoom(room);
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}

	}
	
	private void updateGameRoomsList() {
		try {
			rooms.setAll(gameRoomSvc.getGameRooms());
			
			// Select first entry, easier for debugging
			view.getGameRoomsList().getSelectionModel().selectFirst();
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
	}
	
	public void show() {
		NetworkedMain.setView(view);
		// FOR TESTING PURPOSES
		NetworkedMain.setTitle(NetworkedMain.getPlayer().getName());
		view.setPlayer(NetworkedMain.getPlayer());
		
		updateGameRoomsList();
	}
}
