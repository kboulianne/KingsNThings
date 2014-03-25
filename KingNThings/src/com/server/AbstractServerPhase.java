package com.server;


public abstract class AbstractServerPhase implements IServerPhaseStrategy {

	protected ServerGamePlay context;
	private int cycleCount = 0;
	
	public AbstractServerPhase(ServerGamePlay context) {
		this.context = context;
	}
	
	public AbstractServerPhase(ServerGamePlay context, int cycleCount) {
		this.context = context;
		
		this.cycleCount = cycleCount;
	}
	
	@Override
	public final void decrementCycles() {
		cycleCount --;
	}
	
	@Override
	public final boolean mustCycle() {
		return cycleCount > 0;
	}
}
