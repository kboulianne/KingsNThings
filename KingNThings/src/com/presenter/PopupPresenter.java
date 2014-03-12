package com.presenter;

import com.main.KNTAppFactory;
import com.model.Cup;
import com.model.Player;
import com.view.popups.PlayerPopup;
import com.view.PopupView;
import com.view.popups.CupPopup;

/**
 * Handles popup views displayed in the GamePresenter.
 * @author kurtis
 */
public class PopupPresenter {

	/** The view managed by this presenter. */
	private final PopupView view;

	// All the views (Pane classes) this popup can display.
	public PopupPresenter(PopupView view) {
		this.view = view;
		this.view.setPresenter(this);
	}

	/**
	 * Gets the view managed by this presenter.
	 * @return The View
	 */
	public PopupView getView() {
		return view;
	}

	/**
	 * Dismisses the popup currently being displayed by the view.
	 */
	public void dismissPopup() {
		view.dismiss();
	}

	/**
	 * Shows a popup for the specified player.
	 * @param p The player to display in the popup.
	 */
	public void showPlayerPopup(Player p) {
		view.show(new PlayerPopup(p));
	}

	/**
	 * Shows the cup popup with the specified list of Things.
	 * @param things The list of things to display
	 * @param title The popup title
	 * @param event The EventHandler linked to the tile click.
	 */
	public void showCupPopup(Cup cup) {
		view.show(new CupPopup(cup));
	}
	
	public void showBattlePopup() {
		view.show(KNTAppFactory.getBattlePresenter().getView());
	}
}