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
import com.model.Creature;
import com.model.MountainCreature;
import com.model.Player;
import com.model.Player.PlayerId;
import com.model.game.Game;
import com.presenter.Util;
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public class RecruitThingsPhase extends AbstractPhaseStrategy {

	public RecruitThingsPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Recruiting Things Phase");
		
		gv.getCurrentActionLbl().setText("Recruit Things");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				context.endTurn();
			}
		});
		finishBtn.setDisable(false);
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Logic: End of Recruiting Things Phase");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(null);
		finishBtn.setDisable(true);
	}

	@Override
	public void turnStart() {
		super.turnStart();
		
		if(game.getCurrentPlayer().getId().equals(PlayerId.ONE))	{
			Util.log("Adding three creatures to Players 1 stack for Iteration 1");
			
			Player player = game.getCurrentPlayer();
			Creature cyclops = new MountainCreature("cyclops");
			Creature mountainmen = new MountainCreature("mountainmen");
			Creature goblins = new MountainCreature("goblins");
			
			player.removeGold(5);
			game.getBoard().getHexes().get(9).addCreatToArmy(cyclops, player);
			game.getBoard().getHexes().get(9).addCreatToArmy(mountainmen, player);
			game.getBoard().getHexes().get(9).addCreatToArmy(goblins, player);
		} else {
			Util.log("Skipping Step for " + game.getCurrentPlayer().getName() + " for Iteration 1");
			context.endTurn();
		}
	}

	@Override
	public void turnEnd() {
		
	}

}
