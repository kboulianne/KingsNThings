package com.server;

import com.model.Player;
import com.model.game.Game;

import static com.server.KNTServer.*;

import com.server.services.IGameService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public class GameHandler extends KNTRequestHandler implements IGameService {
	@Override
	public String[] handledRequests() {
		return new String[] { "refreshGame" };
	}

	@Override
	public Game refreshGame(String roomName) throws JSONRPC2Error {
		Game game = null;
		synchronized (GAME_ROOMS) {
			game = ((ServerGameRoom)GAME_ROOMS.get(roomName)).getGame();
		}
		
		return game;
	}
	
}