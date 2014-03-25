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
import com.main.KNTAppFactory;
import com.model.Cup;
import com.model.Game;
import com.model.Player;
import com.model.Thing;
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
	public void phaseStart(Game game) {
		Util.log("Init Phase: Start of Starting Forces Phase");

//		getGamePresenter().getView().getCurrentActionLbl().setText("Place Forces");		
		
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				getGamePresenter().endTurn();
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
	public void turnStart(Game game) {
		super.turnStart(game);
		
		getSidePanePresenter().getView().showArbitraryView("Choose things from rack and\n"
																	   + "   place them on the board", Game.CROWN_IMAGE);
//---------------------------
// NOW ON SERVER
//---------------------------
//		Player player = game.getCurrentPlayer();
//		player.addGold(10);
//		Cup cup = game.getCup();
//		
//		for(int i = 0; i < 10; i++)	{
//			Thing t = cup.getRandomThing();
//			cup.removeThing(t);
//			player.addThing(t);
//		}
		
//		getPlayerInfoPresenter().getView().setPlayer(player);	
	}

	@Override
	public void turnEnd(Game game) {
	}

	@Override
	public String getActionText() {
		return "Place Forces";
	}
}
