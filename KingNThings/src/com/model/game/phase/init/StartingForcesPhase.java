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
import com.model.Player;
import com.model.Player.PlayerId;
import com.model.game.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;

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

		gv.getCurrentActionLbl().setText("Place Forces");		
		
		Button finishBtn = KNTAppFactory.getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				context.endTurn();
			}
		});
		finishBtn.setVisible(true);
		finishBtn.setDisable(false);
		KNTAppFactory.getBoardPresenter().getView().addPlacementHandler();
	}
	
	@Override
	public void phaseEnd() {
		Util.log("Init Phase: End of Starting Forces Phase");
		//Button finishBtn = KNTAppFactory.getDicePresenter().getView().getEndTurnBtn();
		//finishBtn.setDisable(true);
		//finishBtn.setOnAction(null);
	}

	@Override
	public void turnStart() {
		super.turnStart();
		
		KNTAppFactory.getSidePanePresenter().getView().showArbituaryView("Choose things from rack and\n"
																	   + "   them place on the board", Game.CROWN_IMAGE);
		
		Player player = game.getCurrentPlayer();
		player.addGold(10);
		
		if(player.getId() == PlayerId.ONE)	{
			
			game.moveThingFromCupToPlayer("olddragon", player);
			game.moveThingFromCupToPlayer("giantspider", player);
			game.moveThingFromCupToPlayer("elephant", player);
			game.moveThingFromCupToPlayer("brownknight", player);
			game.moveThingFromCupToPlayer("giant", player);
			game.moveThingFromCupToPlayer("dwarves1", player);
			game.moveThingFromCupToPlayer("skeletons", player);
			game.moveThingFromCupToPlayer("watusi", player);
			game.moveThingFromCupToPlayer("goblins", player);
			game.moveThingFromCupToPlayer("ogre", player);

		} else if(player.getId() == PlayerId.TWO)	{
			
			game.moveThingFromCupToPlayer("pterodactylwarriors", player);
			game.moveThingFromCupToPlayer("sandworm", player);
			game.moveThingFromCupToPlayer("greenknight", player);
			game.moveThingFromCupToPlayer("dervish", player);
			game.moveThingFromCupToPlayer("crocodilesjungle", player);
			game.moveThingFromCupToPlayer("nomads", player);
			game.moveThingFromCupToPlayer("druid", player);
			game.moveThingFromCupToPlayer("walkingtree", player);
			game.moveThingFromCupToPlayer("crawlingvines", player);
			game.moveThingFromCupToPlayer("bandits", player);
			
		} else if(player.getId() == PlayerId.THREE)	{
			
			game.moveThingFromCupToPlayer("centaur", player);
			game.moveThingFromCupToPlayer("camelcorps", player);
			game.moveThingFromCupToPlayer("farmers", player);
			game.moveThingFromCupToPlayer("farmers", player);
			game.moveThingFromCupToPlayer("genie", player);
			game.moveThingFromCupToPlayer("skeletons", player);
			game.moveThingFromCupToPlayer("pygmies", player);
			game.moveThingFromCupToPlayer("greathunter", player);
			game.moveThingFromCupToPlayer("nomads", player);
			game.moveThingFromCupToPlayer("witchdoctor", player);
			
		} else	{
			game.moveThingFromCupToPlayer("tribesmen", player);
			game.moveThingFromCupToPlayer("giantlizard", player);
			game.moveThingFromCupToPlayer("villains", player);
			game.moveThingFromCupToPlayer("tigers", player);
			game.moveThingFromCupToPlayer("vampirebat", player);
			game.moveThingFromCupToPlayer("tribesmen", player);
			game.moveThingFromCupToPlayer("darkwizard", player);
			game.moveThingFromCupToPlayer("blackknight", player);
			game.moveThingFromCupToPlayer("giantape", player);
			game.moveThingFromCupToPlayer("buffaloherd", player);
		}
		
		KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(player);	
	}

	@Override
	public void turnEnd() {
		//GameService.getInstance().endTurn(this);
		
	}
}
