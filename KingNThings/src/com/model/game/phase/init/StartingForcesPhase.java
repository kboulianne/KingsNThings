/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.model.game.phase.init;

import com.game.services.GameService;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;

/**
 *
 * @author kurtis
 */
public class StartingForcesPhase extends AbstractPhaseStrategy<Object>{

    public StartingForcesPhase(GamePlay context) {
	super(context);
    }
    
    @Override
    public void phaseStart() {
	System.out.println("Init Phase: Start of Starting Forces Phase");
    }

    @Override
    public void preExecutePhase(Object input) {
	
    }

    @Override
    public void executePhase(Object input) {
	System.out.println("Init Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
    }

    @Override
    public void postExecutePhase(Object input) {
	
    }

    @Override
    public void phaseEnd() {
	System.out.println("Init Phase: End of Starting Forces Phase");
    }

    
}
