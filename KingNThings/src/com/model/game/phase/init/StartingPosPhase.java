/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import javafx.scene.control.Button;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Hex;
import com.model.Player;
import com.model.game.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public class StartingPosPhase extends AbstractPhaseStrategy /*implements HexInput*/ {

	
	Game game;
	GameView gv;
	
	Button finishBtn;
	
	
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

		Util.log("Init Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
	}

//    @Override
//    public void executePhase(final Hex hex) {
//	
//    }
	*/
	@Override
	public void phaseStart() {
		Util.log("Start of Starting Positions Phase");
		
		game =  GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();
		gv.getCurrentActionLbl().setText("Choose Starting Position");
		
		finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		
		KNTAppFactory.getDicepresenter().getView().getDie1().setVisible(false);
		KNTAppFactory.getDicepresenter().getView().getDie2().setVisible(false);
		finishBtn.setText("End Turn");
		
		finishBtn.setVisible(false);
		KNTAppFactory.getBoardpresenter().getView().addStartPosHandler(this);
		
//		turnStart();
	}

	@Override
	public void phaseEnd() {
		Util.log("End of Starting Positions Phase");
		game.getBoard().setFaceDown(false);
		for(Hex h: game.getBoard().getHexes())
			KNTAppFactory.getBoardpresenter().getView().paintHex(h);
		KNTAppFactory.getBoardpresenter().getView().addDefaultHandler();
		
		
		finishBtn.setVisible(true);
		// done automatically on call to GamePlay#next()
//		new StartingKingdomPhase(context).phaseStart();
		
	}

	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		super.turnStart();
		
		// Hardcoded for iteration 1
		Util.log("Selection hardcoded for iteration 1");
		if(game.getCurrentPlayer().getId().equals(Player.PlayerId.ONE))
			KNTAppFactory.getBoardpresenter().handleStartPositionSelectedHexClick(23, this);
		else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.TWO))
			KNTAppFactory.getBoardpresenter().handleStartPositionSelectedHexClick(28, this);
		else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.THREE))
			KNTAppFactory.getBoardpresenter().handleStartPositionSelectedHexClick(32, this);
		else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.FOUR))
			KNTAppFactory.getBoardpresenter().handleStartPositionSelectedHexClick(19, this);
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
//		GameService.getInstance().endTurn(this);
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
