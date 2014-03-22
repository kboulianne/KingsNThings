package com.server;

import java.util.ArrayList;
import java.util.List;

import com.model.Game;
import com.model.GameRoom;
import com.model.Player;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;

import static com.server.KNTServer.*;

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
	}
	
	public void notifyOthers(String playerName, JSONRPC2Notification not) {
		// Exclude notifying playerName
		if (!host.getName().equals(playerName)) {
			synchronized (this) {
				PLAYERS.get(host.getName()).notifyClient(not);
			}
		}
		
		for (Player p : players) {
			if (!p.getName().equals(playerName)) {
				synchronized (this) {
					PLAYERS.get(p.getName()).notifyClient(not);
				}
			}
		}
	}
}
