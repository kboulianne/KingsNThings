/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.game.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public class StartingForcesPhase extends AbstractPhaseStrategy<Object> {

	Game game;
	GameView gv;
	
	
	public StartingForcesPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		System.out.println("Init Phase: Start of Starting Forces Phase");
		
		game =  GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();
		gv.getCurrentActionLbl().setText("Place Forces");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getFinishTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				turnEnd();
			}
		});
	}

	/*
	@Override
	public void executePhase(Object input) {
		System.out.println("Init Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
	}
	*/
	
	@Override
	public void phaseEnd() {
		System.out.println("Init Phase: End of Starting Forces Phase");
		new ExchangePhase(context).phaseStart();
	}

	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		gv.getCurrentPlayerLbl().setText(game.getCurrentPlayer().getName()+"'s Turn: ");
		
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
		GameService.getInstance().endTurn(this);
		
	}

	@Override
	public void addHandlers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeHandlers() {
		// TODO Auto-generated method stub
		
	}

}
