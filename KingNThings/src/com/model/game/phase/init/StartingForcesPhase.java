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
		Cup cup = game.getCup();
		if(player.getId() == PlayerId.ONE)	{
			
			game.moveThingFromCupToPlayer(cup.getThing("olddragon"), player);
			game.moveThingFromCupToPlayer(cup.getThing("giantspider"), player);
			game.moveThingFromCupToPlayer(cup.getThing("elephant"), player);
			game.moveThingFromCupToPlayer(cup.getThing("brownknight"), player);
			game.moveThingFromCupToPlayer(cup.getThing("giant"), player);
			game.moveThingFromCupToPlayer(cup.getThing("dwarves1"), player);
			game.moveThingFromCupToPlayer(cup.getThing("skeletons"), player);
			game.moveThingFromCupToPlayer(cup.getThing("watusi"), player);
			game.moveThingFromCupToPlayer(cup.getThing("goblins"), player);
			game.moveThingFromCupToPlayer(cup.getThing("ogre"), player);

		} else if(player.getId() == PlayerId.TWO)	{
			
			game.moveThingFromCupToPlayer(cup.getThing("pterodactylwarriors"), player);
			game.moveThingFromCupToPlayer(cup.getThing("sandworm"), player);
			game.moveThingFromCupToPlayer(cup.getThing("greenknight"), player);
			game.moveThingFromCupToPlayer(cup.getThing("dervish"), player);
			game.moveThingFromCupToPlayer(cup.getThing("crocodilesjungle"), player);
			game.moveThingFromCupToPlayer(cup.getThing("nomads"), player);
			game.moveThingFromCupToPlayer(cup.getThing("druid"), player);
			game.moveThingFromCupToPlayer(cup.getThing("walkingtree"), player);
			game.moveThingFromCupToPlayer(cup.getThing("crawlingvines"), player);
			game.moveThingFromCupToPlayer(cup.getThing("bandits"), player);
			
		} else if(player.getId() == PlayerId.THREE)	{
			
			game.moveThingFromCupToPlayer(cup.getThing("centaur"), player);
			game.moveThingFromCupToPlayer(cup.getThing("camelcorps"), player);
			game.moveThingFromCupToPlayer(cup.getThing("farmers"), player);
			game.moveThingFromCupToPlayer(cup.getThing("farmers"), player);
			game.moveThingFromCupToPlayer(cup.getThing("genie"), player);
			game.moveThingFromCupToPlayer(cup.getThing("skeletons"), player);
			game.moveThingFromCupToPlayer(cup.getThing("pygmies"), player);
			game.moveThingFromCupToPlayer(cup.getThing("greathunter"), player);
			game.moveThingFromCupToPlayer(cup.getThing("nomads"), player);
			game.moveThingFromCupToPlayer(cup.getThing("witchdoctor"), player);
			
		} else	{
			game.moveThingFromCupToPlayer(cup.getThing("tribesmen"), player);
			game.moveThingFromCupToPlayer(cup.getThing("giantlizard"), player);
			game.moveThingFromCupToPlayer(cup.getThing("villains"), player);
			game.moveThingFromCupToPlayer(cup.getThing("tigers"), player);
			game.moveThingFromCupToPlayer(cup.getThing("vampirebat"), player);
			game.moveThingFromCupToPlayer(cup.getThing("tribesmen"), player);
			game.moveThingFromCupToPlayer(cup.getThing("darkwizard"), player);
			game.moveThingFromCupToPlayer(cup.getThing("blackknight"), player);
			game.moveThingFromCupToPlayer(cup.getThing("giantape"), player);
			game.moveThingFromCupToPlayer(cup.getThing("buffaloherd"), player);
		}
		
		KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(player);	
	}

	@Override
	public void turnEnd() {
		//GameService.getInstance().endTurn(this);
		
	}
}
