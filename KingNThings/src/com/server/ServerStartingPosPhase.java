package com.server;

import com.model.Game;

public class ServerStartingPosPhase extends AbstractServerPhase{

	public ServerStartingPosPhase(ServerGamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnStart(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnEnd(Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void phaseEnd() {
		// Flip the tiles.
		context.room.getGame().getBoard().setFaceDown(false);
	}

}
