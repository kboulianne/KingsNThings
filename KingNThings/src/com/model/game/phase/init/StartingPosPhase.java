/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import com.main.KNTAppFactory;
import com.model.Hex;
import com.model.Player;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;

/**
 *
 * @author kurtis
 */
public class StartingPosPhase extends AbstractPhaseStrategy {
	
	Button finishBtn;
	
	
	public StartingPosPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Start of Starting Positions Phase");
		
		gv.getCurrentActionLbl().setText("Choose Starting Position");
		
		finishBtn = KNTAppFactory.getDicePresenter().getView().getEndTurnBtn();
		KNTAppFactory.getDicePresenter().getView().getRollBtn().setVisible(false);
		KNTAppFactory.getDicePresenter().getView().getDie1().setVisible(false);
		KNTAppFactory.getDicePresenter().getView().getDie2().setVisible(false);		
		finishBtn.setVisible(false);
		KNTAppFactory.getBoardPresenter().getView().addStartPosHandler(game.getBoard().getStartPositions());
		
		KNTAppFactory.getSidePanePresenter().getView().showArbituaryView("Choose a starting position", Hex.START_IMAGE);
	}

	@Override
	public void phaseEnd() {
		Util.log("End of Starting Positions Phase");
		game.getBoard().setFaceDown(false);
		KNTAppFactory.getBoardPresenter().getView().setBoard(game.getBoard());
		KNTAppFactory.getBoardPresenter().getView().addDefaultHandler();
		finishBtn.setVisible(true);
		
		KNTAppFactory.getSidePanePresenter().getView().clearDetailsView();
	}

	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		super.turnStart();

		// Hardcoded for iteration 1
		Util.log("Selection hardcoded for iteration 1");
		if(game.getCurrentPlayer().getId().equals(Player.PlayerId.ONE))
			KNTAppFactory.getBoardPresenter().handleStartPositionSelectedHexClick(23);
		else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.TWO))
			KNTAppFactory.getBoardPresenter().handleStartPositionSelectedHexClick(28);
		else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.THREE))
			KNTAppFactory.getBoardPresenter().handleStartPositionSelectedHexClick(32);
		else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.FOUR))
			KNTAppFactory.getBoardPresenter().handleStartPositionSelectedHexClick(19);
	}

	@Override
	public void turnEnd() {
		
	}
}
