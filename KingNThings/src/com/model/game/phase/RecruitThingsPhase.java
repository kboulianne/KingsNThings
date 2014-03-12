/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.main.KNTAppFactory;
import com.model.Hex;
import com.model.Player;
import com.presenter.Util;

/**
 *
 * @author kurtis
 */
class RecruitThingsPhase extends AbstractPhaseStrategy {

	RecruitThingsPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Recruiting Things Phase");
		
		gv.getCurrentActionLbl().setText("Recruit Things");
		
		Button finishBtn = KNTAppFactory.getDicePresenter().getView().getEndTurnBtn();
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
		
		KNTAppFactory.getBoardPresenter().getView().addDefaultHandler();
		Button finishBtn = KNTAppFactory.getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(true);
		finishBtn.setOnAction(null);
	}

	@Override
	public void turnStart() {
		super.turnStart();
		
		int freeRecruits = 0;
	
		KNTAppFactory.getDicePresenter().getView().getEndTurnBtn().setDisable(true);
		Player player = game.getCurrentPlayer();
		
		for (Hex h : game.getBoard().getHexes()) {
			if ((h != null) && (h.getHexOwner() == player)) {
				freeRecruits++;
			}
		}
		
		freeRecruits = (int) Math.ceil(freeRecruits/2.0);
		
		KNTAppFactory.getSidePanePresenter().getView().showThingRecruitment(freeRecruits, 0);
		KNTAppFactory.getBoardPresenter().getView().setDisable(true);
		KNTAppFactory.getPlayerInfoPresenter().getView().setRackRecruitingThingsHandler(game.getCurrentPlayer());	
	}

	@Override
	public void turnEnd() {
		game.getCurrentPlayer().trimBlock();
		KNTAppFactory.getBoardPresenter().getView().addDefaultHandler();
	}

}
