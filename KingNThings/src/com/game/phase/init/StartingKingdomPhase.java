/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase.init;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.game.phase.AbstractPhaseStrategy;
import com.game.phase.GamePlay;
import com.model.Game;
import com.model.Player;
import com.util.Util;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
public class StartingKingdomPhase extends AbstractPhaseStrategy {

	public StartingKingdomPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		Util.log("Init Phase: Start of Starting Kingdoms Phase");
		
//		getGamePresenter().getView().getCurrentActionLbl().setText("Choose Starting Kingdom");

		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		getBoardPresenter().getView().addStartKingdomsHandler();
		
		finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(false);
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				getGamePresenter().endTurn();
			}
		});
		
		// This phase cycles, so set context to execute turns 2 times (normal cycle + secondary cycle)
		// Cycles are now on server.
//		context.setCycleCount(1);
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
		getSidePanePresenter().getView().showArbitraryView("Choose an adjacent owned hex for your kingdom", Game.CROWN_IMAGE);
		if(Util.AUTOMATE){
			Util.log("Automated");
			if(game.getCurrentPlayer().getId().equals(Player.PlayerId.ONE)){
				getBoardPresenter().handleStartingKingdomsHexClick(9);
				getBoardPresenter().handleStartingKingdomsHexClick(10);
			}
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.TWO)){
				getBoardPresenter().handleStartingKingdomsHexClick(13);
				getBoardPresenter().handleStartingKingdomsHexClick(12);
			}
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.THREE)){
				getBoardPresenter().handleStartingKingdomsHexClick(15);
				getBoardPresenter().handleStartingKingdomsHexClick(16);
			}
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.FOUR)){
				getBoardPresenter().handleStartingKingdomsHexClick(7);
				getBoardPresenter().handleStartingKingdomsHexClick(18);
			}
		}
	
	}

	@Override
	public void turnEnd(Game game) {
		
	}
	
	@Override
	public void phaseEnd(Game game) {
		// Make sure to remove cycles, as a precaution
//		context.setCycleCount(0);
		
		getBoardPresenter().getView().addDefaultHandler();
	}

	@Override
	public String getActionText() {
		return "Choose Starting Kingdom";
	}
}
