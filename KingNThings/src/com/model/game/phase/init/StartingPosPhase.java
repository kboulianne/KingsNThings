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
import com.model.Hex;
import com.model.game.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public class StartingPosPhase extends AbstractPhaseStrategy<Object> /*implements HexInput*/ {

	
	Game game;
	GameView gv;
	
	
	public StartingPosPhase(GamePlay context) {
		super(context);
		

	}

	/**
	 * Execute this phase's logic.
	 *
	 * @param input The hex tile selected as the start position.
	 */
	/*
	@Override
	public void executePhase(final Hex input) {
//	Game game = service.getGame();
//	
//	// Set the starting position for the current player.  Just need to take ownership of the hex.	
//	input.setOwner(game.getCurrentPlayer());
//	
//	// Next player
//	game.nextPlayer();

		System.out.println("Init Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
	}

//    @Override
//    public void executePhase(final Hex hex) {
//	
//    }
	*/
	@Override
	public void phaseStart() {
		System.out.println("Start of Starting Positions Phase");
		
		game =  GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();
		gv.getCurrentActionLbl().setText("Choose Starting Position");
		
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

	@Override
	public void phaseEnd() {
		System.out.println("End of Starting Positions Phase");
		game.getBoard().setFaceDown(false);
		for(Hex h: game.getBoard().getHexes())
			KNTAppFactory.getBoardpresenter().getView().paintHex(h);
		KNTAppFactory.getBoardpresenter().getView().addDefaultHandler();
		
		new StartingKingdomPhase(context).phaseStart();
		
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
