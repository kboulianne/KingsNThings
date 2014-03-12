package com.presenter;

import com.game.services.GameService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import com.main.KNTAppFactory;
import com.model.Battle;
import com.model.Battle.BattlePhase;
import com.model.Creature;
import com.model.Player;
import com.view.BattleView;
import java.util.Iterator;

public class BattlePresenter {

	private BattleView view;
	private Battle battle;
	
	private DicePresenter attackerDice;
	private DicePresenter defenderDice;

	private Iterator<Creature> creatureIt;
	private Creature currentCreature;
	
	//private Map<Creature, Integer> attackerRolls;
	//private Map<Creature, Integer> defenderRolls;
	
	private Player retreated;
	
	/** Determines whether the attacker or defender is rolling. */
	private boolean isDefender;

	public BattlePresenter(BattleView v, DicePresenter dp1, DicePresenter dp2) {
		view = v;

		attackerDice = dp1;
		defenderDice = dp2;
		
		//attackerRolls = new HashMap<>();
		//defenderRolls = new HashMap<>();
		
		// Disable second die
		attackerDice.getView().hideDie2(true);
		defenderDice.getView().hideDie2(true);
	}

	public BattleView getView() {
		return view;
	}

	// use this to start battle
	void startBattle(Battle b) {
		// Display the battle in the view
		battle = b;
		view.setBattle(battle);

		// set handlers
		view.getOffRetreatBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				battle.setBattlePhase(BattlePhase.POSTCOMBAT);
				retreated = battle.getOffender();
				nextBattlePhase();
				
				// Dismiss the popup
				KNTAppFactory.getPopupPresenter().dismissPopup();
			}
		});
		view.getOffContinueBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				nextBattlePhase();
			}
		});
		view.getDefRetreatBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				battle.setBattlePhase(BattlePhase.POSTCOMBAT);
				retreated = battle.getDefender();
				nextBattlePhase();
				KNTAppFactory.getPopupPresenter().dismissPopup();
			}
		});
		view.getDefContinueBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				nextBattlePhase();
			}
		});
		startPhase();
	}

	private void startPhase() {		
		switch (battle.getBattlePhase()) {
			case MAGIC:
			case RANGED:
			case MELEE:
				creatureIt = battle.getAttackerCreaturesForPhase().iterator();
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
	}
	
	// TODO: Split this up.
	private void handleRoll() {
		// Fetch the next creature and store roll. Assume 1-to-1 mapping for now,
		// att. c1 -> def. c1, att. c2 -> def.c2 ...
		int roll;
		String info ="";
		
		//Creature c = creatureIt.next();
		if(currentCreature.isCharge()){
			//TODO roll with two dice
		}
		
		
		if (!isDefender) {			
			// Attacker's turn to roll.
			roll = attackerDice.rollOne();
			
			Util.log("	Offender rolled " +roll + " for "+ currentCreature.getName());
			info = "Offender rolled " +roll + " for "+ currentCreature.getName();
			if(roll<=currentCreature.getCombatVal()){
				battle.setOffHits(battle.getOffHits()+1);
				info += " and gained 1 hit point";
			}else{
				info += " and gained no hit points";
			}
				
			//attackerRolls.put(currentCreature, roll );
			
			// Attacker is done, now defender's turn
			if (!creatureIt.hasNext()) {
				isDefender = true;
				
				// Swap iterator to defender's creatures.
				creatureIt = battle.getDefenderCreaturesForPhase().iterator();
				currentCreature = creatureIt.next();
				battle.setCurrentPlayer(battle.getDefender());
			}else{
				currentCreature = creatureIt.next();
			}
		}
		else {
			// Defender's turn to roll.
			roll = defenderDice.rollOne();
			//defenderRolls.put(currentCreature, roll);
			Util.log("	Deffender rolled " +roll + " for "+ currentCreature.getName());
			info = "Defender rolled " +roll + " for "+ currentCreature.getName();
			if(roll<=currentCreature.getCombatVal()){
				battle.setDefHits(battle.getDefHits()+1);
				info += " and gained 1 hit point";
			}else{
				info += " and gained no hit points";
			}
			
			// Defender is done, apply hits, switch phase and reset iterator.
			if (!creatureIt.hasNext()) {
				isDefender = false;
				nextBattlePhase();
				view.setInfoLbl(info);
				return;
			}else{
				currentCreature = creatureIt.next();
			}
		}
		view.refreshView((isDefender ? "Defender" : "Offender") + " must roll for Creature "+ currentCreature.getName().toUpperCase(), info);
		enableControlsForRoll();
	}
	
	/**
	 * Determines which controls to display according to attacker/defender turn.
	 */
	private void enableControlsForRoll() {
		if (!isDefender) {
			attackerDice.getView().getRollBtn().setDisable(false);
			defenderDice.getView().getRollBtn().setDisable(true);//setVisible(false);
		}
		else {
			attackerDice.getView().getRollBtn().setDisable(true);
			defenderDice.getView().getRollBtn().setDisable(false);
		}
	}
	
	private void applyHits() {
		// For the demo, assume a 1-to-1 hit ratio. Ignores Creature selection.
		Util.log("apply hits");
		attackerDice.getView().setDisable(true);
		defenderDice.getView().setDisable(true);
		
		if(isDefender){
			
		}else{ // offender
			
		}
		
		view.refreshView((isDefender ? "Defender" : "Offender") + "must apply opponent's hits to creatures", "");
		/*
		Iterator<Map.Entry<Creature, Integer>> attacker = attackerRolls.entrySet().iterator();
		Iterator<Map.Entry<Creature, Integer>> defender = defenderRolls.entrySet().iterator();
		
		Map.Entry<Creature, Integer> attEntry = null;
		Map.Entry<Creature, Integer> defEntry = null;
		
		// Assumes for now that highest roll wins
		while (attacker.hasNext()) {
			attEntry = attacker.next();
			
			if (defender.hasNext()) {
				defEntry = defender.next();
				
				int attRoll = attEntry.getValue();
				int defRoll = defEntry.getValue();
				
				if (attRoll >= defRoll) {
					// Attacker wins, kill defender's creature
					battle.killDefenderCreature(defEntry.getKey());
					System.out.println("	Offender killed: " + defEntry.getKey().getName());
				}
				else {
					// Defender wins, kill attacker's creature.
					battle.killAttackerCreature(attEntry.getKey());
					System.out.println("	Defender killed: " + attEntry.getKey().getName());
				}
			}
			else {
				break;
			}
		}
		
		// clear rolls
		attackerRolls.clear();
		defenderRolls.clear();
		
		// Update view
		view.setBattle(battle);*/
	}
	
	// Roll Phases
	private void rollPhase() {
		Util.log("roll phase "+battle.getBattlePhase());
		if (battle.canSkipPhase()) {
			nextBattlePhase();
		}
		// for now
		if(battle.getAttackerCreaturesForPhase() ==  null){
			Util.log("Iterator is NULL");
			nextBattlePhase();
		}
		
		attackerDice.getView().getRollBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				handleRoll();
			}
		});
		
		defenderDice.getView().getRollBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				handleRoll();
			}
		});
		
		battle.setDefHits(0);
		battle.setOffHits(0);
		
		// Activate dice, hide retreat stuff
		attackerDice.getView().setDisable(false);
		defenderDice.getView().setDisable(false);
		view.getOffContinueBtn().setDisable(true);
		view.getOffRetreatBtn().setDisable(true);
		view.getDefContinueBtn().setDisable(true);
		view.getDefRetreatBtn().setDisable(true);
		
		
		// Initialize the iterator
		creatureIt = battle.getAttackerCreaturesForPhase().iterator();
		currentCreature = creatureIt.next();
		enableControlsForRoll();
		
		view.refreshView((isDefender ? "Defender" : "Offender") + " must roll for Creature "+ currentCreature.getName().toUpperCase(),"");
	}
	private void retreatPhase() {
		Util.log("retreat phase");
		// Attacker can choose to retreat. If not then defender can choose to retreat.
		
		// Ask attacker if he wants to retreat.
		attackerDice.getView().setDisable(true);
		defenderDice.getView().setDisable(true);
		if (!isDefender) {
			view.getOffContinueBtn().setDisable(false);
			view.getOffRetreatBtn().setDisable(false);
			view.getDefContinueBtn().setDisable(true);
			view.getDefRetreatBtn().setDisable(true);
		}
		else {
			view.getOffContinueBtn().setDisable(true);
			view.getOffRetreatBtn().setDisable(true);
			view.getDefContinueBtn().setDisable(false);
			view.getDefRetreatBtn().setDisable(false);
		}

		view.refreshView((isDefender ? "Defender" : "Offender") + " must choose to retreat or to continue with battle", "");
	}
	private void postCombatPhase() {
		Util.log("post combat phase");
		// For demo, eliminate his armies, set hex ownership to opponent
//		battle.getAssociatedHex().getArmies(retreated);
		battle.getAssociatedHex().getArmies().remove(retreated);
		if (retreated.equals(battle.getOffender())) {
			battle.getAssociatedHex().setOwner(battle.getDefender());
		} 
		else {
			battle.getAssociatedHex().setOwner(battle.getOffender());
		}
		
		battle.getAssociatedHex().setConflict(false);
		// FOR DEMO
		KNTAppFactory.getBoardPresenter().getView().setBoard(
				GameService.getInstance().getGame().getBoard()
		);
		
		view.refreshView("post combat","");
	}

	
	
	

	private void switchToNextPhase() {
		Util.log("switching from phase "+battle.getBattlePhase()+" to ...");
		if (battle.getBattlePhase() == BattlePhase.RETREAT) {
			if (isDefender) {
				battle.setRoundNumber(battle.getRoundNumber() + 1);
				battle.setBattlePhase(BattlePhase.MAGIC);
				isDefender = false;
			}
			else {
				isDefender = true;
				retreatPhase();
			}
		} 
		else if (battle.getBattlePhase() == BattlePhase.POSTCOMBAT) { 
			
		}	
		else {
			battle.setBattlePhase(BattlePhase.values()[battle.getBattlePhase().ordinal() + 1]);		
		}
		Util.log("	"+battle.getBattlePhase());
	}
	private void nextBattlePhase() {
		switchToNextPhase();
		startPhase();
	}

	public boolean isDefender() {
		return isDefender;
	}
}
