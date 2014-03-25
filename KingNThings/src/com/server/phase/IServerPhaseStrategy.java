package com.server.phase;

import com.model.Game;

public interface IServerPhaseStrategy {
	
    void phaseStart();
    void turnStart();
    void turnEnd(); 
    void phaseEnd();
    
    void decrementCycles();
    boolean mustCycle();
}
