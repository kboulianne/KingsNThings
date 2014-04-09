package com.server.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.Board;
import com.model.Creature;
import com.model.Cup;
import com.model.Game;
import com.model.Hex;
import com.model.Player;
import com.model.Player.PlayerId;

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
			
			Game game = room.getGame();
			
			
			Player p1 = null, p2 = null, p3 = null, p4 = null;
			
			// Extract old players by ID
			for (Player p : game.getPlayerOrder()) {
				if (p.getId().equals(PlayerId.ONE))
					p1 = p;
				else if (p.getId().equals(PlayerId.TWO))
					p2 = p;
				else if (p.getId().equals(PlayerId.THREE))
					p3 = p;
				else
					p4 = p;
			}
			
			Player player = null;
			// Replace the player names (unique attribute used in equals)
			// Loop hexes and replace names in player instances
			for (Hex h : b.getHexes()) {
				if (h.getHexOwner() == null) continue;
				
				// Update the player names
				if (h.getHexOwner().getId().equals(PlayerId.ONE))
					player = p1;
				else if (h.getHexOwner().getId().equals(PlayerId.TWO))
					player = p2;
				else if (h.getHexOwner().getId().equals(PlayerId.THREE))
					player = p3;
				else
					player = p4;
				
				h.setOwner(player);
				
				Map<Player, List<Creature>> map = new HashMap<>();
				
				// Loop the entry set and update the army map
				for (Map.Entry<Player, List<Creature>> entry : h.getArmies().entrySet()) {
					if (entry.getKey().getId().equals(PlayerId.ONE))
						player = p1;
					else if (entry.getKey().getId().equals(PlayerId.TWO))
						player = p2;
					else if (entry.getKey().getId().equals(PlayerId.THREE))
						player = p3;
					else
						player = p4;
					
					map.put(player, entry.getValue());
				}
				
				h.setArmies(map);
			}
			room.getGame().setBoard(b);
			notifyAllClients(room, Notifications.GAME_UPDATED);
		}
	}
}