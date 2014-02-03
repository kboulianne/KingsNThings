/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.main.KNTAppFactory;
import com.presenter.Util;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author kurtis
 */
public class ChangePlayOrderPhase extends AbstractPhaseStrategy{

	public ChangePlayOrderPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		// Nothing to be done here
		Util.log("Change Player Order.");
		
		// TESTING
		// TODO Make method in presenter instead? Would be cleaner.
		// .getDicePresenter().setEndTurnHandler(...)
		KNTAppFactory.getDicepresenter().getView().getEndTurnBtn().
				setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				context.endTurn();
			}
		});;
	}

	@Override
	public void phaseEnd() {
		// Rotate the player order and display
		game.rotatePlayerOrder();
		KNTAppFactory.getGamePresenter().getView().setGame(game);
		Util.log("Changed Player Order.");
	}

	@Override
	public void turnStart() {
		super.turnStart();
	}

	@Override
	public void turnEnd() {
		// Nothing to be done here.
	}

	@Override
	public void addHandlers() { }

	@Override
	public void removeHandlers() { }
}
