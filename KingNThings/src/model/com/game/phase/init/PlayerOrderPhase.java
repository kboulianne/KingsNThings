/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game.phase.init;

import com.services.game.GameService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import model.com.Player;
import model.com.game.Game;
import model.com.game.phase.IPhaseStrategy;

/**
 *
 * @author kurtis
 */
//TODO needs a different strategy implementation one with simply execute()
public class PlayerOrderPhase implements IPhaseStrategy<Object> {

    // Maps roll value the player, getting valueSet should contain play order
    private final SortedMap<Integer, Player> rolls;
 
    public PlayerOrderPhase() {
	rolls = new TreeMap<>();
    }
    
    // TODO phaseStart?
    //TODO preExecute()?
    // clear rolls here
    
    @Override
    public void execute(Object input) {
	Game game = GameService.getInstance().getGame();
	
	// Roll
	game.rollDice();
	
	//TODO handle case where dice totals are equal.
	rolls.put(game.diceTotal(), game.getCurrentPlayer());
	
	System.out.println("Added roll total " + game.diceTotal()
	    + " for " + game.getCurrentPlayer().getName());
	
	
	// Move to postExecute in AbstractPhaseImpl?
	game.nextPlayer();
    }
    
    // TODO phaseEnd()?
    // TODO postExecute()?
    
    public List<Player> getPlayerOrder() {
	return new ArrayList<>(rolls.values());
    }
}
