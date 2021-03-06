/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase;

import com.model.Game;
import com.util.Util;

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
	}

	@Override
	public void phaseEnd(Game game) {
		// Rotate the player order and display		
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
		return "Change of Player Order";
	}
}
