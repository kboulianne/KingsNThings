/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase;

import com.main.KNTAppFactory;
import com.model.Game;
import com.presenter.Util;

/**
 *
 * @author kurtis
 */
class ChangePlayOrderPhase extends AbstractPhaseStrategy{

	ChangePlayOrderPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		// Nothing to be done here
		Util.log("Change Player Order.");
		
		// TESTING
		// TODO Make method in presenter instead? Would be cleaner.
		// .getDicePresenter().setEndTurnHandler(...)
//		KNTAppFactory.getDicePresenter().getView().getEndTurnBtn().setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent t) {
//				context.endTurn();
//			}
//		});;
	}

	@Override
	public void phaseEnd() {
		// Rotate the player order and display
		game.rotatePlayerOrder();
		KNTAppFactory.getGamePresenter().getView().setGame(game, getActionText());
		Util.log("Changed Player Order.");
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
	}

	@Override
	public void turnEnd(Game game) {
		// Nothing to be done here.
	}

	@Override
	public String getActionText() {
		return "Update Me!";
	}
}
