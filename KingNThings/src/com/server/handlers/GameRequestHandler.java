package com.server.handlers;

import com.model.Board;
import com.model.Cup;
import com.model.Game;
import com.model.Player;

import static com.server.KNTServer.*;

import com.server.Notifications;
import com.server.ServerGameRoom;
import com.server.services.IGameService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public class GameRequestHandler extends BaseRequestHandler implements IGameService {
	@Override
	public String[] handledRequests() {
		return new String[] { "getGame", "updateGame", "isPlayerTurn", "endTurn", "skipPhase", 
				"updateCup", "loadBoard"};
	}

	@Override
	public Game getGame(String roomName) throws JSONRPC2Error {
		Game game = null;
		synchronized (GAME_ROOMS) {
			game = ((ServerGameRoom)GAME_ROOMS.get(roomName)).getGame();
			
//			System.out.println(game.getBoard().getHexes());
		}
		
		return game;
	}
	
	@Override
	public void updateGame(String roomName, Game game) throws JSONRPC2Error {
		
		synchronized (GAME_ROOMS) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			
			// Make sure to keep the current player and playerorder players in sync
			for (int i = 0 ; i < game.getPlayerOrder().size() ; i ++) {
				Player p = game.getPlayerOrder().get(i);
				
				if (p.equals(game.getCurrentPlayer())) {
					game.getPlayerOrder().set(i, game.getCurrentPlayer());
				}
			}
			
			room.setGame(game);
		}		
	}

	//TODO: Remove me. This can be determined on the client.
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

			// Manages next turn and next phase.
			room.getGamePlay().next();
		}
	}

	@Override
	public void skipPhase(String roomName) throws JSONRPC2Error {
		ServerGameRoom room;
		synchronized (GAME_ROOMS.get(roomName)) {
			room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			room.getGamePlay().skipPhase();
		}		
	}

	@Override
	public void updateCup(String roomName, Cup cup) throws JSONRPC2Error {
		synchronized (GAME_ROOMS.get(roomName)) {
			ServerGameRoom room = (ServerGameRoom)GAME_ROOMS.get(roomName);
			
			room.getGame().setCup(cup);
		}
	}
	
	@Override
	public void loadBoard(String roomName, Board b) throws JSONRPC2Error {
		synchronized (GAME_ROOMS.get(roomName)) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			
			room.getGame().setBoard(b);
			
			notifyAllClients(room, Notifications.GAME_UPDATED);
		}
	}
}