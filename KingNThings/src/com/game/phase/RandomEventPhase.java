/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.main.KNTAppFactory;
import com.model.Game;
import com.presenter.Util;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
class RandomEventPhase extends AbstractPhaseStrategy {

	RandomEventPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		Util.log("Game Phase: Start of Random Events Phase");
		
		getGamePresenter().getView().getCurrentActionLbl().setText("Random Events");
		
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(false);
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent arg0) {

			}
		});
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Random Events Phase");
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
		getSidePanePresenter().getView().showArbitraryView("Random Events: TODO", Game.CROWN_IMAGE);
	}

	@Override
	public void turnEnd(Game game) {
				
	}

	@Override
	public String getActionText() {
		return "Update Me!";
	}
}
