/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import com.model.Game;
import com.model.Player;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;

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
	public void phaseStart() {
		Util.log("Init Phase: Start of Starting Kingdoms Phase");
		
		getGamePresenter().getView().getCurrentActionLbl().setText("Choose Starting Kingdom");

		getDicePresenter().getView().getEndTurnBtn().setVisible(false);
		getBoardPresenter().getView().addStartKingdomsHandler();
		
		// This phase cycles, so set context to execute turns 2 times (normal cycle + secondary cycle)
		context.setCycleCount(1);
	}

	@Override
	public void turnStart() {
		super.turnStart();
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
	public void turnEnd() {
		
	}
	
	@Override
	public void phaseEnd() {
		// Make sure to remove cycles, as a precaution
		context.setCycleCount(0);
		
		getBoardPresenter().getView().addDefaultHandler();
	}
}
