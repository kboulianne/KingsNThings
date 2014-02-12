package com.presenter;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Die;
import com.model.game.Game;
import com.model.game.phase.GamePlay;
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
	
	public DicePresenter(DiceView view) {
		this.view = view;
		this.view.setPresenter(this);

		// Set initial model
		Game game = GameService.getInstance().getGame();

		// Defaults to dice in game
		die1 = game.getDie1();
		die2 = game.getDie2();
		
		view.setDice(game.getDie1(), game.getDie2());
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
	
	/**
	 * The logic to execute when the players presses the roll button.
	 */
	public int roll() {
//		GameService.getInstance().roll();

		
//		Game game = GameService.getInstance().getGame();

		die1.roll();
		die2.roll();
		
		// Update the view
		view.setDice(die1, die2);
		
		// Update GameView
		KNTAppFactory.getGamePresenter().updateView();
		
		return die1.getValue() + die2.getValue();
	}
	
	/**
	 * For now, ends the player's turn.
	 */
	public void endTurn() {
		GamePlay.getInstance().endTurn();
	}
}
