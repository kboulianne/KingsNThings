/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase;

import com.model.Game;
import com.model.Player;
import com.util.Util;

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
	public void phaseStart(Game game) {
		Util.log("Game Phase: Start of Recruiting Character Phase");
	}
	
	@Override
	public void phaseEnd(Game game) {
		Util.log("Game Phase: End of Recruiting Character Phase");
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
		
		Player currPlay = game.getCurrentPlayer();
		//set cup thing to do nothing
		getPlayerInfoPresenter().getView().setRackDoNothingHandler(currPlay);
		getSidePanePresenter().getView().showSpecialCharRecruitment(currPlay, game.getAllOwnedSpecialChar(), game.getCup());
	}

	@Override
	public void turnEnd(Game game) {
		Player currPlay = game.getCurrentPlayer();
		getPlayerInfoPresenter().getView().setPlayer(currPlay);
	}

	@Override
	public String getActionText() {
		return "Recruit Special Character";
	}
}
