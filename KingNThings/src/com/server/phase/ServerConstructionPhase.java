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
//		// Testing
//		Hex hex = context.room.getGame().getBoard().getHexes().get(19);
//		
//		// For testing
//		if (hex.getFort() == null) {
//			hex.setFort(Fort.create());
//			hex.getFort().upgrade();
//			hex.getFort().upgrade();
//		}
//		if (hex.getFort() != null && (!hex.getFort().getFortType().equals(FortType.CASTLE)
//				&& !hex.getFort().getFortType().equals(FortType.CITADEL))) {
//			hex.setFort(Fort.create());
//			hex.getFort().upgrade();
//		}
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
			if(citOwner.getTimeCitOwned() == 1) {
				context.room.getGame().setWinner(citOwner);
				// Notify clients that the game was won
				KNTServer.notifyAllClients(context.room, Notifications.GAME_ENDED);
				
				// TODO: Remove the game room.
			}
		}
	}

	@Override
	public void skipPhase() {
		// TODO Auto-generated method stub
		
	}

}
