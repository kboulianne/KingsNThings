/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.game.Game;
import com.presenter.Util;
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public class ConstructionPhase extends AbstractPhaseStrategy {

	public ConstructionPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Construction Phase");

		gv.getCurrentActionLbl().setText("Construction Phase");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				context.endTurn();
			}
		});		
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Construction Phase");
	}

	@Override
	public void turnStart() {
		super.turnStart();
		Util.log("Skipping Step for Iteration 1");
		context.endTurn();
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
		//GameService.getInstance().endTurn(this);
	}
}
