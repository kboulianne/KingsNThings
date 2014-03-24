/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase.init;

import javafx.scene.control.Button;

import com.game.phase.AbstractPhaseStrategy;
import com.game.phase.GamePlay;
import com.main.KNTAppFactory;
import com.model.Game;
import com.model.Player;
import com.presenter.Util;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
public class StartingPosPhase extends AbstractPhaseStrategy {
	
	private Button finishBtn;
	
	public StartingPosPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		Util.log("Start of Starting Positions Phase");
		
		finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(true);
		
		getDicePresenter().getView().getDie1().setVisible(false);
		getDicePresenter().getView().getDie2().setVisible(false);		
	}

	@Override
	public void phaseEnd() {
		Util.log("End of Starting Positions Phase");
//		getBoardPresenter().getView().setBoard(game.getBoard());
		getBoardPresenter().getView().addDefaultHandler();
//		finishBtn.setVisible(true);
		
		getSidePanePresenter().getView().clearDetailsView();
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);

		getBoardPresenter().getView().addStartPosHandler(game.getBoard().getStartPositions());
		getSidePanePresenter().getView().showArbitraryView("Choose a starting position", Game.START_HEX_IMAGE);

//		getBoardPresenter().getView().setBoard(game.getBoard());
				
		if(Util.AUTOMATE){
			Util.log("Automated");
			if(game.getCurrentPlayer().getId().equals(Player.PlayerId.ONE))
				getBoardPresenter().handleStartPositionSelectedHexClick(23);
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.TWO))
				getBoardPresenter().handleStartPositionSelectedHexClick(28);
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.THREE))
				getBoardPresenter().handleStartPositionSelectedHexClick(32);
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.FOUR))
				getBoardPresenter().handleStartPositionSelectedHexClick(19);
		}
	}

	@Override
	public void turnEnd(Game game) {
		
	}

	@Override
	public String getActionText() {
		return "Choose Starting Position";
	}
}
