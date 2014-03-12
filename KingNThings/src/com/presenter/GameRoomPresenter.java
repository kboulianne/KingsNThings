package com.presenter;

import javafx.stage.Stage;

import com.main.KNTAppFactory;
import com.main.LobbyTestMain;
import com.model.GameRoom;
import com.server.services.IGameRoomService;
import com.view.GameRoomView;

public class GameRoomPresenter {
	private final GameRoomView view;
	private final IGameRoomService gameRoomSvc;
	private Stage window;
	
	public GameRoomPresenter(final GameRoomView view, final IGameRoomService gameRoomSvc) {
		this.view = view;
		this.gameRoomSvc = gameRoomSvc;
	}
	
	public final GameRoomView getView() {
		return view;
	}

	public void handleStartGameButton() {
			
	}
	
	public void showGameRoom(GameRoom room) {
		LobbyTestMain.setView(view);
		view.setGameRoom(room);
	}
}
