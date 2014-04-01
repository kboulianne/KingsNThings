package com.presenter;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.main.KNTAppFactory;
import com.model.Battle;
import com.model.Battle.BattlePhase;
import com.model.Creature;
import com.model.Game;
import com.model.Player;
import com.model.Thing;
import com.view.BattleView;

import java.util.Iterator;

public class BattlePresenter {

	private BattleView view;
	private Battle battle;
	
	private DicePresenter offenderDice;
	private DicePresenter defenderDice;

	private Iterator<Creature> creatureIt;
	private Creature currentCreature;
	
	private Player retreated;

	public BattlePresenter(BattleView v, DicePresenter dp1, DicePresenter dp2) {
		view = v;

		offenderDice = dp1;
		defenderDice = dp2;
		
		// Disable second die
		offenderDice.getView().hideDie2(true);
		defenderDice.getView().hideDie2(true);
	}

	public BattleView getView() {
		return view;
	}
	public boolean isDefender() {
		return battle.getCurrentPlayer().equals(battle.getDefender());
	}
	public void switchPlayers(){
		if(isDefender()){
			battle.setCurrentPlayer(battle.getOffender());
		}else{
			battle.setCurrentPlayer(battle.getDefender());
		}
	}
	
	//TODO move to view
	/**
	 * Determines which controls to display according to attacker/defender turn.
	 */
	private void enableControlsForRoll() {
		if (isDefender()) {
			offenderDice.getView().getRollBtn().setDisable(true);
			defenderDice.getView().getRollBtn().setDisable(false);
		}
		else {
			offenderDice.getView().getRollBtn().setDisable(false);
			defenderDice.getView().getRollBtn().setDisable(true);
		}
	}

