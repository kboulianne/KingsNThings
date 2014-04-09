package com.presenter;

import com.main.KNTAppFactory;
import com.main.NetworkedMain;
import com.model.Game;
import com.model.Thing;
import com.model.Treasure;
import com.util.Util;
import com.view.PlayerInfoView;
import com.view.customcontrols.ThingView;

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
		KNTAppFactory.getGamePresenter().showPlayerInfoPopup(NetworkedMain.getPlayer());
	}

	/**
	 * Dismisses the PlayerInfo Pop up.
	 */
	public void dismissPopup() {
		KNTAppFactory.getGamePresenter().dismissPopup();
	}

	public void handleExchangeThings(ThingView v, Thing thing) {
		if(thing.isSelected()) {
			KNTAppFactory.getSidePanePresenter().getView().showArbitraryView("Exchange things by clicking the rack\n"
				   + "     Exchange only once per thing", Game.CROWN_IMAGE);
		} else {
			Util.playClickSound();
			
			Game game = KNTAppFactory.getGamePresenter().getLocalInstance();
			//TODO: This could become an RPC call on the server
			Thing t = game.getCup().getRandomThing();
			game.moveThingFromCupToPlayer(t, game.getCurrentPlayer());
			KNTAppFactory.getSidePanePresenter().showThingDetailsFor(t);
			game.getCurrentPlayer().getBlock().removeThing(thing);
			game.getCup().addThing(thing);
			thing = t;
			thing.setSelected(true);
			thing.setFacedDown(false);
			v.refreshView();
		}
	}

	public void handleExchangeTreasures(ThingView thingView, Thing thing) {
		Util.playClickSound();
		Game game = KNTAppFactory.getGamePresenter().getLocalInstance();	

		game.getCurrentPlayer().getBlock().removeThing(thing);
		game.getCup().addThing(thing);
		
		game.getCurrentPlayer().addGold(((Treasure) thing).getValue());
		KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(game.getCurrentPlayer());
		thingView.refreshView();	
	}
}
