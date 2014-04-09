package com.presenter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.main.KNTAppFactory;
import com.main.NetworkedMain;
import com.model.Battle;
import com.model.Battle.BattlePhase;
import com.model.Creature;
import com.model.Game;
import com.model.Hex;
import com.model.Player;
import com.model.Thing;
import com.server.services.IBattleService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.util.Util;
import com.view.BattleView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BattlePresenter {

	private BattleView view;
	// The local instance of the battle currently being fought.
	private Battle battle;
	private IBattleService battleSvc;

	private DicePresenter dice;
	private DicePresenter defenderDice;

	private Iterator<Creature> creatureIt;
	private Creature currentCreature;

	public BattlePresenter(BattleView v, DicePresenter dp1, DicePresenter dp2,
			IBattleService svc) {
		view = v;
		battleSvc = svc;

		dice = dp1;
		defenderDice = dp2;

		// Disable second die
		dice.getView().hideDie2(true);
		defenderDice.getView().hideDie2(true);
	}

	public BattleView getView() {
		return view;
	}

	public boolean isDefender() {
		return battle.getCurrentPlayer().equals(battle.getDefender());
	}

	public void switchPlayers() {
		disableControls();
		if (isDefender()) {
			battle.setCurrentPlayer(battle.getOffender());
		} else {
			battle.setCurrentPlayer(battle.getDefender());
		}
		System.out.println("Switched players");
		
		
		// Trigger battle turn end
		try {
			battleSvc.battleTurnEnded(NetworkedMain.getRoomName(), battle);
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
		

	}

	/**
	 * Called by the attacker to trigger a battle between themselves and the
	 * defender/s on the hex.
	 * 
	 * @param current
	 *            The current player
	 * @param defending
	 *            The players being attacked
	 * @param hex
	 *            The hex the battle is triggered on.
	 */
	public void triggerBattle(final Player current, Player defending,
			Hex hex) {
		Util.playBattleMusic();

		// Tell the server to start a new battle
		try {
			List<Player> def = new ArrayList<>();
			def.add(defending);
			battle = battleSvc.startBattle(NetworkedMain.getRoomName(),
					current, def, hex);
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}

		startBattle();
		startPhase();
	}

	private void startBattle() {
		NetworkedMain.setView(view);
		disableControls();
		// Enable ui if current player
		if (battle.getCurrentPlayer().equals(NetworkedMain.getPlayer())) {
			enableControlsForPhase();
		} 

		// set handlers
		view.getOffRetreatBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				try {
					battleSvc.retreat(NetworkedMain.getRoomName());
				} catch (JSONRPC2Error e) {
					e.printStackTrace();
				}
			}
		});
		view.getOffContinueBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				Util.playClickSound();
				switchPlayers();
			}
		});

		dice.getView().getRollBtn()
			.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					handleRoll();
				}
			});
	}

	private void switchToNextPhase() {
		// For good measure
		disableControls();
		Util.log("switching from phase " + battle.getBattlePhase() + " to ...");
		if (battle.getBattlePhase().ordinal() + 1 < 7)
			battle.setBattlePhase(BattlePhase.values()[battle.getBattlePhase().ordinal() + 1]);
		else {
			battle.setBattlePhase(BattlePhase.MAGIC);
			battle.incrementRoundNumber();
		}
		Util.log("	" + battle.getBattlePhase());
		
	
	}

	private void startPhase() {
		switch (battle.getBattlePhase()) {
		case MAGIC:
		case RANGED:
		case MELEE:
			creatureIt = battle.getCreaturesForPhase().iterator();
			rollPhase();
			break;
		case RETREAT:
			retreatPhase();
			break;
		case APPLYMAJHITS:
		case APPLYMELHITS:
		case APPLYRANHITS:
			applyHits();
			break;
		default:
			break;
		}
		
		enableControlsForPhase();
	}

	// Phases

	// Roll Phases
	private void rollPhase() {
		Util.log("roll phase " + battle.getBattlePhase() + " player: "
				+ battle.getCurrentPlayer().getName());
		
		// Determines if player has to roll in this phase
		if (battle.getCreaturesForPhase().isEmpty()) {
			switchPlayers();
			return;
		}

		if (creatureIt.hasNext()) { 
			currentCreature = creatureIt.next();
		}

		if (currentCreature.isCharge()) {
			if (isDefender()) {
				defenderDice.getView().hideDie2(false);
			} else {
				dice.getView().hideDie2(false);
			}
		}

		battle.setInstructions((isDefender() ? "Defender" : "Offender") +
				" must roll for Creature "+
				currentCreature.getName().toUpperCase());
		battle.setInfo("");
		view.updateBattle(battle);
		
		// Changes were made, update the server.
		try {
			battleSvc.updateBattle(NetworkedMain.getRoomName(), battle);
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
	}

	private void applyHits() {
		Util.log("apply hits player: " + battle.getCurrentPlayer().getName());

		if (battle.currentMustApplyHits()) {
			battle.setInstructions((isDefender() ? "Defender" : "Offender") +
					" must apply opponent's hits to creatures");
			battle.setInfo("");
			
			try {
				battleSvc.updateBattle(NetworkedMain.getRoomName(), battle);
			} catch (JSONRPC2Error e) {
				e.printStackTrace();
			}
			
			view.updateBattle(battle);
			
		}
		else {
			System.out.println("No hits to apply.");
			switchPlayers();
		}
	}

	private void retreatPhase() {
		Util.log("retreat phase player: " + battle.getCurrentPlayer().getName());
		
		// Current player may choose to retreat.
		battle.setInstructions((isDefender() ? "Defender" : "Offender") +
				" must choose to retreat or to continue with battle");
		battle.setInfo("");
		view.updateBattle(battle);
		
		try {
			battleSvc.updateBattle(NetworkedMain.getRoomName(), battle);
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
	}

	private void enableControlsForPhase() {
		switch (battle.getBattlePhase()) {
		case MAGIC:
		case RANGED:
		case MELEE:
			view.getRetreatButtonBox().setDisable(false);
			dice.getView().getRollBtn().setVisible(true);
			break;
		case APPLYMAJHITS:
		case APPLYRANHITS:
		case APPLYMELHITS:
			if (isDefender()) {
				view.getDefGrid().setDisable(false);
			}else {
				view.getOffGrid().setDisable(false);
			}
			break;
		case RETREAT:
			view.getRetreatButtonBox().setVisible(true);
			break;
		default:
			disableControls();
		}
	}
	
	private void disableControls() {
		// No longer the player's turn, disable everything
		view.getRetreatButtonBox().setVisible(false);
		dice.getView().getRollBtn().setVisible(false);
		view.getOffGrid().setDisable(true);
		view.getDefGrid().setDisable(true);
	}
	
	// HANDLERS
	public void handleThingPressed(Thing thing) {

		if (battle.getBattlePhase() == Battle.BattlePhase.APPLYMAJHITS
				|| battle.getBattlePhase() == Battle.BattlePhase.APPLYMELHITS
				|| battle.getBattlePhase() == Battle.BattlePhase.APPLYRANHITS) { // sanity check
			
			if (battle.currentMustApplyHits()) {
				battle.applyHit((Creature) thing);
			}
			
			// Update the battle and determine if more hits should be applied.
			try {
				battleSvc.updateBattle(NetworkedMain.getRoomName(), battle);
			} catch (JSONRPC2Error e) {
				e.printStackTrace();
			}
		
			// more hits to apply
			if (battle.currentMustApplyHits())
				applyHits();
			else
				switchPlayers();

			view.updateBattle(battle);
			
			
		} else {
			Util.log("Cannot apply hits");
		}
	}

	private void handleRoll() {

		int hitPts = 0;
		String player = (isDefender() ? "Defender" : "Offender");
		String info = "";
		
		

		if (currentCreature.isCharge()) { // charging creature gets two die
			int[] diceRolls = dice.rollTwo();
			if (diceRolls[0] <= currentCreature.getCombatVal()) {
				hitPts++;
			}
			if (diceRolls[1] <= currentCreature.getCombatVal()) {
				hitPts++;
			}
			info = player + " rolled a " + diceRolls[0] + " and a "
					+ diceRolls[1] + " for "
					+ currentCreature.getName().toUpperCase()
					+ " and gained " + hitPts + " hit point(s)";
		} 
		else {
			int roll = dice.rollOne();
			if (roll <= currentCreature.getCombatVal()) {
				hitPts++;
			}
			info = player + " rolled " + roll + " for "
					+ currentCreature.getName().toUpperCase()
					+ " and gained " + hitPts + " hit point(s)";
		}
		
		// Store hits
		if (isDefender())
			battle.setDefHits(battle.getDefHits() + hitPts);
		else
			battle.setOffHits(battle.getOffHits() + hitPts);
		
		// Current player is done, switch to next
		if (!creatureIt.hasNext()) {
			System.out.println("No more rolls for current. Trigger turn end");
			switchPlayers();
			return;
		}
		else {
			currentCreature = creatureIt.next();
			if (currentCreature.isCharge()) {
				dice.getView().hideDie2(false);
			} else {
				dice.getView().hideDie2(true);
			}
		}

		// Store the info, instructions etc... and send the updated battle to the server.
		battle.setInfo(info);
		battle.setInstructions(player + " must roll for Creature " + currentCreature.getName().toUpperCase());

		try {
			battleSvc.updateBattle(NetworkedMain.getRoomName(), battle);
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
		view.updateBattle(battle);
	}

	public void onUpdated() {
		disableControls();
		// Fetch the updated battle instance.
		try {
			battle = battleSvc.getOngoingBattle(NetworkedMain.getRoomName());

			// Update the ui
			view.updateBattle(battle);
			
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
	}

	// Called once
	public void onBattleStarted() {
		// Called by defending players when an attacker triggers a battle on a hex.

		// Fetch the battle instance and store it locally
		try {
			battle = battleSvc.getOngoingBattle(NetworkedMain.getRoomName());

			
			//TODO: Restrict
			//Re-enable GameUI if participating in the battle. Since game presenter locks it when it is not current turn in game.
			KNTAppFactory.getGamePresenter().getView().setDisable(false);

			// Sets up handlers
			startBattle();
			
			view.updateBattle(battle);
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
	}

	// Called every time a turn ends. (SwitchPlayers)
	public void onTurnEnded() {
		
		// Update the battle instance
		try {
			battle = battleSvc.getOngoingBattle(NetworkedMain.getRoomName());
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
		
		// Check if it is now this client's turn to play. Always the case for 1-to-1 PVP
		if (battle.getCurrentPlayer().equals(NetworkedMain.getPlayer())) {			
			// Need to switch phases?
			if (battle.getCurrentPlayer().equals(battle.getOffender())) {
				switchToNextPhase();
			}
			
			// Start the phase
			startPhase();
		}
	}

	public void onBattleEnded() {
		// Update
		try {
			battle = battleSvc.getOngoingBattle(NetworkedMain.getRoomName());
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}
		
		// Show game view again
		NetworkedMain.setView(KNTAppFactory.getGamePresenter().getView());
		KNTAppFactory.getPopupPresenter().dismissPopup();
		KNTAppFactory
				.getSidePanePresenter()
				.getView()
				.showArbitraryView(
						"The winner of the last battle was " + battle.getWinner().getName(),
						Game.CROWN_IMAGE);
		

		
		
		// Update in game presenter
		KNTAppFactory.getGamePresenter().updateViews();
	}
}
