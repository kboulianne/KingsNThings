package com.server;

import java.util.ArrayList;
import java.util.List;

import com.game.phase.GamePlay;
import com.model.Battle;
import com.model.Game;
import com.model.GameRoom;
import com.model.Player;
import com.server.phase.ServerGamePlay;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;

import static com.server.KNTServer.*;

public class ServerGameRoom extends GameRoom {

	private transient Game game;
	/** The battle being fought by two or more players. */
	private transient Battle battle;
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
	
	public final Battle getBattle() {
		return battle;
	}
	
	public final void setBattle(Battle battle) {
		this.battle = battle;
	}
	
	public final ServerGamePlay getGamePlay() {
		return gamePlay;
	}
}
