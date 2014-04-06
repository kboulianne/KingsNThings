package com.server.phase;

import java.util.List;

import com.model.Player;
import com.server.KNTServer;
import com.server.Notifications;

public class ServerConstructionPhase extends AbstractServerPhase {

	public ServerConstructionPhase(ServerGamePlay context) {
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
		int total = 0;
		Player citOwner = null;
		List<Player> players = context.room.getGame().getPlayerOrder();
		
		for(int i=0; i<players.size(); i++)	{
			if(players.get(i).isCitadelOwner())	{
				total++;
				citOwner = players.get(i);
			}
		}
		
		if(total == 1)	{
			citOwner.addTimeCitOwned();
			if(citOwner.getTimeCitOwned() == 1)
				// Notify clients that the game was won
				KNTServer.notifyAllClients(context.room, Notifications.GAME_ENDED);
		}
	}

	@Override
	public void skipPhase() {
		// TODO Auto-generated method stub
		
	}

}
