package com.server.phase;

import java.util.Iterator;

import com.model.Game;
import com.model.Player;

public class ServerStartingPosPhase extends AbstractServerPhase {

	public ServerStartingPosPhase(ServerGamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		// TODO Auto-generated method stub
		
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
		// Flip the tiles.
		context.room.getGame().getBoard().setFaceDown(false);
		
		if (context.skip) {
			// Default setup
			Game game = context.room.getGame();
			
			Iterator<Integer> it = game.getBoard().getStartPositions().iterator();
			for (Player p : game.getPlayerOrder()) {
				game.getBoard().getHexes().get(it.next()).setOwner(p);
			}
		}
	}

}
