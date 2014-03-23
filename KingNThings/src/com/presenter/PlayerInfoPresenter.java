package com.presenter;

import com.main.KNTAppFactory;
import com.model.Game;
import com.model.Player;
import com.view.PlayerInfoView;

/**
 * Handles logic related to the player's name and rack.
 * 
 * @author kurtis
 */
public class PlayerInfoPresenter {

	/** The view managed by this presenter. */
	private final PlayerInfoView view;

	public PlayerInfoPresenter(PlayerInfoView view) {
		this.view = view;
		this.view.setPresenter(this);

		// Update the ui.
//		Game game = GameService.getInstance().getGame();
//		view.setPlayer(game.getCurrentPlayer());
	}

	/**
	 * Gets the view managed by this presenter.
	 * @return The view.
	 */
	public PlayerInfoView getView() {
		return view;
	}

// TODO Remove unused code found by UCDetector
// 	/**
// 	 * A Thing was clicked in the player's rack.
// 	 * @param t The Thing instance that was clicked.
// 	 */
// 	public void handleRackClick(Thing t) {
// 		// Show thing in detailsview
// 		KNTAppFactory.getSidePanePresenter().showThingDetailsFor(t);
// 	}

	/**
	 *	The view cup button was clicked. Displays the contents of the cup.
	 */
	public void handleCupClick() {
		// Delegate to the GamePresenter
		KNTAppFactory.getGamePresenter().showCup();
	}

	/**
	 * Pops up the information for the current player.
	 */
	public void showCurrentPlayerInfo() {
		throw new IllegalAccessError("Use new service.");
//		Player p = GameService.getInstance().getGame().getCurrentPlayer();
//		KNTAppFactory.getGamePresenter().showPlayerInfoPopup(p);
	}

	/**
	 * Dismisses the PlayerInfo Pop up.
	 */
	public void dismissPopup() {
		KNTAppFactory.getGamePresenter().dismissPopup();
	}
}
