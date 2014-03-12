package com.client.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Collection;
import com.google.gson.reflect.TypeToken;
import com.model.GameRoom;
import com.model.Player;
import com.server.services.IGameRoomService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public class GameRoomServiceProxy extends ProxyBase implements IGameRoomService {
	
	// Socket to use for communicating with server. Kept open
	// for performance and simplicity.
	public GameRoomServiceProxy(BufferedReader reader, PrintWriter writer) throws IOException {
		super(reader, writer);
	}
	
	
	@Override
	public GameRoom createGameRoom(String name, Player p) throws JSONRPC2Error {
		return invokeOnServer("createGameRoom", GameRoom.class, name, p);
	}
	
	@Override
	public Collection<GameRoom> getGameRooms() throws JSONRPC2Error {
		Type t = new TypeToken<Collection<GameRoom>>() {}.getType();
		return invokeOnServer("getGameRooms", t);
	}
	
	@Override
	public void joinGameRoom(String room, Player player) throws JSONRPC2Error {
		//TODO: find way to infer from this method.
		invokeOnServer("joinGameRoom", room, player);
	}


	@Override
	public void startGame(String room) throws JSONRPC2Error {
		invokeOnServer("startGame");		
	}
}
