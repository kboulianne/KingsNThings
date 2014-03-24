package com.server;

import java.util.ArrayList;
import java.util.List;

import com.game.phase.GamePlay;
import com.model.Game;
import com.model.GameRoom;
import com.model.Player;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;

import static com.server.KNTServer.*;

public class ServerGameRoom extends GameRoom {

	private transient Game game;
	/** Server-side state representing the current phase. */
	private transient ServerGamePlay gamePlay;
	
	public ServerGameRoom(String name, Player host) {
		super(name, host);
		gamePlay = new ServerGamePlay(this);
	}

	public final Game getGame() {
		return game;
	}
	
	public final void setGame(final Game game) {
		
		// Initialization
		if (this.game == null) {
			// Update Player references in game.
			game.setCurrentPlayer(host);
			List<Player> order = new ArrayList<>();
			order.add(host);
			order.addAll(players);
			game.setPlayerOrder(order);
		}
		
		this.game = game;
	}
	
	
	public final ServerGamePlay getGamePlay() {
		return gamePlay;
	}
}
