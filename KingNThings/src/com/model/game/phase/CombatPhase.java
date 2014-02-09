/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import com.main.KNTAppFactory;
import com.model.Battle;
import com.model.Creature;
import com.model.DesertCreature;
import com.model.Fort;
import com.model.JungleCreature;
import com.model.MountainCreature;
import com.model.Player;
import com.presenter.Util;
import com.view.DiceView;
import com.view.popups.BattlePopup;

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
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
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
		
		/////////
		Battle battle = new Battle(game.getCurrentPlayer(), game.getBoard().getHexes().get(0));
		BattlePopup bp = new BattlePopup();
		bp.setPopup(battle);
		KNTAppFactory.getPopupPresenter().getView().show(bp);
		//context.endTurn();
	}
	
	@Override
	public void turnEnd() {
		//GameService.getInstance().endTurn(this);
		
	}
}
