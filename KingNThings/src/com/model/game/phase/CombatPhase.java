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
import com.model.Game;
import com.presenter.Util;

import static com.main.KNTAppFactory.*;
/**
 *
 * @author kurtis
 */
class CombatPhase extends AbstractPhaseStrategy {

	CombatPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Combat Phase");

		getGamePresenter().getView().getCurrentActionLbl().setText("Combat Phase");
		
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				context.endTurn();
			}
		});
		
		getBoardPresenter().getView().addBattleSelectionHandler();
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Combat Phase");
	}

	@Override
	public void turnStart() {
		super.turnStart();
		Util.log("Game Phase: Logic for " + game.getCurrentPlayer().getName());

		getSidePanePresenter().getView().showArbitraryView("Available battles displayed as red hexes", Game.CROWN_IMAGE);
		
		// highlight hexes with available battles
		getBoardPresenter().findAndHighlightConflicts();
		
	}
	
	@Override
	public void turnEnd() {
		//GameService.getInstance().endTurn(this);
		getBoardPresenter().clearConflictHighlights();
		
	}
}
