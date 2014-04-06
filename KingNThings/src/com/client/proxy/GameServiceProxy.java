package com.client.proxy;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;

import com.main.NetworkedMain;
import com.model.Board;
import com.model.Cup;
import com.model.Game;
import com.model.Player;
import com.server.services.IGameService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

public class GameServiceProxy extends ProxyBase implements IGameService {
	
	public GameServiceProxy(LinkedBlockingQueue<JSONRPC2Response> in,
			LinkedBlockingQueue<JSONRPC2Request> out) {
		super(in, out);
	}
	
	@Override
	public Game getGame(String roomName) throws JSONRPC2Error {
		Game game = invokeOnServer("getGame", Game.class, roomName);
		// Keep Owner info synchronized
		NetworkedMain.setPlayer(game.getUpdatedPlayer(NetworkedMain.getPlayer()));
		
		return game;
	}
	
	@Override
	public void updateGame(String roomName, Game game) throws JSONRPC2Error {
		invokeOnServer("updateGame", roomName, game);
	}

	@Override
	public void updateCup(String roomName, Cup cup) throws JSONRPC2Error {
		invokeOnServer("updateCup", roomName, cup);
	}
	
	@Override
	public boolean isPlayerTurn(String roomName, Player p) throws JSONRPC2Error {
		return invokeOnServer("isPlayerTurn", Boolean.class, roomName, p);
	}

	@Override
	public void endTurn(String gameRoom, Player p) throws JSONRPC2Error {
		invokeOnServer("endTurn", gameRoom, p);		
	}
	
	@Override
	public void skipPhase(String roomName) throws JSONRPC2Error {
		invokeOnServer("skipPhase", roomName);
	}

	@Override
	public void loadBoard(String roomName, Board b) throws JSONRPC2Error {
		invokeOnServer("loadBoard", roomName, b);
	}
}
