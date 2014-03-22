package com.client;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;
import com.thetransactioncompany.jsonrpc2.server.NotificationHandler;

public abstract class BaseNotificationHandler implements NotificationHandler {

	@Override
	public String[] handledNotifications() {
		return new String[] {"onPlayerJoinedRoom"};
	}

	@Override
	public void process(JSONRPC2Notification not, MessageContext arg1) {

	}

}
