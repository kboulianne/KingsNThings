package com.presenter;

import com.main.NetworkedMain;
import com.model.GameRoom;
import com.server.services.IGameRoomService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.view.GameRoomView;

public class GameRoomPresenter {
	private final GameRoomView view;
	private final IGameRoomService gameRoomSvc;
	private GameRoom room;

	
	
	public GameRoomPresenter(final GameRoomView view, final IGameRoomService gameRoomSvc) {
		this.view = view;
		this.gameRoomSvc = gameRoomSvc;
	}
	
	public final GameRoomView getView() {
		return view;
	}

	public void handleStartGameButton() {
		// Only show start button for host player.
		try {
			gameRoomSvc.startGame(room.getName());
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param owner Player owning the window
	 * @param room
	 */
	public void showGameRoom(GameRoom room) {
		NetworkedMain.setView(view);
		view.setGameRoom(NetworkedMain.getPlayer(), room);
		this.room = room;
		NetworkedMain.setRoomName(room.getName());
			
		refreshGameRoom();
	}
	

	public void refreshGameRoom() {
		try {
			room = gameRoomSvc.getGameRoom(room.getName());
			
			view.setGameRoom(NetworkedMain.getPlayer(), room);
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
	}
}