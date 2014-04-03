/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.main.KNTAppFactory;
import com.model.Game;
import com.model.Hex;
import com.model.Player;
import com.util.Util;

import static com.main.KNTAppFactory.*;


/**
 *
 * @author kurtis
 */
class ConstructionPhase extends AbstractPhaseStrategy {

	ConstructionPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		Util.log("Game Phase: Start of Construction Phase");

		getGamePresenter().getView().getCurrentActionLbl().setText("Construction Phase");
		
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
			}
		});
		
		getBoardPresenter().getView().addFortBuildingHandler();
		getPlayerInfoPresenter().getView().setRackDoNothingHandler(game.getCurrentPlayer());
	}

	@Override
	public void phaseEnd(Game game) {
		Util.log("Game Phase: End of Construction Phase");
		KNTAppFactory.getBoardPresenter().getView().addDefaultHandler();
		KNTAppFactory.getPlayerInfoPresenter().getView().setRackDefaultHandler(game.getCurrentPlayer());
		
		int total = 0;
		Player citOwner = null;
		List<Player> players = game.getPlayerOrder();
		
		for(int i=0; i<players.size(); i++)	{
			if(players.get(i).isCitadelOwner())	{
				total++;
				citOwner = players.get(i);
			}
		}
		
		if(total == 1)	{
			citOwner.addTimeCitOwned();
			if(citOwner.getTimeCitOwned() == 1)
				KNTAppFactory.getGamePresenter().getView().showWinnerScreen(citOwner);
		}

	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
		getSidePanePresenter().getView().showArbitraryView("Build new forts or upgrade existing forts", Game.CROWN_IMAGE);		
	}

	@Override
	public void turnEnd(Game game) {
		List<Hex> hexes = game.getBoard().getHexes();
		
		for(int i=0; i<hexes.size(); i++)	{
			if(hexes.get(i).getHexOwner() == game.getCurrentPlayer() &&
					hexes.get(i).getFort() != null)	{
				hexes.get(i).getFort().setUpgraded(false);
			}
		}
	}

	@Override
	public String getActionText() {
		return "Update Me!";
	}
	
	
}
