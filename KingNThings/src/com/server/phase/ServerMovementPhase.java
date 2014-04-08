package com.server.phase;

import com.model.Cup;
import com.model.Hex;
import com.model.Player;
import com.model.Thing;
import com.model.Creature;

class ServerMovementPhase extends AbstractServerPhase {

	ServerMovementPhase(ServerGamePlay context) {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skipPhase() {
		// Logic for setting up a battle between two players.
		
		// Claim hex 0 for player 1
		Player p1 = context.room.getGame().getPlayerOrder().get(0);
		Hex conflictHex = context.room.getGame().getBoard().getHexes().get(0);
		conflictHex.setOwner(p1);
		
		// Draw 10 troops from cup
		Cup cup = context.room.getGame().getCup();
		
		int i = 0;
		while (i < 10)	{
			Thing t = cup.getRandomThing();
			if (t instanceof Creature) {
				cup.removeThing(t);
				
				conflictHex.addCreatToArmy((Creature)t, p1);
				i ++;
			}
		}
		
		// Setup player 2
		Player p2 = context.room.getGame().getPlayerOrder().get(1);
		i = 0;
		while (i < 10)	{
			Thing t = cup.getRandomThing();
			if (t instanceof Creature) {
				cup.removeThing(t);
				
				conflictHex.addCreatToArmy((Creature)t, p2);
				i ++;
			}
		}
		
		// Setup player 2
		Player p3 = context.room.getGame().getPlayerOrder().get(2);
		i = 0;
		while (i < 10)	{
			Thing t = cup.getRandomThing();
			if (t instanceof Creature) {
				cup.removeThing(t);
				
				conflictHex.addCreatToArmy((Creature)t, p3);
				i ++;
			}
		}
	}

}
