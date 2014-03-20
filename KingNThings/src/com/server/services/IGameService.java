package com.server.services;

import com.model.Player;
import com.model.game.Game;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public interface IGameService {
	Game refreshGame(String roomName) throws JSONRPC2Error;
}
