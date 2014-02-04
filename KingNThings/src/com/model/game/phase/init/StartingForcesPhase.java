/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Creature;
import com.model.DesertCreature;
import com.model.ForestCreature;
import com.model.JungleCreature;
import com.model.MountainCreature;
import com.model.PlainsCreature;
import com.model.Player.PlayerId;
import com.model.SwampCreature;
import com.model.game.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;
import com.view.GameView;

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
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				context.endTurn();
			}
		});
	}
	
	@Override
	public void phaseEnd() {
		Util.log("Init Phase: End of Starting Forces Phase");
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(null);
	}

	@Override
	public void turnStart() {
		super.turnStart();
		
		if(game.getCurrentPlayer().getId() == PlayerId.ONE)	{
			Creature oldragon = new DesertCreature("olddragon");
			Creature giantspider = new DesertCreature("giantspider");
			Creature elephant = new JungleCreature("elephant");
			Creature brownknight = new MountainCreature("brownknight");
			Creature giant = new MountainCreature("giant");
			Creature dwarves = new MountainCreature("dwarves1");
			Creature skeletons = new DesertCreature("skeletons");
			Creature watusi = new JungleCreature("watusi");
			Creature goblins = new MountainCreature("goblins");
			Creature ogre = new MountainCreature("ogre");
		} else if(game.getCurrentPlayer().getId() == PlayerId.TWO)	{
			Creature pterodactlywarriors = new JungleCreature("pterodactylwarriors");
			Creature sandworm = new DesertCreature("sandworm");
			Creature greenknight = new ForestCreature("sandworm");
			Creature dervish = new DesertCreature("dervish");
			Creature crocodiles = new JungleCreature("crocodilesjungle");
			Creature nomads = new DesertCreature("nomads");
			Creature druid = new ForestCreature("druid");
			Creature walkingtree = new ForestCreature("walkingtree");
			Creature crawlingvines = new JungleCreature("crawlingvines");
			Creature bandits = new ForestCreature("bandits");
		} else if(game.getCurrentPlayer().getId() == PlayerId.THREE)	{
			Creature centaur = new PlainsCreature("centaur");
			Creature camelcorps = new DesertCreature("camelcorps");
			Creature farmers1 = new PlainsCreature("farmers");
			Creature farmers2 = new PlainsCreature("farmers");
			Creature genie = new DesertCreature("genie");
			Creature skeletons=  new DesertCreature("skeletons");
			Creature pygmies = new JungleCreature("pygmies");
			Creature greathunter = new PlainsCreature("greathunter");
			Creature nomads = new DesertCreature("nomads");
			Creature witchdoctor = new JungleCreature("witchdoctor");
		} else	{
			Creature tribesmen1 = new PlainsCreature("tribesmen");
			Creature giantlizard = new SwampCreature("giantlizard");
			Creature villains = new PlainsCreature("villains");
			Creature tigers = new JungleCreature("tigers");
			Creature vampirebat = new SwampCreature("vampirebat");
			Creature tribesmen2 = new PlainsCreature("tribesmen");
			Creature darkwizard = new SwampCreature("darkwizard");
			Creature blackknight = new SwampCreature("blackknight");
 			Creature giantape = new JungleCreature("giantape");
			Creature buffaloherd = new PlainsCreature("buffaloherd");
		}
	}

	@Override
	public void turnEnd() {
		//GameService.getInstance().endTurn(this);
		
	}
}
