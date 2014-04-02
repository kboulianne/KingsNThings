package com.server.phase;

import java.util.Iterator;

import com.model.Game;
import com.model.Player;

public class ServerStartingKingdomPhase extends AbstractServerPhase {

	
	public ServerStartingKingdomPhase(ServerGamePlay context) {
		// Repeat phase once.
		super(context, 1);
	}

	@Override
	public void phaseStart() {

	}

	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void phaseEnd() {

	}

	@Override
	public void skipPhase() {
		// Claim tiles to left and right of start position
		Game game = context.room.getGame();
		
		Iterator<Integer> it = game.getBoard().getStartPositions().iterator();
		for (Player p : game.getPlayerOrder()) {
			int i = it.next();
			// Set index +- 1 from start pos as owned by p
			game.getBoard().getHexes().get(i - 1).setOwner(p);
			game.getBoard().getHexes().get(i + 1).setOwner(p);
		}
	}

}
