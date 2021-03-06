/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase;

import com.model.Creature;
import com.model.Game;
import com.model.Hex;
import com.model.Player;
import com.model.SpecialCharacter;
import com.util.Util;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
class GoldCollectPhase extends AbstractPhaseStrategy {

	public GoldCollectPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		Util.log("Game Phase: Start of Gold Collection Phase");

		getBoardPresenter().getView().setDisable(true);
	}
	
	@Override
	public void phaseEnd(Game game) {
		Util.log("Game Phase: End of Gold Collection Phase");
		getSidePanePresenter().getView().clearDetailsView();
		getBoardPresenter().getView().setDisable(false);
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
		getPlayerInfoPresenter().getView().setRackDefaultHandler(game.getCurrentPlayer());
		
		int hexGold = 0;
		int fortGold = 0;
		int counterGold = 0;
		int specCharGold = 0;
		int totalGold = 0;

		Player player = game.getCurrentPlayer();
		
		for (Hex h : game.getBoard().getHexes()) {
			if ((h.getHexOwner() != null) && (h.getHexOwner().equals(player))) {
				hexGold++;
				if(h.getFort() != null)	
					fortGold += h.getFort().getValue();
				if(h.getCounter() != null)	
					counterGold += h.getCounter().getValue();
				if(h.getArmies(player) != null)	{
					for(Creature c: h.getArmies(player)) {
						if(c instanceof SpecialCharacter)	specCharGold++;
					}
				}
			}
		}
		
		hexGold = (int) Math.ceil(hexGold / 2.0);
		totalGold += (hexGold + fortGold + counterGold + specCharGold);	

		getSidePanePresenter().getView().showGoldCollection(hexGold, fortGold, counterGold, specCharGold);
		
		player.addGold(totalGold);
		getPlayerInfoPresenter().getView().setPlayer(player);
	}

	@Override
	public void turnEnd(Game game) {
		System.out.println();
	}

	@Override
	public String getActionText() {
		return "Gold Collection";
	}

}
