package com.presenter;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Player;
import com.model.game.Game;
import com.model.game.phase.GamePlay;
import com.view.GameView;

//import javafx.event.EventHandler;

/**
 * OPTION 1 of: http://www.zenjava.com/2011/12/11/javafx-and-mvp-a-smorgasbord-of-design-patterns/
 *
 * Logic stuff for the game goes here. Sub-views have their own presenters to encapsulate their logic.
 *
 * @author kurtis
 */
public class GamePresenter {

	private final GameView view;
	
	public GamePresenter( final GameView view) {
		this.view = view;
		this.view.setPresenter(this);
		
		// Update view
		updateView();

	}

	/**
	 * Initial setup for this presenter. Adds all necessary sub-views to the GameView.
	 */
	public void setupSubViews() {
		
		// DiceView
		this.view.addDiceView(KNTAppFactory.getDicePresenter().getView());
		
		// SidePaneView
		this.view.addSidePaneView(KNTAppFactory.getSidePanePresenter().getView());
		
		// BoardView
		this.view.addBoardView(KNTAppFactory.getBoardPresenter().getView());
		
		// PlayerInfoView
		this.view.addPlayerInfoView(KNTAppFactory.getPlayerInfoPresenter().getView());
		
		// PopupView
		KNTAppFactory.getPopupPresenter().getView().setParent(view);
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

	/**
	 * Refreshes the view so that it displays current game state.
	 */
	public void updateView() {
		view.setGame(GameService.getInstance().getGame());
	}
	
	/**
	 * Shows the contents of the cup as a popup.
	 */
	public void showCup() {

		// Get the cup content
		Game game = GameService.getInstance().getGame();

		// For now until can find a cleaner way.
		/*EventHandler<ThingEvent> handler = new EventHandler<ThingEvent>() {

			@Override
			public void handle(ThingEvent t) {
				// Dispatch to ThingDetailsView
				KNTAppFactory.getSidePanePresenter().showThingDetailsFor(t.getThing());
				KNTAppFactory.getPopupPresenter().dismissPopup();
			}
		};*/
		KNTAppFactory.getPopupPresenter().showCupPopup(game.getCup());
	}

	/**
	 * Shows player information when the mouse is hovered over a player name.
	 * @param p 
	 */
	public void showPlayerInfoPopup(Player p) {
		KNTAppFactory.getPopupPresenter().showPlayerPopup(p);
	}

	/**
	 * Dismisses the popup currently displayed by the PopupPresenter.
	 */
	public void dismissPopup() {
		KNTAppFactory.getPopupPresenter().dismissPopup();
	}

	/**
	 * Signals start of phase. Should only be called once.
	 */
	public void startGame() {
		// TODO Keep track of whether this was called?
		// Triggers the First phase
		GamePlay.getInstance().start();
	}
}
