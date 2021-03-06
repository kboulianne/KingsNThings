package com.server;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;

public class Notifications {
	public static final JSONRPC2Notification PLAYER_JOINED_GAME_ROOM = 
			new JSONRPC2Notification("onPlayerJoinedRoom");
	public static final JSONRPC2Notification GAME_STARTED =
			new JSONRPC2Notification("onGameStarted");
//	public static final JSONRPC2Notification TURN_START =
//			new JSONRPC2Notification("onTurnStarted");
	public static final JSONRPC2Notification TURN_ENDED =
			new JSONRPC2Notification("onTurnEnded");
	public static final JSONRPC2Notification PHASE_ENDED = 
			new JSONRPC2Notification("onPhaseEnded");
	public static final JSONRPC2Notification BATTLE_STARTED = 
			new JSONRPC2Notification("onBattleStarted");
	public static final JSONRPC2Notification BATTLE_UPDATED = new JSONRPC2Notification("onBattleUpdated");
	public static final JSONRPC2Notification BATTLE_TURN_ENDED = new JSONRPC2Notification("onBattleTurnEnded");
	public static final JSONRPC2Notification BATTLE_ENDED = 
			new JSONRPC2Notification("onBattleEnded");
	public static final JSONRPC2Notification GAME_ENDED = 
			new JSONRPC2Notification("onGameEnded");
	public static final JSONRPC2Notification GAME_UPDATED = new JSONRPC2Notification("onGameUpdated");
}
