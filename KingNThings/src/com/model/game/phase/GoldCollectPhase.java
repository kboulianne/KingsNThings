/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Creature;
import com.model.Hex;
import com.model.Player;
import com.model.SpecialCharacter;
import com.model.game.Game;
import com.presenter.Util;

/**
 *
 * @author kurtis
 */
class GoldCollectPhase extends AbstractPhaseStrategy {

	GoldCollectPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Gold Collection Phase");

		gv.getCurrentActionLbl().setText("Gold Collection");
		KNTAppFactory.getBoardPresenter().getView().setDisable(true);
	}
	
	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Gold Collection Phase");
		KNTAppFactory.getSidePanePresenter().getView().clearDetailsView();
		KNTAppFactory.getBoardPresenter().getView().setDisable(false);
		KNTAppFactory.getPlayerInfoPresenter().getView().setRackDefaultHandler(game.getCurrentPlayer());
	}

	@Override
	public void turnStart() {
		super.turnStart();
		KNTAppFactory.getPlayerInfoPresenter().getView().setRackTreasureExchangeHandler(game.getCurrentPlayer());
		
		Util.log("Game Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
		
		int hexGold = 0;
		int fortGold = 0;
		int counterGold = 0;
		int specCharGold = 0;
		int totalGold = 0;
		Game game = GameService.getInstance().getGame();
		Player player = game.getCurrentPlayer();
		
		for (Hex h : game.getBoard().getHexes()) {
			if ((h != null) && (h.getHexOwner() == player)) {
				hexGold++;
				if(h.getFort() != null)	fortGold += h.getFort().getValue();
				if(h.getCounter() != null)	counterGold += h.getCounter().getValue();
				if(h.getArmies(player) != null)	{
					for(Creature c: h.getArmies(player)) {
						if(c instanceof SpecialCharacter)	specCharGold++;
					}
				}
			}
		}
		
		hexGold = (int) Math.ceil(hexGold/2.0);
		totalGold += (hexGold + fortGold + counterGold + specCharGold);	

		KNTAppFactory.getSidePanePresenter().getView().showGoldCollection(hexGold, fortGold, counterGold, specCharGold);
		
		player.addGold(totalGold);
		KNTAppFactory.getPlayerInfoPresenter().getView().updateGold(player);
	}

	@Override
	public void turnEnd() {
		
	}

}
