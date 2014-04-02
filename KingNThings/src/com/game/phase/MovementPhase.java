/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.main.KNTAppFactory;
import com.model.Game;
import com.util.Util;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
class MovementPhase extends AbstractPhaseStrategy {

	MovementPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		getBoardPresenter().getView().setDisable(false);
		
		Util.log("Game Phase: Start of Movement Phase");

//		getGamePresenter().getView().getCurrentActionLbl().setText("Movement Phase");
		
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(false);
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent arg0) {
				KNTAppFactory.getGamePresenter().endTurn();
			}
		});
		
		getBoardPresenter().getView().addMovementHandler();
	}

	@Override
	public void phaseEnd() {
		getArmyDetailsPresenter().getView().setDefaultHandler();
		getBoardPresenter().getView().addDefaultHandler();
		Util.log("Game Phase: End of Movement Phase");
	}

	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
		getArmyDetailsPresenter().getView().setMovementHandler();	
		getHexDetailsPresenter().getView().getCurrentPlayerArmy().setMoving(true);
		getHexDetailsPresenter().getView().getCounter().setMoving(true);		
		getSidePanePresenter().getView().showArbitraryView("Move characters within hexes", Game.CROWN_IMAGE);
		
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
	public void turnEnd(Game game) {
		super.turnEnd(game);
		getHexDetailsPresenter().getView().getCurrentPlayerArmy().setMoving(false);
		getHexDetailsPresenter().getView().getCounter().setMoving(false);
	}

	@Override
	public String getActionText() {
		return "Movement Phase";
	}
}
