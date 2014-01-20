/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game.phase.init;

import com.services.game.GameService;
import model.com.Hex;
import model.com.game.phase.AbstractPhaseStrategy;
import model.com.game.phase.GamePlay;

/**
 *
 * @author kurtis
 */
public class StartingPosPhase extends AbstractPhaseStrategy<Hex> /*implements HexInput*/ {
    
    public StartingPosPhase(GamePlay context) {
	super(context);
    }
    /**
     * Execute this phase's logic.
     * @param input The hex tile selected as the start position.
     */
    @Override
    public void executePhase(final Hex input) {
//	Game game = service.getGame();
//	
//	// Set the starting position for the current player.  Just need to take ownership of the hex.	
//	input.setOwner(game.getCurrentPlayer());
//	
//	// Next player
//	game.nextPlayer();
	
	System.out.println("Init Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
    }

    
    
//    @Override
//    public void executePhase(final Hex hex) {
//	
//    }

    @Override
    public void preExecutePhase(Hex input) {
	
    }

    @Override
    public void postExecutePhase(Hex input) {

    }

    @Override
    public void phaseStart() {
	System.out.println("Start of Starting Positions Phase");
    }

    @Override
    public void phaseEnd() {
	System.out.println("End of Starting Positions Phase");
    }
    
}
