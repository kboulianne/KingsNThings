package com.server.services;

import com.model.Game;
import com.model.Player;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public interface IGameService {
	Game getGame(String roomName) throws JSONRPC2Error;
	void updateGame(String roomName, Game game) throws JSONRPC2Error;
	boolean isPlayerTurn(String roomName, Player p) throws JSONRPC2Error;
	void endTurn(String gameRoom, Player p) throws JSONRPC2Error;
	void skipPhase(String roomName) throws JSONRPC2Error;
}
