package com.presenter;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import com.main.KNTAppFactory;
import com.main.NetworkedMain;
import com.model.GameRoom;
import com.model.Player;
import com.server.services.IGameRoomService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.view.GameRoomView;

public class GameRoomPresenter {
	private final GameRoomView view;
	private final IGameRoomService gameRoomSvc;
	private GameRoom room;
	
	private final Timer updateTimer = new Timer();
	Service<GameRoom> service = new Service<GameRoom>() {
		@Override
		protected Task<GameRoom> createTask() {
			return new Task<GameRoom>() {	
				@Override
				protected GameRoom call() throws Exception {
					updateTimer.scheduleAtFixedRate(new TimerTask() {
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
		try {
			gameRoomSvc.startGame(room.getName());
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
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
		view.setGameRoom(room);
		this.room = room;
		
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
					view.setGameRoom(room);
					
					// Check if game was started
					if (room.hasStarted()) {
						// Kill the update service
						service.cancel();
						updateTimer.cancel();
						
						NetworkedMain.setRoomName(room.getName());
						// Delegate to GamePresenter.
						KNTAppFactory.getGamePresenter().showGameUI();
						

					}
				}
			});
			
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}