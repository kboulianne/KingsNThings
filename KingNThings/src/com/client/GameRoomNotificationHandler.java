package com.client;

import javafx.application.Platform;

import com.main.KNTAppFactory;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;

public class GameRoomNotificationHandler extends BaseNotificationHandler {
	
	@Override
	public void process(JSONRPC2Notification not, MessageContext arg1) {
		// For now, will mimic Server's RequestHandler
		switch (not.getMethod()) {
		case "onPlayerJoinedRoom":
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					KNTAppFactory.getGameRoomPresenter().refreshGameRoom();
				}
			});
			
			break;
		}
		
	}
}
