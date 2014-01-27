/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import com.game.services.GameService;
import com.model.game.Game;
import com.model.game.GameAction;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kurtis
 */
//TODO needs a different strategy implementation one with simply executePhase()
public class PlayerOrderPhase extends AbstractPhaseStrategy<Object> {

	public PlayerOrderPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		System.out.println("Init Phase: Start of Player Order Phase");
		context.clearRolls();
	}

	@Override
	public void preExecutePhase(Object input) {
		
	}

	@Override
	public void executePhase(Object input) {
		Game game = GameService.getInstance().getGame();

		// Request action from the user
		// Suspend this execution until notified by ui.
		
		synchronized (context.actions) {
			
			try {

				context.actions.offer(GameAction.ROLL);
				System.out.println("LOCKED: PlayerOrderPhase");
				context.actions.notifyAll();
				// Wait here until action is triggered.
				context.actions.wait();
				System.out.println("UNLOCKED: PlayerOrderPhase");
			} catch (InterruptedException ex) {
				Logger.getLogger(PlayerOrderPhase.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		

		// Add the rolled dice total to the context for later use.
		context.addPlayerRoll(game.diceTotal(), game.getCurrentPlayer());
//	//TODO handle case where dice totals are equal.
//	phase.getRolls().put(game.diceTotal(), game.getCurrentPlayer());
//	
		System.out.println("Added roll total " + game.diceTotal()
				+ " for " + game.getCurrentPlayer().getName());
//	
//	
		// Move to postExecute in AbstractPhaseImpl?
//	game.nextPlayer();
	}

	// TODO phaseEnd()?
	// TODO postExecute()?
//    public List<Player> getPlayerOrder() {
//	return new ArrayList<>(rolls.values());
//    }
	@Override
	public void postExecutePhase(Object input) {

	}

	@Override
	public void phaseEnd() {
		// Updates the player order.
		Game game = GameService.getInstance().getGame();
		game.setPlayerOrder(context.getPlayersHighToLow());

		System.out.println("Init Phase: End of Player Order Phase");
	}

}
