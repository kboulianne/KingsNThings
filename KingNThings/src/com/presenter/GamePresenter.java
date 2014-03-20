package com.presenter;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.main.NetworkedMain;
import com.model.Player;
import com.model.game.Game;
import com.model.game.phase.GamePlay;
import com.server.services.IGameService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.view.GameView;
import static com.main.KNTAppFactory.*;
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
	private IGameService gameSvc;
	
	public GamePresenter( final GameView view, IGameService gameSvc) {
		this.view = view;
		this.gameSvc = gameSvc;
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
	void updateViews() {
		try {
			Game game = gameSvc.refreshGame(NetworkedMain.getRoomName());
			
			// Update subviews
			getDicePresenter().getView().setDice(game.getDie1(), game.getDie2());
			getSidePanePresenter().getView().setOpponents(game.getOpponentsFor(NetworkedMain.getPlayer()));
			getBoardPresenter().getView().setBoard(game.getBoard());
			getPlayerInfoPresenter().getView().setPlayer(NetworkedMain.getPlayer());
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Shows the contents of the cup as a popup.
	 */
	void showCup() {

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
	void showPlayerInfoPopup(Player p) {
		KNTAppFactory.getPopupPresenter().showPlayerPopup(p);
	}

	/**
	 * Dismisses the popup currently displayed by the PopupPresenter.
	 */
	void dismissPopup() {
		KNTAppFactory.getPopupPresenter().dismissPopup();
	}

	public void showGameUI() {
		NetworkedMain.setView(view, 1280, 800);
		
		updateViews();
		// TODO: MOVE ME
		startGame();
	}
	
	/**
	 * Signals start of phase. Should only be called once.
	 */
	public void startGame() {
		// TODO Keep track of whether this was called?
		// Triggers the First phase
		KNTAppFactory.getGamePlay().start();
	}
}
