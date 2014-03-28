package com.presenter;

import com.main.KNTAppFactory;
import com.model.Game;
import com.model.Hex;
import com.view.HexDetailsView;

/**
 * This presenter handles the details view contained in the side pane.
 * @author kurtis
 */
public class HexDetailsPresenter {

	/** The view managed by this presenter. */
	private HexDetailsView view;

	public HexDetailsPresenter(HexDetailsView view) {
		this.view = view;
	}

	/**
	 * Gets the view managed by this Presenter.
	 * @return The view
	 */
	public HexDetailsView getView() {
		return view;
	}
	
	/**
	 * Shows the selected hex in the DetailsView.
	 * @param h 
	 */
	void showHex(Hex h) {
		Game game = KNTAppFactory.getGamePresenter().getLocalInstance();
		view.setHex(h, game.getCurrentPlayer(), game.getOpponentsForCurrent());
	}

}
