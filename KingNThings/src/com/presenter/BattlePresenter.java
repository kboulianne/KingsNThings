package com.presenter;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import com.main.KNTAppFactory;
import com.model.Battle;
import com.model.Battle.BattlePhase;
import com.model.Creature;
import com.model.Die;
import com.view.BattleView;
import com.view.DiceView;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BattlePresenter {

	private BattleView view;

	private Battle battle;
	// TODO assume 1st available creature for DEMO
//	Creature selectedCreature;

	private DicePresenter attackerDice;
	private DicePresenter defenderDice;

//	private Map<Creature
	private Iterator<Creature> creatureIt;
	
	private int attackerRoll;
	private int defenderRoll;
	
	/** Determines whether the attacker or defender is rolling. */
	boolean isDefender;

	public BattlePresenter(BattleView v, DicePresenter dp1, DicePresenter dp2) {
		view = v;
		view.setPresenter(this);

		attackerDice = dp1;
		defenderDice = dp2;
	}

	public BattleView getView() {
		return view;
	}

	// use this to start battle
	public void startBattle(Battle b) {
//		battle = view.getBattle(); // called more than once
		// Display the battle in the view
		battle = b;
		view.setBattle(battle);

		// set handlers
		view.getOffRetreatBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				battle.setBattlePhase(BattlePhase.POSTCOMBAT);
				nextBattlePhase();
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
				nextBattlePhase();
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

		// reset lists used in magic melee and ranged - move later
//		if (isDefender) {
//			defCreatures = new ArrayList<>();
//			defCreatures.addAll(battle.getDefenderCreaturesForPhase());
//		} else {
//			offCreatures = new ArrayList<>();
//			offCreatures.addAll(battle.getOffenderCreatures());
//		}

		
		
		switch (battle.getBattlePhase()) {
			case MAGIC:
			case RANGED:
			case MELEE:
//				addRollHandlers();
				rollPhase();
				break;
			case RETREAT:
				retreatPhase();
				break;
			case POSTCOMBAT:
				postCombatPhase();
				break;
		}
	}
	
	
	private void handleRoll() {
		// Fetch the next creature and store roll. Assume 1-to-1 mapping for now,
		// att. c1 -> def. c1, att. c2 -> def.c2 ...
		
		creatureIt.next();
		
		if (!isDefender) {			
			attackerRoll = attackerDice.rollOne();
			System.out.println("Attacker rolled: " + attackerRoll + " for " + battle.getBattlePhase().phaseAsString + " phase");
			
			// Attacker is done, now defender's turn
			if (!creatureIt.hasNext()) {
				System.out.println("Defender's turn");
				isDefender = true;
				// Swap iterator to defender's creatures.
				creatureIt = battle.getDefenderCreaturesForPhase().iterator();
			}
		}
		else {
			defenderRoll = defenderDice.rollOne();
			System.out.println("Defender rolled: " + attackerRoll + " for " + battle.getBattlePhase().phaseAsString + " phase");
			
			
			// Defender is done, apply hits, switch phase and reset iterator.
			if (!creatureIt.hasNext()) {
				isDefender = false;
				// applyHits()
				nextBattlePhase();
				creatureIt = battle.getAttackerCreatures().iterator();
			}
		}
		
		
		

		
		// If we reach the end of the iterator, move to defender or switch phases.
//		if ()
		
		
	}
	
	// Roll Phases
	private void rollPhase() {
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
		
		// Initialize the iterator
		creatureIt = battle.getAttackerCreaturesForPhase().iterator();
//		defenderIt = battle.getDefenderCreaturesForPhase().iterator();
		
		// Determine which dicepresenter to choose
//		DicePresenter current = attackerDice;
//		DicePresenter other = defenderDice;
//		List<Creature> creatures = offCreatures;
		
		

//		// Current player is defender
//		if (isDefender) {
//			current = defenderDice;
//			other = attackerDice;
//			creatures = defCreatures;
//		}

//		rollPhaseHelper("Defender", creatures, current, view.getDefButtonBox(), other, view.getOffButtonBox());

		// Set roll handlers
		
	}

//	private void rollPhaseHelper(
//			String player, List<Creature> creatureList,
//			DicePresenter currentPlayerDV, HBox currentPlayersHBox,
//			DicePresenter otherPlayerDV, HBox otherPlayersHBox) {
//
//		selectedCreature = null;
//		selectedCreature = findNextCreature(creatureList, battle.getBattlePhase());
//
//		if (selectedCreature == null) {
//			nextBattlePhase();
//			return;
//		}
//		//update view
//		currentPlayerDV.getView().getRollBtn().setVisible(true);
//		currentPlayersHBox.setVisible(false);
////
//		// To have a visual on the other rolls, just hide the button
//		otherPlayerDV.getView().getRollBtn().setVisible(false);
//		otherPlayersHBox.setVisible(false);
//		view.refreshView(player + " must roll for " + battle.getBattlePhase().phaseAsString + " Creature: " + selectedCreature.getName());
//
//		rollButtonHandler(player, currentPlayerDV, creatureList);
//	}

//	private void rollButtonHandler(final String player, final DicePresenter dp, final List<Creature> creatureList) {
//		dp.getView().getRollBtn().setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent t) {
//				int rolledValue = dp.roll();
//				rollForCreature(selectedCreature, rolledValue);
//				selectedCreature = findNextCreature(creatureList, battle.getBattlePhase());
//				if (selectedCreature == null) {
//					nextBattlePhase();
//				} else {
//					view.refreshView(player + " must roll for " + battle.getBattlePhase().phaseAsString + " Creature: " + selectedCreature.getName());
//				}
//			}
//		});
//	}

	private Creature findNextCreature(List<Creature> creatureList, BattlePhase phase) {
		Creature creature = null;
		for (Creature c : creatureList) {
			if (phase == BattlePhase.MAGIC) {
				if (c.isMagic()) {
					creature = c;
					creatureList.remove(c);
					break;
				}
			} else if (phase == BattlePhase.RANGED) {
				if (c.isRanged() && !c.isMagic()) {
					creature = c;
					creatureList.remove(c);
					break;
				}
			} else if (phase == BattlePhase.MELEE) {
				if (!(c.isRanged() || c.isMagic())) {
					creature = c;
					creatureList.remove(c);
					break;
				}
			}
		}
		return creature;
	}

	private void rollForCreature(Creature c, int rolledValue) {
		if (c.isMagic()) {
			Util.log("TODO roll for magic creature " + rolledValue);
		} else if (c.isRanged() && !c.isMagic()) {
			Util.log("TODO roll for ranged creature " + rolledValue);
		} else if (!c.isRanged() || !c.isMagic()) { //melee
			Util.log("TODO roll for melee creature " + rolledValue);
		}
	}

	private void retreatPhase() {
		//update model
		//battle.setBattlePhase(BattlePhase.RETREAT);

		//update view
		attackerDice.getView().setVisible(false);
		defenderDice.getView().setVisible(false);
		if (isDefender) {
			view.getOffButtonBox().setVisible(false);
			view.getDefButtonBox().setVisible(true);
		} else {
			view.getOffButtonBox().setVisible(true);
			view.getDefButtonBox().setVisible(false);
		}

		view.refreshView((isDefender ? "Defender" : "Offender") + " must choose to retreat or to continue with battle");
	}

	private void postCombatPhase() {
		//update model
		//battle.setBattlePhase(BattlePhase.POSTCOMBAT);

		if (isDefender) {
			view.getDefButtonBox().setVisible(true);
			view.getOffContinueBtn().setVisible(false);
			view.getDefContinueBtn().setVisible(true);
			view.getDefContinueBtn().setText("Finish Battle");
			view.getDefContinueBtn().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					KNTAppFactory.getPopupPresenter().dismissPopup();
				}
			});
		} else {
			view.getOffButtonBox().setVisible(true);
			view.getDefContinueBtn().setVisible(false);
			view.getOffContinueBtn().setVisible(true);
			view.getOffContinueBtn().setText("Finish Battle");
			view.getOffContinueBtn().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					KNTAppFactory.getPopupPresenter().dismissPopup();
				}
			});
		}
		view.getOffRetreatBtn().setVisible(false);
		view.getDefRetreatBtn().setVisible(false);
		view.refreshView("Post Combat - " + (isDefender ? "Offender" : "Defender") + " retreated from battle");
	}

	protected void nextRound() {
		battle.setRoundNumber(battle.getRoundNumber() + 1);
		battle.setBattlePhase(BattlePhase.MAGIC);
	}

	protected void switchToNextPhase() {
//		battle.setCurrentPlayer(battle.getOffender());
		
		if (battle.getBattlePhase() == BattlePhase.RETREAT) {
			nextRound();
		} 
		else if (battle.getBattlePhase() == BattlePhase.POSTCOMBAT) { 
			/*do nothing*/ 
		}	
		else {
			// Cool trick didn't know that! :-)
			battle.setBattlePhase(BattlePhase.values()[battle.getBattlePhase().ordinal() + 1]);
		}
	}

	private void nextBattlePhase() {
		System.out.println("Next battle phase - attacker's turn.");
//		if (battle.getCurrentPlayer() == null) {
//			isDefender = false;
//			switchToNextPhase();
//		} else if (battle.getCurrentPlayer().equals(battle.getOffender())) {
//			isDefender = true;
//			battle.setCurrentPlayer(battle.getDefender());
//		} else {
//			isDefender = false;
//			switchToNextPhase();
//		}
		switchToNextPhase();
		
		startPhase();
	}
}
