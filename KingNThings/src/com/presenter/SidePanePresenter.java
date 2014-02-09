package com.presenter;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Hex;
import com.model.Player;
import com.model.Thing;
import com.model.game.Game;
import com.view.SidePaneView;

/**
 * Handles Opponent view, and any <ViewName>DetailView that can be displayed in the pane.
 * 
 * @author kurtis
 */
public class SidePanePresenter {

	/** The view managed by this presenter. */
	private final SidePaneView view;

	public SidePanePresenter(SidePaneView view) {
		this.view = view;
		this.view.setPresenter(this);

		// Update the opponent list.
		Game game = GameService.getInstance().getGame();
		view.setOpponents(game.getOpponent1(), game.getOpponent2(), game.getOpponent3());
	}

	/**
	 * Gets the view managed by this presenter.
	 * @return 
	 */
	public SidePaneView getView() {
		return view;
	}

	/**
	 * Shows the details for the Hex selected in the BoardPresenter.
	 * @param h  The hex to display.
	 */
	public void showHexDetailsFor(Hex h) {
		view.showHexDetailsView(KNTAppFactory.getHexDetailsPresenter().getView());

		// make the presenter update the view
		KNTAppFactory.getHexDetailsPresenter().showHex(h);	
	}

	/**
	 * Shows the details for the Thing selected in the player rack, ArmyOrMisc etc...
	 * @param t The Thing to display.
	 */
	public void showThingDetailsFor(Thing t) {
		view.showThingDetailsView(KNTAppFactory.getThingDetailsPresenter().getViewFor(t));

		// Make the presenter update the UI
		KNTAppFactory.getThingDetailsPresenter().showThing(t);
	}

	/**
	 * Shows the PlayerInfo as a popup for the specified player.
	 * @param player The player information to display in the popup.
	 */
	public void showOpponentInfo(Player player) {
		KNTAppFactory.getGamePresenter().showPlayerInfoPopup(player);
	}

	/**
	 * Dismisses the Opponent Info popup being displayed.
	 */
	public void dismissOpponentInfo() {
		KNTAppFactory.getGamePresenter().dismissPopup();
	}
}
