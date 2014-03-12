/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.main.KNTAppFactory;
import com.model.game.Game;
import com.presenter.Util;

/**
 *
 * @author kurtis
 */
class RandomEventPhase extends AbstractPhaseStrategy {

	RandomEventPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Random Events Phase");
		
		gv.getCurrentActionLbl().setText("Random Events");
		
		Button finishBtn = KNTAppFactory.getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(false);
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent arg0) {
				context.endTurn();
			}
		});
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Random Events Phase");
	}

	@Override
	public void turnStart() {
		super.turnStart();
		KNTAppFactory.getSidePanePresenter().getView().showArbitraryView("Random Events: TODO", Game.CROWN_IMAGE);
	}

	@Override
	public void turnEnd() {
				
	}
}
