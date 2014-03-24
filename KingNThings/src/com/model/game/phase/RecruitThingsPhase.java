/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.model.Game;
import com.model.Hex;
import com.model.Player;
import com.presenter.Util;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
class RecruitThingsPhase extends AbstractPhaseStrategy {

	RecruitThingsPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		Util.log("Game Phase: Start of Recruiting Things Phase");
		
		getGamePresenter().getView().getCurrentActionLbl().setText("Recruit Things");
		
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				context.endTurn();
			}
		});
		finishBtn.setVisible(true);
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Logic: End of Recruiting Things Phase");
		
		getBoardPresenter().getView().addDefaultHandler();
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(true);
		finishBtn.setOnAction(null);
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
		
		int freeRecruits = 0;
	
		getDicePresenter().getView().getEndTurnBtn().setDisable(true);
		Player player = game.getCurrentPlayer();
		
		for (Hex h : game.getBoard().getHexes()) {
			if ((h != null) && (h.getHexOwner() == player)) {
				freeRecruits++;
			}
		}
		
		freeRecruits = (int) Math.ceil(freeRecruits/2.0);
		
		getSidePanePresenter().getView().showThingRecruitment(freeRecruits, 0);
		getBoardPresenter().getView().setDisable(true);
		getPlayerInfoPresenter().getView().setRackRecruitingThingsHandler(game.getCurrentPlayer());	
	}

	@Override
	public void turnEnd(Game game) {
		game.getCurrentPlayer().trimBlock();
		getBoardPresenter().getView().addDefaultHandler();
	}

	@Override
	public String getActionText() {
		return "Update Me!";
	}

}
