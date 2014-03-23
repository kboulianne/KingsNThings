package com.server;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;

public class Notifications {
	public static final JSONRPC2Notification PLAYER_JOINED_GAME_ROOM = 
			new JSONRPC2Notification("onPlayerJoinedRoom");
	public static final JSONRPC2Notification GAME_STARTED =
			new JSONRPC2Notification("onGameStarted");
	public static final JSONRPC2Notification TURN_ENDED =
			new JSONRPC2Notification("onTurnEnded");
}
