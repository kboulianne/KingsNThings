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
public class CombatPhase extends AbstractPhaseStrategy {

	Game game;
	GameView gv;
	
	public CombatPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Combat Phase");
		game =  GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();
		gv.getCurrentActionLbl().setText("Combat Phase");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				turnEnd();
			}
		});
		
		turnStart();
		
	}
/*
	@Override
	public void executePhase(Object input) {
		Util.log("Game Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
	}
*/

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Combat Phase");
		new ConstructionPhase(context).phaseStart();
	}

	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		
		super.turnStart();
		
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
