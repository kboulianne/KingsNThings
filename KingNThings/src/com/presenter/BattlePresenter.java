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
import java.util.List;

public class BattlePresenter {

	private BattleView view;

	Battle battle;
	Creature selectedCreature;

	private DicePresenter attackerDice;
	private DicePresenter defenderDice;

	ArrayList<Creature> offCreatures;
	ArrayList<Creature> defCreatures;

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
		if (isDefender) {
			defCreatures = new ArrayList<>();
			defCreatures.addAll(battle.getDefenderCreatures());
		} else {
			offCreatures = new ArrayList<>();
			offCreatures.addAll(battle.getOffenderCreatures());
		}

		switch (battle.getBattlePhase()) {
			case MAGIC:
			case RANGED:
			case MELEE:
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

	// Roll Phases
	private void rollPhase() {
		// Determine which dicepresenter to choose
		DicePresenter current = attackerDice;
		DicePresenter other = defenderDice;
		List<Creature> creatures = offCreatures;
		
		// Current player is defender
		if (isDefender) {
			current = defenderDice;
			other = attackerDice;
			creatures = defCreatures;
		}
		
		rollPhaseHelper("Defender", creatures, current, view.getDefButtonBox(), other, view.getOffButtonBox());

	}

	private void rollPhaseHelper(
			String player, List<Creature> creatureList,
			DicePresenter currentPlayerDV, HBox currentPlayersHBox, 
			DicePresenter otherPlayerDV, HBox otherPlayersHBox) {

		selectedCreature = null;
		selectedCreature = findNextCreature(creatureList, battle.getBattlePhase());

		if (selectedCreature == null) {
			nextBattlePhase();
			return;
		}
		//update view
		currentPlayerDV.getView().setVisible(true);
		currentPlayersHBox.setVisible(false);

		otherPlayerDV.getView().setVisible(false);
		otherPlayersHBox.setVisible(false);
		view.refreshView(player + " must roll for " + battle.getBattlePhase().phaseAsString + " Creature: " + selectedCreature.getName());
		
		handleRollBtn(player, currentPlayerDV, creatureList);
	}

	private void handleRollBtn(final String player, final DicePresenter dp, final List<Creature> creatureList) {
		dp.getView().getRollBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				int rolledValue = dp.roll();
				rollForCreature(selectedCreature, rolledValue);
				selectedCreature = findNextCreature(creatureList, battle.getBattlePhase());
				if (selectedCreature == null) {
					nextBattlePhase();
				} else {
					view.refreshView(player + " must roll for " + battle.getBattlePhase().phaseAsString + " Creature: " + selectedCreature.getName());
				}
			}
		});
	}

	private Creature findNextCreature(List<Creature> creatureList, BattlePhase phase) {
		Creature creature = null;
		for (Creature c : creatureList) {
			if (phase == BattlePhase.MAGIC) {
				if (c.getMagic()) {
					creature = c;
					creatureList.remove(c);
					break;
				}
			} else if (phase == BattlePhase.RANGED) {
				if (c.getRanged() && !c.getMagic()) {
					creature = c;
					creatureList.remove(c);
					break;
				}
			} else if (phase == BattlePhase.MELEE) {
				if (!(c.getRanged() || c.getMagic())) {
					creature = c;
					creatureList.remove(c);
					break;
				}
			}
		}
		return creature;
	}

	private void rollForCreature(Creature c, int rolledValue) {
		if (c.getMagic()) {
			Util.log("TODO roll for magic creature " + rolledValue);
		} else if (c.getRanged() && !c.getMagic()) {
			Util.log("TODO roll for ranged creature " + rolledValue);
		} else if (!c.getRanged() || !c.getMagic()) { //melee
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
		battle.setCurrentPlayer(battle.getOffender());
		if (battle.getBattlePhase() == BattlePhase.RETREAT) {
			nextRound();
		} else if (battle.getBattlePhase() == BattlePhase.POSTCOMBAT) { /*do nothing*/ } else {
			battle.setBattlePhase(BattlePhase.values()[battle.getBattlePhase().ordinal() + 1]);
		}
	}

	private void nextBattlePhase() {
		if (battle.getCurrentPlayer() == null) {
			isDefender = false;
			switchToNextPhase();
		} else if (battle.getCurrentPlayer().equals(battle.getOffender())) {
			isDefender = true;
			battle.setCurrentPlayer(battle.getDefender());
		} else {
			isDefender = false;
			switchToNextPhase();
		}
		startPhase();
	}
}
