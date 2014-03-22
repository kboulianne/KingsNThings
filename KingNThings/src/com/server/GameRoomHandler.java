package com.server;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.model.Game;
import com.model.GameRoom;
import com.model.Player;
import com.server.services.IGameRoomService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;

import static com.server.KNTServer.*;
import static com.server.Errors.*;

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
		return new String[] { "getGameRooms", "refreshGameRoom", "joinGameRoom", "createGameRoom",
				"startGame"};
	}
	
	@Override
	public GameRoom createGameRoom(String name, Player host) throws JSONRPC2Error {
		ServerGameRoom room = null;
		
		synchronized (GAME_ROOMS) {
			// Make sure the room does not exist.
			if (!GAME_ROOMS.containsKey(name)) {
				room = new ServerGameRoom(name, host);
				
				GAME_ROOMS.put(name, room);
			}
			else {
				// Throw error
				throw GAME_ROOM_ROOM_EXISTS;
			}
		}
		
		return room;
	}
	
	@Override
	public GameRoom refreshGameRoom(String name) throws JSONRPC2Error {
		synchronized (GAME_ROOMS) {
			if (!GAME_ROOMS.containsKey(name)) {
				throw GAME_ROOM_ROOM_INEXISTANT;
			}
			else {
				return GAME_ROOMS.get(name);
			}
		}
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
				ServerGameRoom gr = (ServerGameRoom)GAME_ROOMS.get(room);
				
				if (gr.isFull())
					throw GAME_ROOM_ROOM_FULL;
				
				gr.addPlayer(player);
				
				// TESTING
				// Player joined. Notify other clients.
				// TODO: Need to ensure that the request takes priority. Meaning respond to request, then send notification.
				gr.notifyOthers(player.getName(), Notifications.PLAYER_JOINED_GAME_ROOM);
			}
			else {
				throw GAME_ROOM_ROOM_INEXISTANT;
			}
		}
	}

	@Override
	public void startGame(String room) throws JSONRPC2Error {
		// Lock the map
		synchronized (GAME_ROOMS) {
			if (GAME_ROOMS.containsKey(room)) {
				// Make sure there are four players in the room (including host.)
				ServerGameRoom gr = (ServerGameRoom)GAME_ROOMS.get(room);
				
				// Make sure the game was not already started.
				if (gr.hasStarted()) throw GAME_ROOM_GAME_IN_PROGRESS;
				
				if (gr.isFull()) {
					// Ok to start the game.
					// Create and initialize the Game instance with Host and Opponents.
					Game game = new Game(gr.getHost(), gr.getPlayers());
					
					gr.setGame(game);
					gr.setStarted(true);
					// Lock the Games map and add this game instance
//					synchronized (GAMES) {
//						GAMES.put(room, game);
//					}
				}
				else {
					// For now
					throw new JSONRPC2Error(5, "Waiting for more players.");
				}
			}
			else {
				throw GAME_ROOM_ROOM_INEXISTANT;
			}
		}
	}
}