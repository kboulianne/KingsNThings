package com.presenter;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.model.GameRoom;
import com.model.Player;
import com.model.Player.PlayerId;
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
		System.out.println("Refresh");
	}

	public void handleHostButton() {
		System.out.println("Host");
		//TODO: Testing
		Player host = new Player(PlayerId.ONE, "Tester 1");
		try {
			gameRoomSvc.createGameRoom("Tester 1 Game Room", host);
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateGameRoomsList();
	}

	public void handleJoinEvent() {
		System.out.println("Join");		
	}

	public void handleWindowShown() {
		System.out.println("Fetching GameRooms.");
		updateGameRoomsList();
	}
	
	private void updateGameRoomsList() {
		try {
			rooms.setAll(gameRoomSvc.getGameRooms());
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
