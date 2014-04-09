package com.server.phase;

import com.model.Cup;
import com.model.Game;
import com.model.Player;
import com.model.Thing;

public class ServerStartingForcesPhase extends AbstractServerPhase {
	
	public ServerStartingForcesPhase(ServerGamePlay serverGamePlay) {
		super(serverGamePlay);
	}

	@Override
	public void phaseStart() {
	}

	@Override
	public void turnStart() {
		Game game = context.room.getGame();
		Player player = game.getCurrentPlayer();
		player.addGold(10);
		Cup cup = game.getCup();
		
		for(int i = 0; i < 10; i++)	{
			Thing t = cup.getRandomThing();
			cup.removeThing(t);
			player.addThing(t);
		}
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
	}

	@Override
	public void phaseEnd() {
		// TODO Auto-generated method stub
	}

	@Override
	public void skipPhase() {
		// TODO Auto-generated method stub
	}
}
