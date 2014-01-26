package com.presenter;

import com.game.services.GameService;
import com.model.game.Game;
import com.view.DiceView;

/**
 * Presenter encapsulating logic for the two dice and roll action.
 *
 * @author kurtis
 */
public class DicePresenter {

	private final DiceView view;
    // In case we need to do something else in the main screen.
	//  private GamePresenter mainPresenter;

	public DicePresenter(DiceView view, GamePresenter mainPresenter) {
		this.view = view;
		this.view.setPresenter(this);
//	this.mainPresenter = mainPresenter;

		// Set initial model
		Game game = GameService.getInstance().getGame();

		view.setDice(game.getDie1(), game.getDie2());
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
	public void roll() {
		//TODO Currently possible to do game.rollDice(), bypassing the service.
		GameService.getInstance().roll();

		Game game = GameService.getInstance().getGame();

		// Update the view
		view.setDice(game.getDie1(), game.getDie2());
	}
}
