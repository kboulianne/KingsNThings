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
import com.model.GamePiece;
import com.model.Hex;
import com.model.IncomeCounter;
import com.model.Player;
import com.model.SpecialCharacter;
import com.model.game.Game;
import com.presenter.Util;

/**
 *
 * @author kurtis
 */
public class RecruitThingsPhase extends AbstractPhaseStrategy {

	public RecruitThingsPhase(GamePlay context) {
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
		finishBtn.setDisable(true);
		finishBtn.setVisible(true);
		
		//KNTAppFactory.getBoardPresenter().getView().addPlacementHandler();
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
	
		Player player = game.getCurrentPlayer();
		
		for (Hex h : game.getBoard().getHexes()) {
			if ((h != null) && (h.getHexOwner() == player)) {
				freeRecruits++;
			}
		}
		
		freeRecruits = (int) Math.ceil(freeRecruits/2.0);
		
		KNTAppFactory.getSidePanePresenter().getView().showThingRecruitment(freeRecruits);	
	}

	@Override
	public void turnEnd() {
		game.getCurrentPlayer().trimBlock();
	}

}
