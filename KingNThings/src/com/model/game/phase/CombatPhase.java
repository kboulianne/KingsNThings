/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.main.KNTAppFactory;
import com.model.Battle;
import com.model.DesertCreature;
import com.model.Fort;
import com.model.JungleCreature;
import com.model.MountainCreature;
import com.model.Player;
import com.presenter.Util;
import com.view.DiceView;

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
		Player oppPlayer = game.getOpponent2();
		game.getBoard().getHexes().get(0).addItemToHex(new Fort(Fort.FortType.CITADEL));
		game.getBoard().getHexes().get(0).addCreatToArmy(new DesertCreature("skeletons"), oppPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new JungleCreature("watusi"), oppPlayer);
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("goblins"), game.getOpponent3());
		game.getBoard().getHexes().get(0).addCreatToArmy(new MountainCreature("ogre"), game.getOpponent1());
		
		/////////
		Battle battle = new Battle(game.getCurrentPlayer(), game.getBoard().getHexes().get(0));
		
		VBox rootBox = new VBox();
		Label titleLbl = new Label("Battle: <Instructions>");
		titleLbl.getStyleClass().add("title");
		Label roundNumLbl = new Label("Round Number: "+battle.getRoundNumber());
		Label battleRoundLbl = new Label("Phase: "+battle.getBattleRound().battleRoundName);
		Label HexLbl = new Label("Terrain: "+battle.getAssociatedHex().getTypeAsString());
		
		
		VBox offenderBox = new VBox();
		Label offenderLbl = new Label("Offender: "+battle.getOffender().getName());
		offenderLbl.getStyleClass().add("title");
		DiceView offDice = new DiceView();
		offDice.setDice(battle.getOffenderDie1(), battle.getOffenderDie2());
		offDice.setEndTurnButtonLbl("Retreat");
		offenderBox.getChildren().addAll(offenderLbl, offDice);
		
		VBox defenderBox = new VBox();
		Label defenderLbl = new Label("Defender: "+battle.getDefenderName());
		defenderLbl.getStyleClass().add("title");
		DiceView defDice = new DiceView();
		defDice.setDice(battle.getDefenderDie1(), battle.getDefenderDie2());
		defDice.setEndTurnButtonLbl("Retreat");
		defenderBox.getChildren().addAll(defenderLbl, defDice);
		
		HBox offenderDefenderBox = new HBox();
		offenderDefenderBox.getChildren().addAll(offenderBox, defenderBox);
		
		rootBox.getChildren().addAll(titleLbl, roundNumLbl, battleRoundLbl, HexLbl, offenderDefenderBox);
		
		KNTAppFactory.getPopupPresenter().getView().show(rootBox);
		//context.endTurn();
	}
	
	@Override
	public void turnEnd() {
		//GameService.getInstance().endTurn(this);
		
	}
}
