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
import com.model.Player;
import com.model.Player.PlayerId;
import com.model.SwampCreature;
import com.model.Thing;
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
		KNTAppFactory.getBoardPresenter().getView().addDefaultHandler();
		KNTAppFactory.getHexDetailsPresenter().getView().getCurrentPlayerArmy().setPhase("other");
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp1Army().setPhase("other");
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp2Army().setPhase("other");
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp3Army().setPhase("other");
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(true);
		finishBtn.setOnAction(null);
	}

	@Override
	public void turnStart() {
		super.turnStart();
		
		KNTAppFactory.getHexDetailsPresenter().getView().getCurrentPlayerArmy().setPhase("placement");
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp1Army().setPhase("placement");
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp2Army().setPhase("placement");
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp3Army().setPhase("placement");
		
		Player player = game.getCurrentPlayer();
		player.addGold(10);
		
		if(player.getId() == PlayerId.ONE)	{
			Creature olddragon = new DesertCreature("olddragon");
			Creature giantspider = new DesertCreature("giantspider");
			Creature elephant = new JungleCreature("elephant");
			Creature brownknight = new MountainCreature("brownknight");
			Creature giant = new MountainCreature("giant");
			Creature dwarves = new MountainCreature("dwarves1");
			Creature skeletons = new DesertCreature("skeletons");
			Creature watusi = new JungleCreature("watusi");
			Creature goblins = new MountainCreature("goblins");
			Creature ogre = new MountainCreature("ogre");
			
			player.addThing(olddragon);
			player.addThing(giantspider);
			player.addThing(elephant);
			player.addThing(brownknight);
			player.addThing(giant);
			player.addThing(dwarves);
			player.addThing(skeletons);
			player.addThing(watusi);
			player.addThing(goblins);
			player.addThing(ogre);
			
		} else if(player.getId() == PlayerId.TWO)	{
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
			
			player.addThing(pterodactlywarriors);
			player.addThing(sandworm);
			player.addThing(greenknight);
			player.addThing(dervish);
			player.addThing(crocodiles);
			player.addThing(nomads);
			player.addThing(druid);
			player.addThing(walkingtree);
			player.addThing(crawlingvines);
			player.addThing(bandits);
			
		} else if(player.getId() == PlayerId.THREE)	{
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
			
			player.addThing(centaur);
			player.addThing(camelcorps);
			player.addThing(farmers1);
			player.addThing(farmers2);
			player.addThing(genie);
			player.addThing(skeletons);
			player.addThing(pygmies);
			player.addThing(greathunter);
			player.addThing(nomads);
			player.addThing(witchdoctor);
			
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
			
			player.addThing(tribesmen1);
			player.addThing(giantlizard);
			player.addThing(villains);
			player.addThing(tigers);
			player.addThing(vampirebat);
			player.addThing(tribesmen2);
			player.addThing(darkwizard);
			player.addThing(blackknight);
			player.addThing(giantape);
			player.addThing(buffaloherd);
			
		}
		
		KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(player);	
	}

	@Override
	public void turnEnd() {
		//GameService.getInstance().endTurn(this);
		
	}
}
