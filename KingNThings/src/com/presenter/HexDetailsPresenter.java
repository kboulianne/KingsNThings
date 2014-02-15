package com.presenter;

import com.main.KNTAppFactory;
import com.model.Hex;
import com.model.Thing;
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
		this.view.setPresenter(this);
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
	public void showHex(Hex h) {
		view.setHex(h);
	}

	/**
	 * A thing was clicked in an ArmyOrMisc control.
	 * @param t 
	 */
	public void handleThingClick(Thing t) {
		// Show ThingDetailView in SidePane.
		t.setSelected(!t.isSelected());
		KNTAppFactory.getSidePanePresenter().showThingDetailsFor(t);
	}
}
