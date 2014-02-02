/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Hex;
import com.model.game.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;
import com.view.GameView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


/**
 *
 * @author kurtis
 */
//TODO needs a different strategy implementation one with simply executePhase()
public class PlayerOrderPhase extends AbstractPhaseStrategy {
	
	Game game;
	GameView gv;
	


	public PlayerOrderPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Init Phase: Start of Player Order Phase");
		context.clearRolls();
		
		game =  GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();
		
		//top label
		gv.getCurrentActionLbl().setText("Roll the Dice");

		
		//detail pane
		
		//on clicks
		//GameService.getInstance().endTurn();
		//game.nextPlayer();
		
		game.getBoard().setFaceDown(true);
		// Update the view
		KNTAppFactory.getBoardpresenter().getView().setBoard(game.getBoard());
		KNTAppFactory.getBoardpresenter().getView().setOnMouseClicked(null);
		
		Button rollBtn = KNTAppFactory.getDicepresenter().getView().getEndTurnBtn();
		rollBtn.setText("Roll");
		rollBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// Roll
				KNTAppFactory.getDicepresenter().roll();
				// End turn
				context.endTurn();
//				KNTAppFactory.getDicepresenter().endTurn();
				
			}
		});	
	}



	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		//top label
		super.turnStart();
		
		KNTAppFactory.getGamePresenter().getView().setGame(game);
		
		Util.log("Skipping Step for Itertion 1");
	}

	@Override
	public void turnEnd() {
		// DEACTIVATED =======================================
		//context.addPlayerRoll(game.diceTotal(), game.getCurrentPlayer());
		// ===================================================
		
		Util.log("Added roll total " + game.diceTotal()
				+ " for " + game.getCurrentPlayer().getName());
		
		// Notify UI
		KNTAppFactory.getGamePresenter().getView().setGame(game);
	}
	
	@Override
	public void phaseEnd() {
		// Updates the player order.
		// DEACTIVATED =======================================
//		game.setPlayerOrder(context.getPlayersHighToLow());
		// ===================================================
		
//		Util.log("Init Phase: End of Player Order Phase");
	}
	
	@Override
	public void addHandlers() {}

	@Override
	public void removeHandlers() {}

}
