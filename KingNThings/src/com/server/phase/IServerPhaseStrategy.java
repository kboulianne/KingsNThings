package com.server.phase;

import com.model.Game;

public interface IServerPhaseStrategy {
	
    void phaseStart();
    void turnStart();
    void turnEnd(); 
    void phaseEnd();
    /**
     * Called when testing so we can skip over the phase by providing default data.
     */
    void skipPhase();
    
    void decrementCycles();
    boolean mustCycle();
}
