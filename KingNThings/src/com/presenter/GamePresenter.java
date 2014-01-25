/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.presenter;

import com.game.services.GameService;
import com.model.Player;
import com.model.game.Game;
import com.model.game.GameEvents;
import com.model.game.phase.GamePlay;
import com.view.GameView;
import com.view.ThingEvent;
import java.util.concurrent.locks.Lock;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
	//private DicePresenter dicePresenter;
	private SidePanePresenter sidePanePresenter;
	//private BoardPresenter boardPresenter;
	private PlayerInfoPresenter playerInfoPresenter;
	private PopupPresenter popupPresenter;

	public GamePresenter(
			final GameView view,
			final DicePresenter dicePresenter,
			final SidePanePresenter sidePanePresenter,
			final BoardPresenter boardPresenter,
			final PlayerInfoPresenter playerInfoPresenter,
			final PopupPresenter popupPresenter
	/* service and Other presenters here */) {
		this.view = view;
		this.view.setPresenter(this);

		// TODO forced to add sub-views in order due to current layout. Fix this.
		// Keep DicePresenter and add its view to the main view
		//this.dicePresenter = dicePresenter;
		// TODO WHAT AM I DOING? JUST ADD VIEW FROM PRESENTER
		this.view.addDiceView(dicePresenter.getView());

		// SidePane initialization
		this.sidePanePresenter = sidePanePresenter;
		this.view.addSidePaneView(sidePanePresenter.getView());

		// Board initialization
		//this.boardPresenter = boardPresenter;
		this.view.addBoardView(boardPresenter.getView());

		// PlayerInfo initialization
		this.playerInfoPresenter = playerInfoPresenter;
		this.view.addPlayerInfoView(playerInfoPresenter.getView());

		// The presenter which displays popups on the screen.
		this.popupPresenter = popupPresenter;
		// Set the parent view
		this.popupPresenter.getView().setParent(this.view);

		// Update view
		Game model = GameService.getInstance().getGame();
		view.setGame(model);

		// Everything is loaded, start game
		GameService.getInstance().startGame();

		listenForGameEvents();
	}

	/**
	 * Gets the view managed by this presenter.
	 *
	 * @return
	 */
	public GameView getView() {
		return view;
	}

	private void listenForGameEvents() {
		// Hook up to game.getGamePlay().someEvent?
		// then update view
		GamePlay.getInstance();
		// Start a background thread that wakes up when GamePlay notifies it
		// of a new action.
		Service<Void> service = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						System.out.println("GamePresenter: call()");
						
						// Consumes messages
						GameEvents.getConsumer().run();
						
						return null;
					}
				};
			}
		};

		service.start();
		
	}

	// UI Logic stuff is done here. Access service for model.
	public void endPlayerTurn() {
		GameService.getInstance().endTurn();

		Game game = GameService.getInstance().getGame();
		view.setGame(game);

		// For now
//	playerInfoPresenter.getView().setPlayer(game.getCurrentPlayer());
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

		// TESTING: THIS WILL BE IN phase executes
		GameEvents.getProducer().setMessage("A TEST MESSAGE");

//	popupPresenter.showPopup();
	}

	public void showPlayerInfoPopup(Player p) {
		popupPresenter.showPlayerPopup(p);
	}

	public void dismissPopup() {

		popupPresenter.dismissPopup();
	}

}
