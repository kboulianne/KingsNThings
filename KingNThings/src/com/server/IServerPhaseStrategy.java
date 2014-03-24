package com.server;

public interface IServerPhaseStrategy {
	void prePhaseContitions();
	void preTurnConditions();
	void postTurnConditions();
	void postPhaseConditions();
}
