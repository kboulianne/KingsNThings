/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.presenter.Util;

/**
 *
 * @author kurtis
 */
public class RandomEventPhase extends AbstractPhaseStrategy {

	public RandomEventPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Random Events Phase");

		gv.getCurrentActionLbl().setText("Random Events");
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Random Events Phase");
	}

	@Override
	public void turnStart() {

	}

	@Override
	public void turnEnd() {
				
	}
}
