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
import com.util.Util;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
class MovementPhase extends AbstractPhaseStrategy {

	MovementPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		getBoardPresenter().getView().setDisable(false);
		
		Util.log("Game Phase: Start of Movement Phase");
		
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(false);
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent arg0) {
				KNTAppFactory.getGamePresenter().endTurn();
			}
		});
		
		getBoardPresenter().getView().addMovementHandler();
	}

	@Override
	public void phaseEnd(Game game) {
		//getArmyDetailsPresenter().getView().setDefaultHandler();
		getBoardPresenter().getView().addDefaultHandler();
		Util.log("Game Phase: End of Movement Phase");
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
		//getArmyDetailsPresenter().getView().setMovementHandler();	
		getHexDetailsPresenter().getView().getCurrentPlayerArmy().setMoving(true);
		getHexDetailsPresenter().getView().getCounter().setMoving(true);		
		getSidePanePresenter().getView().showArbitraryView("Move characters within hexes", Game.CROWN_IMAGE);
	}

	@Override
	public void turnEnd(Game game) {
		super.turnEnd(game);
		getHexDetailsPresenter().getView().getCurrentPlayerArmy().setMoving(false);
		getHexDetailsPresenter().getView().getCounter().setMoving(false);
	}

	@Override
	public String getActionText() {
		return "Movement Phase";
	}
}
