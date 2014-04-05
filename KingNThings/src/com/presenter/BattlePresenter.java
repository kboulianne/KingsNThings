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

	private Player retreated;

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
			// TODO Auto-generated catch block
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
	public void triggerBattle(final Player current, List<Player> defending,
			Hex hex) {
		Util.playBattleMusic();

		// Tell the server to start a new battle
		try {
			battle = battleSvc.startBattle(NetworkedMain.getRoomName(),
					current, defending, hex);
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		startBattle();
		startPhase();
	}

	private void startBattle() {
		KNTAppFactory.getPopupPresenter().showBattlePopup();
//		view.updateBattle(battle);

		// Enable ui if current player
		if (battle.getCurrentPlayer().equals(NetworkedMain.getPlayer())) {
//			view.showRollControls();
//			view.setDisable(false);
			enableControlsForPhase();
		} else {
//			view.hideControls();
//			view.setDisable(true);
			disableControls();
		}

		// set handlers
		view.getOffRetreatBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				Util.playClickSound();
				battle.setBattlePhase(BattlePhase.POSTCOMBAT);
				retreated = battle.getOffender();

				// Dismiss the popup
				KNTAppFactory.getPopupPresenter().dismissPopup();
				startPhase();
			}
		});
		view.getOffContinueBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				Util.playClickSound();
				switchPlayers();
				
				// Update view
//				view.updateBattle(battle);
//				battle.setBattlePhase(BattlePhase.RETREAT);
//				retreatPhase();
			}
		});
		// view.getDefRetreatBtn().setOnAction(new EventHandler<ActionEvent>() {
		// @Override
		// public void handle(ActionEvent t) {
		// Util.playClickSound();
		// battle.setBattlePhase(BattlePhase.POSTCOMBAT);
		// retreated = battle.getDefender();
		//
		// KNTAppFactory.getPopupPresenter().dismissPopup();
		// startPhase();
		// }
		// });
		// view.getDefContinueBtn().setOnAction(new EventHandler<ActionEvent>()
		// {
		// @Override
		// public void handle(ActionEvent t) {
		// Util.playClickSound();
		//
		//
		// battle.setRoundNumber(battle.getRoundNumber() + 1);
		// battle.setBattlePhase(BattlePhase.MAGIC);
		// switchPlayers();
		// startPhase();
		//
		// }
		// });
		dice.getView().getRollBtn()
				.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent t) {
						handleRoll();
					}
				});

		defenderDice.getView().getRollBtn()
				.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent t) {
						handleRoll();
					}
				});
	}

	// STATE CHARGERS
