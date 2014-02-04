/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import com.main.KNTAppFactory;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;

/**
 *
 * @author kurtis
 */
public class StartingKingdomPhase extends AbstractPhaseStrategy {

	public StartingKingdomPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Init Phase: Start of Starting Kingdoms Phase");
		
		gv.getCurrentActionLbl().setText("Choose Starting Kingdom");

		// Already added in the view. This Button has only one function anyway.
//		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
//		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
//			
//			@Override
//			public void handle(ActionEvent arg0) {
//				turnEnd();
//			}
//		});
	}

	@Override
	public void turnStart() {
		super.turnStart();
	}

	@Override
	public void turnEnd() {
		
	}
	
	@Override
	public void phaseEnd() {

	}
}
