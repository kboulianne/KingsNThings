package com.presenter;

import com.game.phase.GamePlay;
import com.main.KNTAppFactory;
import com.main.NetworkedMain;
import com.model.Die;
import com.model.Game;
import com.server.services.IGameService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.view.DiceView;

/**
 * Presenter encapsulating logic for the two dice and roll action.
 *
 * @author kurtis
 */
public class DicePresenter {

	private final DiceView view;
	//TODO: Will need this for battle.
	private final IGameService gameSvc;
	
	private Die die1;
	private Die die2;
	
	public DicePresenter(DiceView view, IGameService gameSvc) {
		this.view = view;
		this.gameSvc = gameSvc;
	}

	//TODO: Restructure this so that we can reuse the existing instance.
	public DicePresenter(DiceView view, Die d1, Die d2) {
		this(view, null);
		
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
		die1.roll();
		
		view.setDice(die1, die2);
		
		KNTAppFactory.getGamePresenter().updateViews();
		
		return die1.getValue();
	}
	
	int[] rollTwo() {
		Util.playDiceRollSound();
		die1.roll();
		
		view.setDice(die1, die2);
		
		KNTAppFactory.getGamePresenter().updateViews();
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
	
//		// FIXME: Will cause problems for battle.
//		Game game = null;
//		try {
//			game = gameSvc.refreshGame(NetworkedMain.getRoomName());
//
//			die1 = game.getDie1();
//			die2 = game.getDie2();
//	
//			
//			die1.roll();
//			die2.roll();
//		
//			
//			// Update the server instance
//			//TODO: add update dice for efficiency, low priority.
//			gameSvc.updateGame(NetworkedMain.getRoomName(), game);
//		} catch (JSONRPC2Error e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Game local = KNTAppFactory.getGamePresenter().getLocalInstance();
		
		die1 = local.getDie1();
		die2 = local.getDie2();
		
		die1.roll();
		die2.roll();
		
		// Update the view
		view.setDice(die1, die2);
		
		// Update GameView
//		KNTAppFactory.getGamePresenter().updateViews();
		
		return die1.getValue() + die2.getValue();
	}
	
//	/**
//	 * For now, ends the player's turn.
//	 */
//	public void endTurn() {
//		KNTAppFactory.getGamePlay().endTurn();
//	}
}
