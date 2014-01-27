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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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

//	final static Task<Void> gamePlayActions;
//	
//	static {
//		gamePlayActions = new Task<Void>() {
//
//			@Override
//			protected Void call() throws Exception {
//			
//				while (true) {
//					// Listen for next action to be executed by user.
//					synchronized (GamePlay.getInstance().actions) {
//						System.out.println("Blocked TASK");
//						GamePlay.getInstance().actions.wait();
//						System.out.println("Unblocked TASK");
//						
//					}
//				}
//			}
//		};
//		
//		new Thread(gamePlayActions).start();
//	}
	
	
	
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
	public void test() {
		// So we don't block the ui thread.
		final Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				
				// Everything is loaded, start game
				GameService.getInstance().startGame();

				// FOR NOW
				synchronized(GamePlay.getInstance().actions) {
					try {
						System.out.println("LOCKED: GamePresenter");
						// Wait for GameAction to be set.
						GamePlay.getInstance().actions.wait();
						
						System.out.println("UNLOCKED: GamePresenter");
					} catch (InterruptedException ex) {
						Logger.getLogger(GamePresenter.class.getName()).log(Level.SEVERE, null, ex);
					}

					
					// Needs to execute on FX Thread.
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							view.setAction(GamePlay.getInstance().actions.peek());
						}
					});
					
				}
				
				return null;
			}
		};
		new Thread(task).start();

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
//		GameEvents.getProducer().setMessage("A TEST MESSAGE");

//	popupPresenter.showPopup();
	}

	public void showPlayerInfoPopup(Player p) {
		popupPresenter.showPlayerPopup(p);
	}

	public void dismissPopup() {

		popupPresenter.dismissPopup();
	}

}
