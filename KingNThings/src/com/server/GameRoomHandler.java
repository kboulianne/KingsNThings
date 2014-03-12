package com.server;

import java.util.Collection;

import com.model.GameRoom;
import com.model.Player;
import com.model.game.Game;
import com.server.services.IGameRoomService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import static com.server.KNTServer.*;

/**
 * RealSubject implementation of the proxy pattern.
 * @author kurtis
 *
 */
//TODO: Separate implementation from handler?
public class GameRoomHandler extends KNTRequestHandler implements IGameRoomService {
	@Override
	public String[] handledRequests() {
		// Report available methods
		//TODO: Get method names from reflection
		return new String[] { "getGameRooms", "joinGameRoom", "createGameRoom",
				"startGame"};
	}
	
	@Override
	public GameRoom createGameRoom(String name, Player host) throws JSONRPC2Error {
		GameRoom room = null;
		
		synchronized (GAME_ROOMS) {
			// Make sure the room does not exist.
			if (!GAME_ROOMS.containsKey(name)) {
				room = new GameRoom(name, host);
				
				GAME_ROOMS.put(name, room);
			}
			else {
				// Throw error
				throw new JSONRPC2Error(1, "A game room with this name already exists.");
			}
		}
		
		return room;
	}
	
	@Override
	public Collection<GameRoom> getGameRooms() throws JSONRPC2Error {
		//TODO: Change me to collection
		// Must lock the collection to make sure no changes are made in between.
		synchronized (GAME_ROOMS) {
			return GAME_ROOMS.values();
		}
	}
	
	@Override
	public void joinGameRoom(String room, Player player) throws JSONRPC2Error {
		
		synchronized (GAME_ROOMS) {
			if (GAME_ROOMS.containsKey(room)) {
				GameRoom gr = GAME_ROOMS.get(room);
				
				if (gr.isFull())
					throw new JSONRPC2Error(3, "Game Room is full!");
				
				gr.addPlayer(player);
			}
			else {
				throw new JSONRPC2Error(2, "Room does not exist.");
			}
		}
	}

	@Override
	public void startGame(String room) throws JSONRPC2Error {
		// Lock the map
		synchronized (GAME_ROOMS) {
			if (GAME_ROOMS.containsKey(room)) {
				// Make sure there are four players in the room (including host.)
				GameRoom gr = GAME_ROOMS.get(room);
				
				// Make sure the game was not already started.
				if (gr.hasStarted()) throw new JSONRPC2Error(4, "Cannot start the game. It is already in progress");
				
				if (gr.isFull()) {
					// Ok to start the game.
					// Create and initialize the Game instance with Host and Opponents.
					Game game = new Game(gr.getHost(), gr.getPlayers());
					
					// Lock the Games map and add this game instance
					synchronized (GAMES) {
						GAMES.put(room, game);
					}
				}
				else {
					// For now
					throw new JSONRPC2Error(5, "Waiting for more players.");
				}
			}
			else {
				throw new JSONRPC2Error(3, "Room does not exist.");
			}
		}
	}
}