/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game.phase;

import com.services.game.GameService;

/**
 *
 * @author kurtis
 */
public class GoldCollectPhase extends AbstractPhaseStrategy<Object>{

    public GoldCollectPhase(GamePlay context) {
	super(context);
    }

    @Override
    public void phaseStart() {
	System.out.println("Game Phase: Start of Gold Collection Phase");
    }

    @Override
    public void preExecutePhase(Object input) {
    }

    @Override
    public void executePhase(Object input) {
	System.out.println("Game Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
    }

    @Override
    public void postExecutePhase(Object input) {
	
    }

    @Override
    public void phaseEnd() {
	System.out.println("Game Phase: End of Gold Collection Phase");
    }
    
}
