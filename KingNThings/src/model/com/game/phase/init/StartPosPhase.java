/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game.phase.init;

import com.services.game.GameService;
import model.com.Hex;
import model.com.game.Game;
import model.com.game.HexInput;
import model.com.game.phase.IPhaseStrategy;
import model.com.game.phase.Phase;

/**
 *
 * @author kurtis
 */
public class StartPosPhase implements IPhaseStrategy<Hex> /*implements HexInput*/ {

    private GameService service = GameService.getInstance();
    
    /**
     * Execute this phase's logic.
     * @param input The hex tile selected as the start position.
     */
    @Override
    public void execute(final Hex input) {
	Game game = service.getGame();
	
	// Set the starting position for the current player.  Just need to take ownership of the hex.	
	input.setOwner(game.getCurrentPlayer());
	
	// Next player
	game.nextPlayer();
    }

    
    
//    @Override
//    public void execute(final Hex hex) {
//	
//    }
    
}
