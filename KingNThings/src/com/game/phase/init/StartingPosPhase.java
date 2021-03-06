/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase.init;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.game.phase.AbstractPhaseStrategy;
import com.game.phase.GamePlay;
import com.model.Game;
import com.util.Util;

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
		finishBtn.setDisable(false);
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				getGamePresenter().endTurn();
			}
		});
		
		getDicePresenter().getView().getDie1().setVisible(false);
		getDicePresenter().getView().getDie2().setVisible(false);		
	}

	@Override
	public void phaseEnd(Game game) {
		Util.log("End of Starting Positions Phase");
		getBoardPresenter().getView().addDefaultHandler();		
		getSidePanePresenter().getView().clearDetailsView();
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);

		getBoardPresenter().getView().addStartPosHandler(game.getBoard().getStartPositions());
		getSidePanePresenter().getView().showArbitraryView("Choose a starting position", Game.START_HEX_IMAGE);
	}

	@Override
	public void turnEnd(Game game) {
		
	}

	@Override
	public String getActionText() {
		return "Choose Starting Position";
	}
}
