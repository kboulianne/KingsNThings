package com.presenter;

import javafx.scene.input.MouseEvent;

import com.game.phase.GamePlay;
import com.main.KNTAppFactory;
import com.main.NetworkedMain;
import com.model.Die;
import com.model.Game;
import com.server.services.IGameService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.util.Util;
import com.view.DiceView;

/**
 * Presenter encapsulating logic for the two dice and roll action.
 *
 * @author kurtis
 */
public class DicePresenter {

	private final DiceView view;
	
	private Die die1;
	private Die die2;
	
	private boolean diceSet = false;
	
	public DicePresenter(DiceView view) {
		this.view = view;
	}

	public DicePresenter(DiceView view, Die d1, Die d2) {
		this(view);
		
		// Use the specified dice as the model
		die1 = d1;
		die2 = d2;
	}
	
	/**
	 * Gets the view being managed by this presenter.
	 *
	 * @return The DiceView.
	 */
	public DiceView getView() {
		return view;
	}
	
	int rollOne() {
		Util.playDiceRollSound();
		if (!diceSet) {
			die1.roll();
		}
		else {
			diceSet = false;
		}
		
		view.setDice(die1, die2);
		
		return die1.getValue();
	}
	
	int[] rollTwo() {
		Util.playDiceRollSound();
		if (!diceSet) {
			die1.roll();
		}
		else {
			diceSet = false;
		}
		
		view.setDice(die1, die2);
		
//		KNTAppFactory.getGamePresenter().updateViews();
		int[] dieRolls = new int[2];
		dieRolls[0] = die1.getValue();
		dieRolls[1] = die2.getValue();
		
		return dieRolls;
	}
	
	/**
	 * The logic to execute when the players presses the roll button.
	 */
	public int roll() {
		Util.playDiceRollSound();
		
		// When using dice in game.
		if (die1 == null || die2 == null) {
			Game local = KNTAppFactory.getGamePresenter().getLocalInstance();
		
			die1 = local.getDie1();
			die2 = local.getDie2();
		}
		
		if (!diceSet) {
			die1.roll();
			die2.roll();
		}
		else {
			diceSet = false;
		}
		
		// Update the view
		view.setDice(die1, die2);
		
		int value = die1.getValue() + die2.getValue(); 
		
		// Dirty fix but ok for now
		die1 = null;
		die2 = null;
		
		return value;
	}

	public void handleMouseClick(MouseEvent me, boolean isDie1) {
		// When using dice in game.
		if (die1 == null || die2 == null) {
			Game local = KNTAppFactory.getGamePresenter().getLocalInstance();
		
			die1 = local.getDie1();
			die2 = local.getDie2();
		}
		
		Die d = (isDie1 ? die1 : die2);
		if (me.isPrimaryButtonDown()) {
			int val = d.getValue() + 1;
			if (val > 6) val = 1;
			d.setValue(val);
		}
		else {
			int val = d.getValue() - 1;
			if (val < 1) val = 6;
			d.setValue(val);
		}
		
		diceSet = true;
		
		view.setDice(die1, die2);
	}
}
