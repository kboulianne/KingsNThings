/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.main.KNTAppFactory;
import com.model.Creature;
import com.model.Game;
import com.model.Hex;
import com.model.Player;
import com.model.SpecialCharacter;
import com.presenter.Util;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
class GoldCollectPhase extends AbstractPhaseStrategy {

	GoldCollectPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		Util.log("Game Phase: Start of Gold Collection Phase");

		getGamePresenter().getView().getCurrentActionLbl().setText("Gold Collection");
		getBoardPresenter().getView().setDisable(true);
	}
	
	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Gold Collection Phase");
		getSidePanePresenter().getView().clearDetailsView();
		getBoardPresenter().getView().setDisable(false);
		getPlayerInfoPresenter().getView().setRackDefaultHandler(game.getCurrentPlayer());
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
		getPlayerInfoPresenter().getView().setRackTreasureExchangeHandler(game.getCurrentPlayer());
		//FIXME: Use service in context.
//		Util.log("Game Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
		
		int hexGold = 0;
		int fortGold = 0;
		int counterGold = 0;
		int specCharGold = 0;
		int totalGold = 0;
//		Game game = GameService.getInstance().getGame();
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

		getSidePanePresenter().getView().showGoldCollection(hexGold, fortGold, counterGold, specCharGold);
		
		player.addGold(totalGold);
		getPlayerInfoPresenter().getView().updateGold(player);
	}

	@Override
	public void turnEnd(Game game) {
		
	}

}
