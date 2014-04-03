package com.presenter;

import com.main.NetworkedMain;
import com.model.Game;
import com.model.Player;
import com.server.services.IGameService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.view.GameView;

import static com.main.KNTAppFactory.*;


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
	
	// The game being modified during the turn.
	private static Game game;
	
	public GamePresenter( final GameView view, IGameService gameSvc) {
		this.view = view;
		this.gameSvc = gameSvc;
	}

	/**
	 * Initial setup for this presenter. Adds all necessary sub-views to the GameView.
	 */
	public void setupSubViews() {
		// DiceView
		this.view.addDiceView(getDicePresenter().getView());
		// SidePaneView
		this.view.addSidePaneView(getSidePanePresenter().getView());
		// BoardView
		this.view.addBoardView(getBoardPresenter().getView());
		// PlayerInfoView
		this.view.addPlayerInfoView(getPlayerInfoPresenter().getView());
		// PopupView
		getPopupPresenter().getView().setParent(view);
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
			game = gameSvc.getGame(NetworkedMain.getRoomName());
			updateViews(game);

		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateViews(Game game) {
		//FIXME: Called twice when turn ends and phase ends. PhaseEnd should imply turnEnd.
		// Update subviews
		view.setGame(game, getGamePlay().getActionText());
		getDicePresenter().getView().setDice(game.getDie1(), game.getDie2());
		getSidePanePresenter().getView().setOpponents(game.getOpponentsFor(NetworkedMain.getPlayer()));
		getBoardPresenter().getView().setBoard(game.getBoard());
		
		
		// Enable UI for current player.
		if (NetworkedMain.isPlayerTurn(game.getCurrentPlayer())) {
			// Get latest player info
			NetworkedMain.setPlayer(game.getCurrentPlayer());
			getPlayerInfoPresenter().getView().setPlayer(game.getCurrentPlayer());
			
			view.setDisable(false);
		}
		else {
			view.setDisable(true);
		}
	}
	
	/**
	 * Shows the contents of the cup as a popup.
	 */
	void showCup() {
		getPopupPresenter().showCupPopup(game.getCup());
	}

	/**
	 * Shows player information when the mouse is hovered over a player name.
	 * @param p 
	 */
	public void showPlayerInfoPopup(Player p) {
		getPopupPresenter().showPlayerPopup(p);
	}

	/**
	 * Dismisses the popup currently displayed by the PopupPresenter.
	 */
	void dismissPopup() {
		getPopupPresenter().dismissPopup();
	}

	public void showGameUI() {
		NetworkedMain.setView(view, 1280, 800);
	}

	public void endTurn() {
		try {
			getGamePlay().getPhaseLogic().turnEnd(game);
			// Update the game on server
			gameSvc.updateGame(NetworkedMain.getRoomName(), game);
			// Tell server that this player's turn is done.
			gameSvc.endTurn(NetworkedMain.getRoomName(), NetworkedMain.getPlayer());
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Game getLocalInstance() {
		return game;
	}

	public void onGameStarted() {
		// Show the UI
		showGameUI();
		
		try {
			game = gameSvc.getGame(NetworkedMain.getRoomName());
			updateViews(game);
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Trigger initial phase.
		getGamePlay().nextPhase();
		getGamePlay().getPhaseLogic().phaseStart(game);
		getGamePlay().getPhaseLogic().turnStart(game);
	}
	
	/**
	 * Handles notification sent by server.
	 * Current player's turn ended. 
	 */
	public void onTurnEnded() {
		try {
			// Refresh local instance.
			game = gameSvc.getGame(NetworkedMain.getRoomName());
			updateViews(game);
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Local instance is in sync. Check if it is this client's, re-enable UI and execute client-side turnStart logic
		if (NetworkedMain.isPlayerTurn(game.getCurrentPlayer())) {
			
			// Trigger turnStart in phase logic.
			getGamePlay().getPhaseLogic().turnStart(game);
		}
	}

	public void onPhaseEnded() {
		getGamePlay().getPhaseLogic().phaseEnd(game);

		
		try {
			getGamePlay().nextPhase();
			// Refresh local instance.
			game = gameSvc.getGame(NetworkedMain.getRoomName());
			getGamePlay().getPhaseLogic().phaseStart(game);
			// Only call this if it is the player's turn
			if (NetworkedMain.isPlayerTurn(game.getCurrentPlayer()))
				getGamePlay().getPhaseLogic().turnStart(game);
			updateViews(game);
			
//			//TODO: Re-integrate phase navigation in GamePlay.
//			getGamePlay().getPhaseLogic().phaseStart(game);
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void skipPhase() {
		try {
			gameSvc.skipPhase(NetworkedMain.getRoomName());
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