//	private void nextBattlePhase() {
//		switchToNextPhase();
////		startPhase();
//	}

	private void switchToNextPhase() {
		// For good measure
		disableControls();
		Util.log("switching from phase " + battle.getBattlePhase() + " to ...");
		battle.setBattlePhase(BattlePhase.values()[battle.getBattlePhase()
				.ordinal() + 1]);
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
		case POSTCOMBAT:
			postCombatPhase();
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
//		if (battle.getOffenderCreaturesForPhase().isEmpty()
//				&& battle.getDefenderCreaturesForPhase().isEmpty()) {
//			// TODO: Trigger battle_phase_end
////			nextBattlePhase();
//			return;
//		}
//
//		// Change to getCreaturesForPhase using current player.
//		if (battle.getOffenderCreaturesForPhase().isEmpty()) {
//			// TODO: trigger battle_turn_end
//			switchPlayers();
//			creatureIt = battle.getDefenderCreaturesForPhase().iterator();
//		}

//		battle.setDefHits(0);
//		battle.setOffHits(0);

		// Activate dice, hide retreat stuff
//		dice.getView().setDisable(false);
//		defenderDice.getView().setDisable(false);
//		view.getOffContinueBtn().setDisable(true);
//		view.getOffRetreatBtn().setDisable(true);
		// view.getDefContinueBtn().setDisable(true);
		// view.getDefRetreatBtn().setDisable(true);
//		view.getDefGrid().setDisable(true);
//		view.getOffGrid().setDisable(true);

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
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Attacker can choose to retreat. If not then defender can choose to
		// retreat.

		// Ask attacker if he wants to retreat.
////		offenderDice.getView().setDisable(true);
////		defenderDice.getView().setDisable(true);
//		if (isDefender()) {
////			view.getOffContinueBtn().setDisable(true);
////			view.getOffRetreatBtn().setDisable(true);
//			// view.getDefContinueBtn().setDisable(false);
//			// view.getDefRetreatBtn().setDisable(false);
//		} else {
//			view.getOffContinueBtn().setDisable(false);
//			view.getOffRetreatBtn().setDisable(false);
//			// view.getDefContinueBtn().setDisable(true);
//			// view.getDefRetreatBtn().setDisable(true);
//		}
//		throw new UnsupportedOperationException("FIX BELOW!");
		// view.refreshView((isDefender() ? "Defender" : "Offender") +
		// " must choose to retreat or to continue with battle", "");
	}

	private void postCombatPhase() {
		// TODO: Move to server?
		throw new UnsupportedOperationException("FIX POST-COMBAT");
//		Util.stopBattleMusic();
//		Util.log("post combat phase player: "
//				+ battle.getCurrentPlayer().getName());
//
//		Hex associatedHex = battle.getAssociatedHex();
//		Player winner = null;
//		if (battle.isDefenderEliminated()) {
//			winner = battle.getOffender();
//			associatedHex.getArmies().remove(battle.getDefender());
//		} else if (battle.isOffenderEliminated()) {
//			winner = battle.getDefender();
//			associatedHex.getArmies().remove(battle.getOffender());
//		} else { // retreat
//			associatedHex.getArmies().remove(retreated);
//			if (retreated.equals(battle.getOffender())) {
//				winner = battle.getDefender();
//			} else {
//				winner = battle.getOffender();
//			}
//		}
//		
//		if(associatedHex.getOwner() == null && associatedHex.getKedabCreatures().isEmpty()){
//			associatedHex.setOwner(winner);
//		}else if(!associatedHex.getKedabCreatures().isEmpty() && associatedHex.getArmies().size()<2){
//			associatedHex.setConflict(false);
//		}
//		if(associatedHex.getArmies().keySet().size()==1 && associatedHex.getKedabCreatures().isEmpty()){ 	
//			associatedHex.setOwner(winner);
//			associatedHex.setConflict(false);
//		}
//		// FOR DEMO
//		KNTAppFactory
//				.getBoardPresenter()
//				.getView()
//				.setBoard(
//						KNTAppFactory.getGamePresenter().getLocalInstance()
//								.getBoard());
//		KNTAppFactory.getPopupPresenter().dismissPopup();
//		KNTAppFactory
//				.getSidePanePresenter()
//				.getView()
//				.showArbitraryView(
//						"The winner of the last battle was " + winner.getName(),
//						Game.CROWN_IMAGE);

		// FIXME winning game condition
		/*
		 * if(){
		 * 
		 * }
		 */
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
			}
			else {
				view.getOffGrid().setDisable(false);
			}
			break;
		case RETREAT:
			view.getRetreatButtonBox().setVisible(true);
