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
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public class ConstructionPhase extends AbstractPhaseStrategy<Object> {

	Game game;
	GameView gv;
	
	public ConstructionPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		System.out.println("Game Phase: Start of Construction Phase");
		game =  GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();
		gv.getCurrentActionLbl().setText("Construction Phase");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getFinishTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				turnEnd();
			}
		});
		
		turnStart();
		
	}

	/*@Override
	public void executePhase(Object input) {
		System.out.println("Game Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
	}*/

	@Override
	public void phaseEnd() {
		System.out.println("Game Phase: End of Construction Phase");
		
		new SpecialPowersPhase(context).phaseStart();
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
