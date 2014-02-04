/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.game.Game;
import com.presenter.Util;
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public class RecruitCharPhase extends AbstractPhaseStrategy {
	
	public RecruitCharPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Recruiting Character Phase");
		
		gv.getCurrentActionLbl().setText("Recruit Special Character");
	}
	
	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Recruiting Character Phase");
	}

	@Override
	public void turnStart() {
		super.turnStart();
		
		Util.log("Skipping Step for Iteration 1");
		context.endTurn();	
	}

	@Override
	public void turnEnd() {
		
	}
}