//			view.getOffContinueBtn().setVisible(true);
//			view.getOffRetreatBtn().setVisible(true);
			break;
		default:
			disableControls();
		}
	}
	
	private void disableControls() {
		// No longer the player's turn, diable everything
		view.getRetreatButtonBox().setVisible(false);
		dice.getView().getRollBtn().setVisible(false);
//		view.getOffContinueBtn().setVisible(false);
//		view.getOffRetreatBtn().setVisible(false);
		view.getOffGrid().setDisable(true);
		view.getDefGrid().setDisable(true);
	}
	
	// HANDLERS
	public void handleThingPressed(Thing thing) {

		if (battle.getBattlePhase() == Battle.BattlePhase.APPLYMAJHITS
				|| battle.getBattlePhase() == Battle.BattlePhase.APPLYMELHITS
				|| battle.getBattlePhase() == Battle.BattlePhase.APPLYRANHITS) { // sanity
																					// check

			String info = "";
			
			if (battle.currentMustApplyHits()) {
				battle.applyHit((Creature) thing);
			}
			
			
//			if (isDefender()) {
//				battle.killDefenderCreature((Creature) thing);
//				info = "Offender killed creature "
//						+ thing.getName().toUpperCase();
//				Util.playSwordSound();
//
//				battle.setOffHits(battle.getOffHits() - 1);
//				view.updateBattle(battle);
//
//				// Winning Conditions
//				if (battle.isDefenderEliminated()
//						|| battle.isOffenderEliminated()) {
////					view.getDefGrid().setDisable(true);
////					view.getOffGrid().setDisable(true);
//					battle.setBattlePhase(BattlePhase.POSTCOMBAT);
//					postCombatPhase();
//					return;
//				}
//				if (battle.getOffHits() == 0) {
////					view.getDefGrid().setDisable(true);
////					view.getOffGrid().setDisable(true);
//					switchPlayers();
////					nextBattlePhase();
//					return;
//				}
//
//			} else { // Offender
//				battle.killOffenderCreature((Creature) thing);
//				info = "Defender killed creature "
//						+ thing.getName().toUpperCase();
//				Util.playSwordSound();
//
//				battle.setDefHits(battle.getDefHits() - 1);
//				view.updateBattle(battle);
//
//				// Winning Conditions
//				if (battle.isDefenderEliminated()
//						|| battle.isOffenderEliminated()) {
////					view.getDefGrid().setDisable(true);
////					view.getOffGrid().setDisable(true);
//					battle.setBattlePhase(BattlePhase.POSTCOMBAT);
//					postCombatPhase();
//					return;
//				}
//				if (battle.getDefHits() == 0) {
////					view.getDefGrid().setDisable(false);
////					view.getOffGrid().setDisable(true);
//					switchPlayers();
//					if (battle.getOffHits() == 0) {
////						view.getDefGrid().setDisable(true);
////						view.getOffGrid().setDisable(true);
//						switchPlayers();
////						nextBattlePhase();
//						return;
//					}
//				}
//			}
			
			// Update the battle and determine if more hits should be applied.
			try {
				battleSvc.updateBattle(NetworkedMain.getRoomName(), battle);
			} catch (JSONRPC2Error e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			// more hits to apply?
			if (battle.currentMustApplyHits())
				applyHits();
			else
				switchPlayers();

			view.updateBattle(battle);
			
			
		} else {
			Util.log("cannot apply hits");
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

		// Store the info, instructions etc... and send the updated battle to
		// the server.
		// TODO: Move up
		battle.setInfo(info);
		battle.setInstructions(player + " must roll for Creature " + currentCreature.getName().toUpperCase());

		try {
			battleSvc.updateBattle(NetworkedMain.getRoomName(), battle);
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Called once
	public void onBattleStarted() {
		// Called by defending players when an attacker triggers a battle on a
		// hex.

		// Fetch the battle instance and store it locally
		try {
			battle = battleSvc.getOngoingBattle(NetworkedMain.getRoomName());

			
			//TODO: Restrict
			//Re-enable GameUI if participating in the battle. Since gamepresenter locks it when it is not current turn in game.
			KNTAppFactory.getGamePresenter().getView().setDisable(false);
			KNTAppFactory.getPopupPresenter().showBattlePopup();
			// Sets up handlers
			startBattle();
			
			view.updateBattle(battle);
			
			// Show the ui and lock if not currently executing turn.
//			startBattle();
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Called every time a turn ends. (SwitchPlayers)
	public void onTurnEnded() {
//		disableControls();
		
		// Update the battle instance
		try {
			battle = battleSvc.getOngoingBattle(NetworkedMain.getRoomName());
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
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
}
