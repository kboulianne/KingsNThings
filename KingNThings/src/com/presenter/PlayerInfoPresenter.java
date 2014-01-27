/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.presenter;

import com.game.services.GameService;
import com.model.Player;
import com.model.game.Game;
import com.view.PlayerInfoView;
import com.view.ThingEvent;

/**
 *
 * @author kurtis
 */
public class PlayerInfoPresenter {

	private PlayerInfoView view;
	private SidePanePresenter sidePanePresenter;
	private GamePresenter mainPresenter;

	public PlayerInfoPresenter(PlayerInfoView view) {
		this.view = view;
		this.view.setPresenter(this);

		Game game = GameService.getInstance().getGame();
		view.setPlayer(game.getCurrentPlayer());
	}

	public PlayerInfoView getView() {
		return view;
	}

	public void setDependencies(SidePanePresenter sidePanePresenter, GamePresenter mainPresenter) {
		this.sidePanePresenter = sidePanePresenter;
		this.mainPresenter = mainPresenter;
	}
	
//	// Avoids infinite recursion in Factory. To be fixed later.
//	public void setGamePresenter(final GamePresenter presenter) {
//		if (presenter == null) {
//			throw new NullPointerException("Presenter cannot be null.");
//		}
//		if (mainPresenter != null) {
//			throw new IllegalStateException("Presenter has already been set.");
//		}
//
//		mainPresenter = presenter;
//	}

    //TODO ViewCup should be managed by GamePresenter
	public void handleRackClick(ThingEvent t) {

		// Show thing in detailsview
		sidePanePresenter.showThingDetailsFor(t.getThing());
	}

	public void handleCupClick() {
		// Delegate to the GamePresenter
		mainPresenter.showCup();
	}

	public void showCurrentPlayerInfo() {
		Player p = GameService.getInstance().getGame().getCurrentPlayer();
		mainPresenter.showPlayerInfoPopup(p);
	}

	public void dismissCurrentPlayerInfo() {
		mainPresenter.dismissPopup();
	}
}
