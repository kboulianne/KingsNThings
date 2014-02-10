/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.main.KNTAppFactory;
import com.model.Battle;
import com.model.DesertCreature;
import com.model.Fort;
import com.model.JungleCreature;
import com.model.MountainCreature;
import com.model.Player;
import com.presenter.Util;
import com.view.BattleView;

/**
 *
 * @author kurtis
 */
public class CombatPhase extends AbstractPhaseStrategy {

	public CombatPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Combat Phase");

		gv.getCurrentActionLbl().setText("Combat Phase");
		
		Button finishBtn = KNTAppFactory.getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				context.endTurn();
			}
		});
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Combat Phase");
	}

	@Override
	public void turnStart() {
		super.turnStart();
		Util.log("Game Phase: Logic for " + game.getCurrentPlayer().getName());
		// for testing
		Player currentPlayer = game.getCurrentPlayer();
		game.getBoard().getHexes().get(0).addCreatToArmy(new DesertCreature("olddragon"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new DesertCreature("giantspider"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new JungleCreature("elephant"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("brownknight"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("giant"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("dwarves"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new JungleCreature("elephant"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("brownknight"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("giant"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("dwarves"), currentPlayer);
		Player oppPlayer = game.getOpponent2();
		game.getBoard().getHexes().get(0).addItemToHex(new Fort(Fort.FortType.CITADEL));
		game.getBoard().getHexes().get(0).addItemToHex(new DesertCreature("skeletons"));
		game.getBoard().getHexes().get(0).addCreatToArmy(new JungleCreature("watusi"), oppPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("goblins"), game.getOpponent3());
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("ogre"), game.getOpponent1());
		
		
		// search hexes find available battles
		
		// if no available battles end turn
		
		// highlight hexes with available battles
		
		
		///////// this will go in the handler for the hex click
		Battle battle = new Battle(game.getCurrentPlayer(), game.getBoard().getHexes().get(0));
		BattleView bp = KNTAppFactory.getBattlePresenter().getView();
		bp.setPopup(battle);
		KNTAppFactory.getPopupPresenter().getView().show(bp);
		
		KNTAppFactory.getBattlePresenter().startPhase();
		//context.endTurn();
	}
	
	@Override
	public void turnEnd() {
		//GameService.getInstance().endTurn(this);
		
	}
}
