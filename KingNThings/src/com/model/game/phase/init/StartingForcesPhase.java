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
import com.model.Cup;
import com.model.Game;
import com.model.Player;
import com.model.Thing;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
public class StartingForcesPhase extends AbstractPhaseStrategy {

	public StartingForcesPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Init Phase: Start of Starting Forces Phase");

		getGamePresenter().getView().getCurrentActionLbl().setText("Place Forces");		
		
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				context.endTurn();
			}
		});
		finishBtn.setVisible(true);
		finishBtn.setDisable(false);
		getBoardPresenter().getView().addPlacementHandler();
	}
	
	@Override
	public void phaseEnd() {
		Util.log("Init Phase: End of Starting Forces Phase");
		getBoardPresenter().getView().addDefaultHandler();
		//Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		//finishBtn.setDisable(true);
		//finishBtn.setOnAction(null);
	}

	@Override
	public void turnStart() {
		super.turnStart();
		
		getSidePanePresenter().getView().showArbitraryView("Choose things from rack and\n"
																	   + "   place them on the board", Game.CROWN_IMAGE);
		
		Player player = game.getCurrentPlayer();
		player.addGold(10);
		Cup cup = game.getCup();
		
		for(int i = 0; i < 10; i++)	{
			Thing t = cup.getRandomThing();
			cup.removeThing(t);
			player.addThing(t);
		}
		
		getPlayerInfoPresenter().getView().setPlayer(player);	
	}

	@Override
	public void turnEnd() {
		//GameService.getInstance().endTurn(this);
		
	}
}
