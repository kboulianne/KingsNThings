/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.main.KNTAppFactory;
import com.model.Hex;
import com.model.game.Game;
import com.presenter.Util;

/**
 *
 * @author kurtis
 */
class ConstructionPhase extends AbstractPhaseStrategy {

	ConstructionPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Construction Phase");

		gv.getCurrentActionLbl().setText("Construction Phase");
		
		Button finishBtn = KNTAppFactory.getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				context.endTurn();
			}
		});
		
		KNTAppFactory.getBoardPresenter().getView().addFortBuildingHandler();
		KNTAppFactory.getPlayerInfoPresenter().getView().setRackDoNothingHandler(game.getCurrentPlayer());
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Construction Phase");
		
		KNTAppFactory.getBoardPresenter().getView().addDefaultHandler();
		KNTAppFactory.getPlayerInfoPresenter().getView().setRackDefaultHandler(game.getCurrentPlayer());
	}

	@Override
	public void turnStart() {
		super.turnStart();
		KNTAppFactory.getSidePanePresenter().getView().showArbitraryView("Build new forts or upgrade existing forts", Game.CROWN_IMAGE);		
	}

	@Override
	public void turnEnd() {
		List<Hex> hexes = game.getBoard().getHexes();
		
		for(int i=0; i<hexes.size(); i++)	{
			if(hexes.get(i).getHexOwner() == game.getCurrentPlayer() &&
					hexes.get(i).getFort() != null)	{
				hexes.get(i).getFort().setUpgraded(false);
			}
		}
	}
}
