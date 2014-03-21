package com.presenter;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.main.NetworkedMain;
import com.model.Game;
import com.model.GameRoom;
import com.model.Player;
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
	private final Timer updateTimer = new Timer();
	Service<GameRoom> service = new Service<GameRoom>() {
		@Override
		protected Task<GameRoom> createTask() {
			return new Task<GameRoom>() {	
				@Override
				protected GameRoom call() throws Exception {
					updateTimer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							Platform.runLater(new Runnable() {
								
								@Override
								public void run() {
									monitor();
								}
							});
							
						}
					}, 1000, 5000);
					return null;
				}
			};
		}
	};
	
	
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
	public void updateViews() {
		try {
			Game game = gameSvc.refreshGame(NetworkedMain.getRoomName());
			updateViews(game);

		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateViews(Game game) {
		// Update subviews
		getDicePresenter().getView().setDice(game.getDie1(), game.getDie2());
		getSidePanePresenter().getView().setOpponents(game.getOpponentsFor(NetworkedMain.getPlayer()));
		getBoardPresenter().getView().setBoard(game.getBoard());
		getPlayerInfoPresenter().getView().setPlayer(NetworkedMain.getPlayer());
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
		
		Game game = null;
		try {
			game = gameSvc.refreshGame(NetworkedMain.getRoomName());
			updateViews(game);

			// Test
//			System.out.println(gameSvc.isPlayerTurn(NetworkedMain.getRoomName(), NetworkedMain.getPlayer()));
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateViews(game);
		startGame(game);
	}
	
	/**
	 * Signals start of phase. Should only be called once.
	 */
	private void startGame(Game game) {
		// Disable UI if not current player, and listen for changes.
		if (!game.getCurrentPlayer().equals(NetworkedMain.getPlayer())) {
			view.setDisable(true);
			service.start();
		}
		
		// Triggers the First phase
		KNTAppFactory.getGamePlay().start();
	}

	public void turnHasEnded() {
		// Lock the UI by disabling it
		view.setDisable(true);
		System.out.println("Turn ended");
		
		// Start the hacky refresh thread.
		service.reset();
		service.start();
	}
	
	private void monitor() {
		try {
//			final Game game = gameSvc.refreshGame(NetworkedMain.getRoomName());
			
			// Check if it is now this client's/player's turn.
			if (gameSvc.isPlayerTurn(NetworkedMain.getRoomName(), NetworkedMain.getPlayer())) {
				System.out.println("MY TURN");

				service.cancel();
				updateTimer.cancel();
				
				view.setDisable(false);
				
				// Fetch the updated game
				Game game = gameSvc.refreshGame(NetworkedMain.getRoomName());
				getDicePresenter().getView().setDice(game.getDie1(), game.getDie2());
				// This one crashes.
				//getBoardPresenter().getView().setBoard(game.getBoard());
				getPlayerInfoPresenter().getView().setPlayer(NetworkedMain.getPlayer());
				
				// Have no choice...
				KNTAppFactory.getGamePlay().startTurn();
			}
			

		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
