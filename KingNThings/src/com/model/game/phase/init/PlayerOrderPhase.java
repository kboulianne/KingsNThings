/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.game.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;
import com.view.GameView;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


/**
 *
 * @author kurtis
 */
//TODO needs a different strategy implementation one with simply executePhase()
public class PlayerOrderPhase extends AbstractPhaseStrategy<Object> {
	
	Game game;
	GameView gv;
	


	public PlayerOrderPhase(GamePlay contxt) {
		super(contxt);
	}

	@Override
	public void phaseStart() {
		System.out.println("Init Phase: Start of Player Order Phase");
		context.clearRolls();
		

		game =  GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();
		
		//top label
		gv.getCurrentActionLbl().setText("Roll the Dice");
		
		//detail pane
		
		//on clicks
		//GameService.getInstance().endTurn();
		//game.nextPlayer();
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getFinishTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				turnEnd();
			}
		});		
	}

	/*@Override
	public void executePhase(Object input) {
		Game game = GameService.getInstance().getGame();
		

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
 */

	@Override
	public void phaseEnd() {
		// Updates the player order.
		
		//game.setPlayerOrder(context.getPlayersHighToLow());
		
		Util.log("Init Phase: End of Player Order Phase");
		new StartingPosPhase(context).phaseStart();
	}

	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		//top label
		
		super.turnStart();
		
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
		GameService.getInstance().endTurn(this);
	}

	@Override
	public void addHandlers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeHandlers() {
		// TODO Auto-generated method stub
		
	}

}
