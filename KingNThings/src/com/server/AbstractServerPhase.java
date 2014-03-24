package com.server;

import com.game.phase.IPhaseStrategy;

public abstract class AbstractServerPhase implements IPhaseStrategy {

	protected ServerGamePlay context;
	
	public AbstractServerPhase(ServerGamePlay context) {
		this.context = context;
	}
}
