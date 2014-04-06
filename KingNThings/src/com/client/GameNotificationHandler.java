package com.client;

import javafx.application.Platform;

import com.main.KNTAppFactory;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;

public class GameNotificationHandler extends BaseNotificationHandler {

	@Override
	public String[] handledNotifications() {
		return new String[] {"onGameStarted", "onTurnEnded", "onPhaseEnded", "onGameEnded"};
	}
	
	@Override
	public void process(final JSONRPC2Notification not, MessageContext arg1) {
		// We are on the response/notification processing thread. Execute on FX thread.
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				switch (not.getMethod()) {
				case "onGameStarted":
					// Show GameView
					KNTAppFactory.getGamePresenter().onGameStarted();
					break;
				case "onTurnEnded":
					KNTAppFactory.getGamePresenter().onTurnEnded();
					break;
				case "onPhaseEnded":
					KNTAppFactory.getGamePresenter().onPhaseEnded();
					break;
				case "onGameEnded":
					KNTAppFactory.getGamePresenter().onGameEnded();
					break;
				default:
					break;
				}					
			}
		});

	}
}
