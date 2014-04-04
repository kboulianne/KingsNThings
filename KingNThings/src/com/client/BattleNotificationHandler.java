package com.client;

import javafx.application.Platform;

import com.main.KNTAppFactory;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;

public class BattleNotificationHandler extends BaseNotificationHandler {

	@Override
	public String[] handledNotifications() {
		return new String[] { "onBattleStarted" };
	}

	@Override
	public void process(final JSONRPC2Notification not, MessageContext arg1) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				switch (not.getMethod()) {
				case "onBattleStarted":
					KNTAppFactory.getBattlePresenter().onBattleStarted();
					break;
				}
			}
		});
	}

}
