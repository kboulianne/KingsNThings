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
import com.model.Player;
import com.model.Player.PlayerId;
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
		finishBtn.setDisable(false);
		finishBtn.setVisible(true);
		
		KNTAppFactory.getBoardPresenter().getView().addPlacementHandler();
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Logic: End of Recruiting Things Phase");
		
		KNTAppFactory.getBoardPresenter().getView().addDefaultHandler();
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(true);
		finishBtn.setOnAction(null);
	}

	@Override
	public void turnStart() {
		super.turnStart();
		
		if(game.getCurrentPlayer().getId().equals(PlayerId.ONE))	{
			Util.log("Adding three creatures to Players 1 block for Iteration 1");
			
			Player player = game.getCurrentPlayer();
			
			game.moveThingFromCupToPlayer("cyclops", player);
			game.moveThingFromCupToPlayer("mountainmen", player);
			game.moveThingFromCupToPlayer("goblins", player);
			
			KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(player);
		} else {
			Util.log("Skipping Step for " + game.getCurrentPlayer().getName() + " for Iteration 1");
			context.endTurn();
		}
	}

	@Override
	public void turnEnd() {
		game.getCurrentPlayer().trimBlock();
	}

}
