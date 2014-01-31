/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.presenter;

import com.game.services.GameService;
import com.model.Player;
import com.model.game.Game;
import com.model.game.phase.GamePlay;
import com.view.GameView;
import com.view.ThingEvent;
import javafx.event.EventHandler;

/**
 * OPTION 1 of: http://www.zenjava.com/2011/12/11/javafx-and-mvp-a-smorgasbord-of-design-patterns/
 *
 * Logic stuff for the game goes here. Sub-views have their own presenters to encapsulate their logic.
 *
 * @author kurtis
 */
public class GamePresenter {

	private final GameView view;
	// Singleton for now, not needed here
//    private GameService gameService;
	private SidePanePresenter sidePanePresenter;
	private PopupPresenter popupPresenter;
	
	private DicePresenter dicePresenter;

	
	public GamePresenter( final GameView view) {
		this.view = view;
		this.view.setPresenter(this);

		// Update view
		updateView();
//		Game model = GameService.getInstance().getGame();
//		view.setGame(model);

	}

	public void setDependencies(
			final DicePresenter dicePresenter,
			final SidePanePresenter sidePanePresenter,
			final BoardPresenter boardPresenter,
			final PlayerInfoPresenter playerInfoPresenter,
			final PopupPresenter popupPresenter) {
		
		// DicePresenter
		this.dicePresenter = dicePresenter;
		this.view.addDiceView(dicePresenter.getView());
		
		// SidePanePresenter
		this.sidePanePresenter = sidePanePresenter;
		this.view.addSidePaneView(sidePanePresenter.getView());
		
		// BoardPresenter
		this.view.addBoardView(boardPresenter.getView());
		
		// PlayerInfoView
		this.view.addPlayerInfoView(playerInfoPresenter.getView());
		
		// PopupPresenter
		this.popupPresenter = popupPresenter;
		this.popupPresenter.getView().setParent(view);
	}
	
	/**
	 * Gets the view managed by this presenter.
	 *
	 * @return
	 */
	public GameView getView() {
		return view;
	}


	// UI Logic stuff is done here. Access service for model.

	public void updateView() {
		view.setGame(GameService.getInstance().getGame());
	}
	
	public void showCup() {

		// Get the cup content
		Game game = GameService.getInstance().getGame();

		// For now until can find a cleaner way.
		EventHandler<ThingEvent> handler = new EventHandler<ThingEvent>() {

			@Override
			public void handle(ThingEvent t) {
				// Dispatch to ThingDetailsView
				sidePanePresenter.showThingDetailsFor(t.getThing());
				popupPresenter.dismissPopup();
			}
		};

		popupPresenter.showCupPopup(game.getCup().getListOfThings(), "Cup", handler);
	}

	public void showPlayerInfoPopup(Player p) {
		popupPresenter.showPlayerPopup(p);
	}

	public void dismissPopup() {

		popupPresenter.dismissPopup();
	}

	public void startGame() {
		// Triggers the First phase
		GamePlay.getInstance().start();
	}


	public DicePresenter getDicePresenter() {
		return dicePresenter;
	}


}
