package com.server;

import java.util.ArrayList;
import java.util.List;

import com.model.Game;
import com.model.GameRoom;
import com.model.Player;

public class ServerGameRoom extends GameRoom {

	private transient Game game;
	
	public ServerGameRoom(String name, Player host) {
		super(name, host);
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
//		game.setOpponent1(players.get(0));
//		game.setOpponent2(players.get(1));
//		game.setOpponent3(players.get(2));
	}
}
