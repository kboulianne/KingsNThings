/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.model.game.phase;

import com.game.services.GameService;
import com.model.game.Game;
import com.model.Player;
import com.model.Hex;

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
    	int hexGold = 0;
    	int fortGold = 0;
    	int counterGold = 0;
    	int specCharGold = 0;
    	Game game = GameService.getInstance().getGame();
    	Player player = game.getCurrentPlayer();
    	System.out.println("Game Phase: Logic for " + player.getName());
    	
    	for(Hex h: game.getBoard().getHexes())	{
    		if(h.getOwner().equals(player))	{
    			hexGold++;
    		}
    	}
    	
    	
    }

    @Override
    public void postExecutePhase(Object input) {
	
    }

    @Override
    public void phaseEnd() {
	System.out.println("Game Phase: End of Gold Collection Phase");
    }
    
}
