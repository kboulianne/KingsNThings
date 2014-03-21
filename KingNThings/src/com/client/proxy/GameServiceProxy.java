package com.client.proxy;

import java.io.BufferedReader;
import java.io.PrintWriter;

import com.main.NetworkedMain;
import com.model.Game;
import com.model.Player;
import com.server.services.IGameService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public class GameServiceProxy extends ProxyBase implements IGameService {
	
	public GameServiceProxy(BufferedReader reader, PrintWriter writer) {
		super(reader, writer);
	}
	
	// TODO: Keep a local instance of the game.
	@Override
	public Game refreshGame(String roomName) throws JSONRPC2Error {
		Game game = invokeOnServer("refreshGame", Game.class, roomName);
		// Keep Owner info synchronized
		NetworkedMain.setPlayer(game.getUpdatedPlayer(NetworkedMain.getPlayer()));
		
		return game;
	}
	
	@Override
	public void updateGame(String roomName, Game game) throws JSONRPC2Error {
		invokeOnServer("updateGame", roomName, game);
	}

	@Override
	public boolean isPlayerTurn(String roomName, Player p) throws JSONRPC2Error {
		return invokeOnServer("isPlayerTurn", Boolean.class, roomName, p);
	}
}
