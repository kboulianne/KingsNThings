package com.server.phase;

import java.util.SortedMap;
import java.util.TreeMap;

import com.game.phase.IPhaseStrategy;
import com.model.Game;
import com.model.Player;
import com.presenter.Util;

public class ServerPlayerOrderPhase extends AbstractServerPhase {

	private SortedMap<Integer, Player> rolls;
	
	public ServerPlayerOrderPhase(ServerGamePlay context) {
		super(context);
		rolls = new TreeMap<>(new Util.ReverseIntegerSortComparator());
	}

	//TODO: since context stores GameRoom, we do not need to pass Game to method calls.
	// Create Server-side strategy interface.
	@Override
	public void phaseStart() {
		rolls.clear();
	}

	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnEnd() {
		Game game = context.room.getGame();
		// When a player ends their turn, extract roll values and store them for the player
		rolls.put(game.diceTotal(), game.getCurrentPlayer());
	}

	@Override
	public void phaseEnd() {
		// End of PlayerOrderPhase,
		//TODO:
		Game game = context.room.getGame();
		
		// Extract new player order.
		System.out.println("New Player Order: " + rolls.values());
		game.setPlayerOrder(rolls.values());
	}

}
