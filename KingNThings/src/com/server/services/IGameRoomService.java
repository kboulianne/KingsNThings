package com.server.services;

import java.util.Collection;

import com.model.GameRoom;
import com.model.Player;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;


/*
 * Subject interface of proxy pattern.
 */
public interface IGameRoomService {
	//TODO: The exceptions thrown are Implementation specific. Change them to Throwable?
	/**
	 * Creates a new GameRoom instance with given name and Player that acts as "host" or "creator" of the room.
	 * @param name The unique name with which the GameRoom should be created.
	 * @param host The Player creating/hosting the game. 
	 * @return The newly created room.
	 * @throws JSONRPC2Error
	 */
	GameRoom createGameRoom(String name, Player host) throws JSONRPC2Error;
	
	/**
	 * Most likely temporary until publish/subscribe is implemented on the server.
	 * @param name The unique room name.
	 * @return The Game Room.
	 * @throws JSONRPC2Error
	 */
	GameRoom refreshGameRoom(String name) throws JSONRPC2Error;
	
	/**
	 * Gets all available  
	 * @return
	 * @throws JSONRPC2Error
	 */
	Collection<GameRoom> getGameRooms() throws JSONRPC2Error;
	
	/**
	 * Assigns the player to the specified room defined by its unique name 
	 * @param room The unique room name
	 * @param player The player that wishes to join the game room.
	 * @throws JSONRPC2Error
	 */
	void joinGameRoom(String room, Player player) throws JSONRPC2Error;
	
	/**
	 * Tells the server to initialize the game for the specified game room
	 * and starts the game with the GameRoom host as the first player.
	 * @param room The unique room name for which the game should be initialized
	 * and started.
	 * @throws JSONRPC2Error
	 */
	void startGame(String room) throws JSONRPC2Error;
}
