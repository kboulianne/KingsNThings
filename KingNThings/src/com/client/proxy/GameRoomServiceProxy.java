package com.client.proxy;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.reflect.TypeToken;
import com.model.GameRoom;
import com.model.Player;
import com.server.services.IGameRoomService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

public class GameRoomServiceProxy extends ProxyBase implements IGameRoomService {
		
	public GameRoomServiceProxy(LinkedBlockingQueue<JSONRPC2Response> inputMessages,
			LinkedBlockingQueue<JSONRPC2Request> outMessages) {
		super(inputMessages, outMessages);
	}

	@Override
	public GameRoom createGameRoom(String name, Player p) throws JSONRPC2Error {
		return invokeOnServer("createGameRoom", GameRoom.class, name, p);
	}
	
	@Override
	public GameRoom getGameRoom(String name) throws JSONRPC2Error {
		return invokeOnServer("getGameRoom", GameRoom.class, name);
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
		invokeOnServer("startGame", room);		
	}
}