	// use this to start battle
	void startBattle(Battle b) {
		Util.playBattleMusic();
		
		// Display the battle in the view
		battle = b;
		view.setBattle(battle);

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
				battle.setBattlePhase(BattlePhase.RETREAT);
				retreatPhase();
			}
		});
		view.getDefRetreatBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				Util.playClickSound();
				battle.setBattlePhase(BattlePhase.POSTCOMBAT);
				retreated = battle.getDefender();
				
				KNTAppFactory.getPopupPresenter().dismissPopup();
				startPhase();
			}
		});
		view.getDefContinueBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				Util.playClickSound();

				
				battle.setRoundNumber(battle.getRoundNumber() + 1);
				battle.setBattlePhase(BattlePhase.MAGIC);
				switchPlayers();
				startPhase();
				
			}
		});
		offenderDice.getView().getRollBtn().setOnAction(new EventHandler<ActionEvent>() {
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
		
		startPhase();
	}

	// STATE CHARGERS
	private void nextBattlePhase() {
		switchToNextPhase();
		startPhase();
	}
	private void switchToNextPhase() {
		Util.log("switching from phase "+battle.getBattlePhase()+" to ...");
		battle.setBattlePhase(BattlePhase.values()[battle.getBattlePhase().ordinal() + 1]);		
		Util.log("	"+battle.getBattlePhase());
	}
	
	
	private void startPhase() {		
		switch (battle.getBattlePhase()) {
			case MAGIC:
			case RANGED:
			case MELEE:
				creatureIt = battle.getOffenderCreaturesForPhase().iterator();
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
	
	
	//Phases

	// Roll Phases
	private void rollPhase() {
		Util.log("roll phase "+battle.getBattlePhase() + " player: "+battle.getCurrentPlayer().getName());
		if (battle.getOffenderCreaturesForPhase().isEmpty() && 
				battle.getDefenderCreaturesForPhase().isEmpty()) {
			nextBattlePhase();
			return;
		}
		
		if(battle.getOffenderCreaturesForPhase().isEmpty()){
			switchPlayers();
			creatureIt = battle.getDefenderCreaturesForPhase().iterator();
		}
		
		battle.setDefHits(0);
		battle.setOffHits(0);
		
		// Activate dice, hide retreat stuff
		offenderDice.getView().setDisable(false);
		defenderDice.getView().setDisable(false);
		view.getOffContinueBtn().setDisable(true);
		view.getOffRetreatBtn().setDisable(true);
		view.getDefContinueBtn().setDisable(true);
		view.getDefRetreatBtn().setDisable(true);
		view.getDefGrid().setDisable(true);
		view.getOffGrid().setDisable(true);
		
		currentCreature = creatureIt.next();
		enableControlsForRoll();
		
		if(currentCreature.isCharge()){
			if(isDefender()){
				defenderDice.getView().hideDie2(false);
			}else {
				offenderDice.getView().hideDie2(false);
			}
		}
		
		view.refreshView((isDefender() ? "Defender" : "Offender") + " must roll for Creature "+ currentCreature.getName().toUpperCase(),"");
	}
	private void applyHits() {
		Util.log("apply hits player: "+battle.getCurrentPlayer().getName());
		
		if(battle.getDefHits()==0 && battle.getOffHits() == 0){
			battle.setCurrentPlayer(battle.getOffender());
			view.getDefGrid().setDisable(true);
			view.getOffGrid().setDisable(true);
			battle.setCurrentPlayer(battle.getOffender());
			nextBattlePhase();
			return;
		}else if(battle.getDefHits()==0){
			battle.setCurrentPlayer(battle.getDefender());
			view.getDefGrid().setDisable(false);
			view.getOffGrid().setDisable(true);
		}else{
			view.getDefGrid().setDisable(true);
			view.getOffGrid().setDisable(false);
		}
		offenderDice.getView().setDisable(true);
		defenderDice.getView().setDisable(true);
		view.refreshView((isDefender() ? "Defender" : "Offender") + " must apply opponent's hits to creatures", "");
	}
	private void retreatPhase() {
		Util.log("retreat phase player: "+battle.getCurrentPlayer().getName());
		
		// Attacker can choose to retreat. If not then defender can choose to retreat.
		
		// Ask attacker if he wants to retreat.
		offenderDice.getView().setDisable(true);
		defenderDice.getView().setDisable(true);
		if (isDefender()) {
			view.getOffContinueBtn().setDisable(true);
			view.getOffRetreatBtn().setDisable(true);
			view.getDefContinueBtn().setDisable(false);
			view.getDefRetreatBtn().setDisable(false);
		}
		else {
			view.getOffContinueBtn().setDisable(false);
			view.getOffRetreatBtn().setDisable(false);
			view.getDefContinueBtn().setDisable(true);
			view.getDefRetreatBtn().setDisable(true);
		}	
		
		view.refreshView((isDefender() ? "Defender" : "Offender") + " must choose to retreat or to continue with battle", "");
	}
	
	
	private void postCombatPhase() {
<<<<<<< HEAD
		throw new IllegalAccessError("Use new service.");
//		Util.stopBattleMusic();
//		Util.log("post combat phase player: "+battle.getCurrentPlayer().getName());
//		// For demo, eliminate his armies, set hex ownership to opponent
////		battle.getAssociatedHex().getArmies(retreated);
//		Player winner = null;
//		if(battle.isDefenderEliminated()){
//			winner = battle.getOffender();
//			battle.getAssociatedHex().getArmies().remove(battle.getDefender());
//		}else if(battle.isAttackerEliminated()){
//			winner = battle.getDefender();
//			battle.getAssociatedHex().getArmies().remove(battle.getOffender());
//		}else{ //retreat
//			battle.getAssociatedHex().getArmies().remove(retreated);
//			if (retreated.equals(battle.getOffender())) {
//				winner = battle.getDefender();
//			} 
//			else {
//				winner = battle.getOffender();
//			}
//		}
//		
//		battle.getAssociatedHex().setOwner(winner);
//		battle.getAssociatedHex().setConflict(false);
//		// FOR DEMO
//		KNTAppFactory.getBoardPresenter().getView().setBoard(GameService.getInstance().getGame().getBoard());
//		KNTAppFactory.getPopupPresenter().dismissPopup();
//		KNTAppFactory.getSidePanePresenter().getView().showArbitraryView("The winner of the last battle was "+winner.getName(), Game.CROWN_IMAGE);
=======
		Util.stopBattleMusic();
		Util.log("post combat phase player: "+battle.getCurrentPlayer().getName());
		// For demo, eliminate his armies, set hex ownership to opponent
//		battle.getAssociatedHex().getArmies(retreated);
		Player winner = null;
		if(battle.isDefenderEliminated()){
			winner = battle.getOffender();
			battle.getAssociatedHex().getArmies().remove(battle.getDefender());
		}else if(battle.isOffenderEliminated()){
			winner = battle.getDefender();
			battle.getAssociatedHex().getArmies().remove(battle.getOffender());
		}else{ //retreat
			battle.getAssociatedHex().getArmies().remove(retreated);
			if (retreated.equals(battle.getOffender())) {
				winner = battle.getDefender();
			} 
			else {
				winner = battle.getOffender();
			}
		}
		
		battle.getAssociatedHex().setOwner(winner);
		battle.getAssociatedHex().setConflict(false);
		// FOR DEMO
		KNTAppFactory.getBoardPresenter().getView().setBoard(GameService.getInstance().getGame().getBoard());
		KNTAppFactory.getPopupPresenter().dismissPopup();
		KNTAppFactory.getSidePanePresenter().getView().showArbitraryView("The winner of the last battle was "+winner.getName(), Game.CROWN_IMAGE);
>>>>>>> battle-test
	}
	
	
	// HANDLERS
	public void handleThingPressed(Thing thing){
	
		if(battle.getBattlePhase() == Battle.BattlePhase.APPLYMAJHITS ||
				battle.getBattlePhase() == Battle.BattlePhase.APPLYMELHITS ||
				battle.getBattlePhase() == Battle.BattlePhase.APPLYRANHITS ){ //sanity check
			
			String info = "";
			if(isDefender()){
				battle.killDefenderCreature((Creature)thing);
				info = "Offender killed creature " + thing.getName().toUpperCase();
				//TODO 
				Util.playSwordSound();
				//Util.playDeathSound();
				// Update view
				view.setBattle(battle);
				battle.setOffHits(battle.getOffHits()-1);
				if(battle.getOffHits()==0){
					view.getDefGrid().setDisable(true);
					view.getOffGrid().setDisable(true);
					switchPlayers();
					nextBattlePhase();
				}
				// Winning Conditions
				if(battle.isDefenderEliminated() || battle.isOffenderEliminated()){
					view.getDefGrid().setDisable(true);
					view.getOffGrid().setDisable(true);
					battle.setBattlePhase(BattlePhase.POSTCOMBAT);
					postCombatPhase();
					return;
				}
				return;
				
			}else{ // Offender
				battle.killOffenderCreature((Creature)thing);
				info = "Defender killed creature " + thing.getName().toUpperCase();
				//TODO 
				Util.playSwordSound();
				//Util.playDeathSound();
				battle.setDefHits(battle.getDefHits()-1);
				if(battle.getDefHits()==0){
					view.getDefGrid().setDisable(false);
					view.getOffGrid().setDisable(true);
					switchPlayers(); // to defenders turn
				}
			}
			
			// Update view
			view.setBattle(battle);
			
			// Winning Conditions
			if(battle.isDefenderEliminated() || battle.isOffenderEliminated()){
				view.getDefGrid().setDisable(true);
				view.getOffGrid().setDisable(true);
				battle.setBattlePhase(BattlePhase.POSTCOMBAT);
				postCombatPhase();
				return;
			}
		
			view.refreshView((isDefender() ? "Defender" : "Offender") + " must apply opponent's hits to creatures", info);

		}else{
			Util.log("cannot apply hits");
		}
	}
	
	private void handleRoll() {

		int hitPts = 0; 
		String info = "";
		
		if (!isDefender()) { // Attacker's turn to roll.
					
			if(currentCreature.isCharge()){  // charging creature gets two die
				int[] diceRolls =offenderDice.rollTwo();
				if(diceRolls[0] <=currentCreature.getCombatVal()){	
					hitPts++;
				}
				if(diceRolls[1] <=currentCreature.getCombatVal()){	
					hitPts++;
				}
				info = "Offender rolled a " +diceRolls[0] + " and a "+diceRolls[1]+" for "+ currentCreature.getName().toUpperCase() + " and gained "+hitPts+" hit point(s)";
			}else{
				int roll = offenderDice.rollOne();
				if(roll<=currentCreature.getCombatVal()){	
					hitPts++;
				}
				info = "Offender rolled " +roll + " for "+ currentCreature.getName().toUpperCase() + " and gained "+hitPts+" hit point(s)";
			}
			battle.setOffHits(battle.getOffHits()+hitPts);
			
			// Attacker is done, now defender's turn
			if (!creatureIt.hasNext()) {	
				if(battle.getDefenderCreaturesForPhase().isEmpty()){
					nextBattlePhase();
					return;
				}else{ // Swap iterator to defender's creatures.
					switchPlayers();
					creatureIt = battle.getDefenderCreaturesForPhase().iterator();
					currentCreature = creatureIt.next();
					battle.setCurrentPlayer(battle.getDefender());
				}
				offenderDice.getView().hideDie2(true);
			}else{
				currentCreature = creatureIt.next();
				if(currentCreature.isCharge()){
					offenderDice.getView().hideDie2(false);
				}else{
					offenderDice.getView().hideDie2(true);
				}
			}
		}
		else { // Defender's turn to roll.
			
			if(currentCreature.isCharge()){ // charging creature gets two die
				int[] diceRolls =defenderDice.rollTwo();
				if(diceRolls[0] <=currentCreature.getCombatVal()){	
					hitPts++;
				}
				if(diceRolls[1] <=currentCreature.getCombatVal()){	
					hitPts++;
				}
				info = "Offender rolled a " +diceRolls[0] + " and a "+diceRolls[1]+" for "+ currentCreature.getName().toUpperCase() + " and gained "+hitPts+" hit point(s)";
			}else{
				int roll = defenderDice.rollOne();
				if(roll<=currentCreature.getCombatVal()){	
					hitPts++;
				}
				info = "Offender rolled " +roll + " for "+ currentCreature.getName().toUpperCase() + " and gained "+hitPts+" hit point(s)";
			}
			battle.setDefHits(battle.getDefHits()+hitPts);
			
			// Defender is done, apply hits, switch phase and reset iterator.
			if (!creatureIt.hasNext()) {
				switchPlayers();
				nextBattlePhase();
				view.setInfoLbl(info);
				defenderDice.getView().hideDie2(true);
				return;
			}else{
				currentCreature = creatureIt.next();
				if(currentCreature.isCharge()){
					defenderDice.getView().hideDie2(false);
				}else{
					defenderDice.getView().hideDie2(true);
				}
			}
		}
		
		view.refreshView((isDefender() ? "Defender" : "Offender") + " must roll for Creature "+ currentCreature.getName().toUpperCase(), info);
		enableControlsForRoll();
	}
}
