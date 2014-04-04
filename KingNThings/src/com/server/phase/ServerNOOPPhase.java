package com.server.phase;

import com.model.Player;


public class ServerNOOPPhase extends AbstractServerPhase {

	public ServerNOOPPhase(ServerGamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnStart() {

	}

	@Override
	public void turnEnd() {
//		for (Player p : context.room.getGame().getPlayerOrder()) {
//			System.out.println(p.getName() + " now has " + p.getGold());
//		}
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
