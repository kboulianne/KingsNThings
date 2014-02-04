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
public class SpecialPowersPhase extends AbstractPhaseStrategy {

	
	Game game;
	GameView gv;
	
	public SpecialPowersPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Special Powers Phase");
		
		game =  GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();
		
		gv.getCurrentActionLbl().setText("Deploy Special Powers");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				turnEnd();
			}
		});
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Special Powers Phase");
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
		// Done in gameplay
//		GameService.getInstance().endTurn(this);
	}
}
