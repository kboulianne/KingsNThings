package com.server.phase;

class ServerChangePlayerOrder extends AbstractServerPhase{

	ServerChangePlayerOrder(ServerGamePlay context) {
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
		context.room.getGame().rotatePlayerOrder();
	}

	@Override
	public void skipPhase() {
		phaseEnd();
	}
}
