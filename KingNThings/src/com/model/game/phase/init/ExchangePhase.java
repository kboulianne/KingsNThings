/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import com.model.Block;
import com.model.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
public class ExchangePhase extends AbstractPhaseStrategy {

	public ExchangePhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Init Phase: Start of Exchange Things Phase");
		getGamePresenter().getView().getCurrentActionLbl().setText("Exchange Things");
		getBoardPresenter().getView().addPlacementHandler();
	}
	
	@Override
	public void phaseEnd() {
		Util.log("Init Phase: End of Exchange Things Phase");
	}

	@Override
	public void turnStart() {
		getSidePanePresenter().getView().showArbitraryView("        Exchange things by clicking the rack\n"
																	   + "   1 new recruit is given for every 1 returned", Game.CROWN_IMAGE);
		Block currentPlayerBlock = game.getCurrentPlayer().getBlock();
		if(currentPlayerBlock.getListOfThings().isEmpty()){
			context.endTurn();	
		} else {
			super.turnStart();
			getPlayerInfoPresenter().getView().setRackExchangeThingsHandler(game.getCurrentPlayer());		
		}
	}

}
