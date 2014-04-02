/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase.init;

import com.game.phase.AbstractPhaseStrategy;
import com.game.phase.GamePlay;
import com.model.Block;
import com.model.Game;
import com.util.Util;

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
	public void phaseStart(Game game) {
		Util.log("Init Phase: Start of Exchange Things Phase");
//		getGamePresenter().getView().getCurrentActionLbl().setText("Exchange Things");
		getBoardPresenter().getView().addPlacementHandler();
	}
	
	@Override
	public void phaseEnd() {
		Util.log("Init Phase: End of Exchange Things Phase");
	}

	@Override
	public void turnStart(Game game) {
		getSidePanePresenter().getView().showArbitraryView("        Exchange things by clicking the rack\n"
																	   + "   1 new recruit is given for every 1 returned", Game.CROWN_IMAGE);
		Block currentPlayerBlock = game.getCurrentPlayer().getBlock();
		if(currentPlayerBlock.getListOfThings().isEmpty()){
			
		} else {
			super.turnStart(game);
			getPlayerInfoPresenter().getView().setRackExchangeThingsHandler(game.getCurrentPlayer());		
		}
	}

	@Override
	public String getActionText() {
		return "Exchange Things";
	}

}
