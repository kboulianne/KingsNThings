package com.presenter;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import com.main.LobbyTestMain;
import com.model.GameRoom;
import com.model.Player;
import com.server.services.IGameRoomService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.view.GameRoomView;

public class GameRoomPresenter {
	private final GameRoomView view;
	private final IGameRoomService gameRoomSvc;
	private GameRoom room;
	//TODO Keep player and game room in application main.
	private Player owner;
	
	Service<GameRoom> service = new Service<GameRoom>() {
		@Override
		protected Task<GameRoom> createTask() {
			return new Task<GameRoom>() {	
				@Override
				protected GameRoom call() throws Exception {
					new Timer().scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							refreshGameRoom();
						}
					}, 1000, 2000);
					return null;
				}
			};
		}
	};
	
	
	public GameRoomPresenter(final GameRoomView view, final IGameRoomService gameRoomSvc) {
		this.view = view;
		this.gameRoomSvc = gameRoomSvc;
	}
	
	public final GameRoomView getView() {
		return view;
	}

	public void handleStartGameButton() {
		// Only show start button for host player.
	}
	
	/**
	 * 
	 * @param owner Player owning the window
	 * @param room
	 */
	public void showGameRoom(Player owner, GameRoom room) {
		LobbyTestMain.setView(view);
		view.setGameRoom(owner, room);
		this.room = room;
		this.owner = owner;
		
		service.start();
	}
	
	// TODO: In the future, subscribe to notifications on the server. Lets just get things working first.
	/**
	 * HACK FOR NOW. Request data refresh every two seconds.
	 */
	private void refreshGameRoom() {
		try {
			room = gameRoomSvc.refreshGameRoom(room.getName());
			
			// This must be done on FXApplicationThread
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					view.setGameRoom(owner, room);
				}
			});
			
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}