/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.DesertCreature;
import com.model.JungleCreature;
import com.model.MountainCreature;
import com.model.Player;
import com.model.game.Game;
import com.presenter.Util;
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public class MovementPhase extends AbstractPhaseStrategy {

	Game game;
	GameView gv;
	
	public MovementPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Movement Phase");
		
		game = GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();
		
		gv.getCurrentActionLbl().setText("Movement Phase");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
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
		KNTAppFactory.getBoardPresenter().getView().addDefaultHandler();
		KNTAppFactory.getHexDetailsPresenter().getView().getCurrentPlayerArmy().setMovementPhase(false);
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp1Army().setMovementPhase(false);
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp2Army().setMovementPhase(false);
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp3Army().setMovementPhase(false);
		Util.log("Game Phase: End of Movement Phase");
	}

	@Override
	public void turnStart() {
		super.turnStart();
				
		KNTAppFactory.getHexDetailsPresenter().getView().getCurrentPlayerArmy().setMovementPhase(true);
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp1Army().setMovementPhase(false);
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp2Army().setMovementPhase(false);
		KNTAppFactory.getHexDetailsPresenter().getView().getOpp3Army().setMovementPhase(false);
		// for testing
		//Player 1
		//Stack 1
		
		Player currentPlayer = game.getCurrentPlayer();
		Util.log("curr"+ currentPlayer.getName());
		game.getBoard().getHexes().get(2).addCreatToArmy(new DesertCreature("olddragon"), currentPlayer);
		game.getBoard().getHexes().get(2).addCreatToArmy(new DesertCreature("giantspider"), currentPlayer);
		game.getBoard().getHexes().get(2).addCreatToArmy(new JungleCreature("elephant"), currentPlayer);
		game.getBoard().getHexes().get(2).addCreatToArmy(new MountainCreature("brownknight"), currentPlayer);
		game.getBoard().getHexes().get(2).addCreatToArmy(new MountainCreature("giant"), currentPlayer);
		game.getBoard().getHexes().get(2).addCreatToArmy(new MountainCreature("dwarves"), currentPlayer);
		//Stack 2
		game.getBoard().getHexes().get(9).addCreatToArmy(new DesertCreature("skeletons"), currentPlayer);
		game.getBoard().getHexes().get(9).addCreatToArmy(new JungleCreature("watusi"), currentPlayer);
		game.getBoard().getHexes().get(9).addCreatToArmy(new MountainCreature("goblins"), currentPlayer);
		game.getBoard().getHexes().get(9).addCreatToArmy(new MountainCreature("ogre"), currentPlayer);
		
		
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
		//GameService.getInstance().endTurn(this);
		
	}
}
