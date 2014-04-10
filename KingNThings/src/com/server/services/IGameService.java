package com.server.services;

import com.google.gson.JsonObject;
import com.model.Cup;
import com.model.Game;
import com.model.Player;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public interface IGameService {
	Game getGame(String roomName) throws JSONRPC2Error;
	void updateGame(String roomName, Game game) throws JSONRPC2Error;
	/**
	 * Issues would arise if we serialized the cup with the game. This call
	 * updates it separately until the issue can be resolved.
	 * @param roomName The GameRoom name for which the cup should be updated.
	 * @param cup
	 * @throws JSONRPC2Error
	 */
	void updateCup(String roomName, Cup cup) throws JSONRPC2Error;
	boolean isPlayerTurn(String roomName, Player p) throws JSONRPC2Error;
	void endTurn(String gameRoom, Player p) throws JSONRPC2Error;
	void skipPhase(String roomName) throws JSONRPC2Error;
	void loadBoard(String roomName, JsonObject obj) throws JSONRPC2Error;
}
