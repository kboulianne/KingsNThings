/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;

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

		gv.getCurrentActionLbl().setText("Exchange Things");

	}
	
	@Override
	public void phaseEnd() {
		Util.log("Init Phase: End of Exchange Things Phase");
	}

	@Override
	public void turnStart() {
		super.turnStart();
		Util.log("Skipping Step for Itertion 1");
		context.endTurn();		
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
//		GameService.getInstance().endTurn(this);
		
	}
}
