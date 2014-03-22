package com.server;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;

public class Notifications {
	public static final JSONRPC2Notification PLAYER_JOINED_GAME_ROOM = 
			new JSONRPC2Notification("onPlayerJoinedRoom");
}
