/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.model.Player;
import com.presenter.Util;
import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
class RecruitCharPhase extends AbstractPhaseStrategy {
	
	RecruitCharPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Recruiting Character Phase");
		
		getGamePresenter().getView().getCurrentActionLbl().setText("Recruit Special Character");
	}
	
	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Recruiting Character Phase");
	}

	@Override
	public void turnStart() {
		super.turnStart();
		
		Player currPlay = game.getCurrentPlayer();
		//set cup thing to do nothing
		getPlayerInfoPresenter().getView().setRackDoNothingHandler(currPlay);
		getSidePanePresenter().getView().showSpecialCharRecruitment(currPlay.getName(), currPlay.getAllOwnedSpecialChar());
	}

	@Override
	public void turnEnd() {
		Player currPlay = game.getCurrentPlayer();
		getPlayerInfoPresenter().getView().setPlayer(currPlay);
	}
}
