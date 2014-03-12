package com.server;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public class Errors {
	public static final JSONRPC2Error GAME_ROOM_ROOM_EXISTS = new JSONRPC2Error(1000, "A game room with this name already exists.");
	public static final JSONRPC2Error GAME_ROOM_ROOM_FULL = new JSONRPC2Error(1001, "Room is full.");
	public static final JSONRPC2Error GAME_ROOM_ROOM_INEXISTANT = new JSONRPC2Error(1002, "Room does not exist.");
	public static final JSONRPC2Error GAME_ROOM_GAME_IN_PROGRESS = new JSONRPC2Error(1003, "Game is already in progress");
}
