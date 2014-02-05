/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.game.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.model.game.phase.GoldCollectPhase;
import com.presenter.Util;
import com.view.GameView;

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
