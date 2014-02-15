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
import com.presenter.Util;

/**
 *
 * @author kurtis
 */
public class MovementPhase extends AbstractPhaseStrategy {

	public MovementPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Movement Phase");

		gv.getCurrentActionLbl().setText("Movement Phase");
		
		Button finishBtn = KNTAppFactory.getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(false);
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent arg0) {
				context.endTurn();
			}
		});
		
		KNTAppFactory.getBoardPresenter().getView().addMovementHandler();
	}

	@Override
	public void phaseEnd() {
		KNTAppFactory.getArmyDetailsPresenter().getView().setDefaultHandler();
		KNTAppFactory.getBoardPresenter().getView().addDefaultHandler();
		KNTAppFactory.getHexDetailsPresenter().getView().getCurrentPlayerArmy().setMoving(false);
		Util.log("Game Phase: End of Movement Phase");
	}

	@Override
	public void turnStart() {
		super.turnStart();
		KNTAppFactory.getArmyDetailsPresenter().getView().setMovementHandler();	
		KNTAppFactory.getHexDetailsPresenter().getView().getCurrentPlayerArmy().setMoving(true);		
		
		/*// for testing
		Player currentPlayer = game.getCurrentPlayer();
		game.getBoard().getHexes().get(0).addCreatToArmy(new DesertCreature("olddragon"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new DesertCreature("giantspider"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new JungleCreature("elephant"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("brownknight"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("giant"), currentPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("dwarves"), currentPlayer);
		Player oppPlayer = game.getOpponent2();
		game.getBoard().getHexes().get(0).addItemToHex(new Fort(Fort.FortType.CITADEL));
		game.getBoard().getHexes().get(0).addItemToHex(new MountainCreature("goblins"));
		game.getBoard().getHexes().get(0).addCreatToArmy(new JungleCreature("watusi"), oppPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("goblins"), game.getOpponent3());
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("ogre"), game.getOpponent1());*/
		
	}

	@Override
	public void turnEnd() {
		super.turnEnd();
	}
}
