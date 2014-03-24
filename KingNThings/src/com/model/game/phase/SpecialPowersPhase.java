/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.main.KNTAppFactory;
import com.model.Game;
import com.presenter.Util;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
class SpecialPowersPhase extends AbstractPhaseStrategy {

	SpecialPowersPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		Util.log("Game Phase: Start of Special Powers Phase");
		
		getGamePresenter().getView().getCurrentActionLbl().setText("Deploy Special Powers");
		
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Special Powers Phase");
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
		getSidePanePresenter().getView().showArbitraryView("Special Powers TODO", Game.CROWN_IMAGE);
	}

	@Override
	public void turnEnd(Game game) {

	}

	@Override
	public String getActionText() {
		return "Update Me!";
	}
}
