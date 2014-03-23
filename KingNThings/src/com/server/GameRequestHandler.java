package com.server;

import com.model.Game;
import com.model.Player;

import static com.server.KNTServer.*;

import com.server.services.IGameService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public class GameRequestHandler extends BaseRequestHandler implements IGameService {
	@Override
	public String[] handledRequests() {
		return new String[] { "refreshGame", "updateGame", "isPlayerTurn", "endTurn" };
	}

	@Override
	public Game refreshGame(String roomName) throws JSONRPC2Error {
		Game game = null;
		synchronized (GAME_ROOMS) {
			game = ((ServerGameRoom)GAME_ROOMS.get(roomName)).getGame();
		}
		
		return game;
	}
	
	@Override
	public void updateGame(String roomName, Game game) throws JSONRPC2Error {
		
		synchronized (GAME_ROOMS) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			
			room.setGame(game);
		}		
	}

	@Override
	public boolean isPlayerTurn(String roomName, Player p) throws JSONRPC2Error {
		synchronized (GAME_ROOMS) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			
			return room.getGame().getCurrentPlayer().equals(p);
		}
	}

	@Override
	public void endTurn(String gameRoom, Player p) throws JSONRPC2Error {
		// Notify other clients that the player completed his/her turn.
		synchronized (GAME_ROOMS.get(gameRoom)) {
			ServerGameRoom room = (ServerGameRoom)GAME_ROOMS.get(gameRoom);
			// TODO: Update game here?
			room.notifyOtherClients(p.getName(), Notifications.TURN_ENDED);
		}
	}
}