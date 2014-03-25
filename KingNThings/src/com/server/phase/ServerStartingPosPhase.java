package com.server.phase;

import com.model.Game;

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
	}

}
