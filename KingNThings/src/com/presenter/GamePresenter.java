/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import com.model.game.Game;
import view.com.DiceView;
import view.com.GameView;

/**
 * OPTION 1 of:
 * http://www.zenjava.com/2011/12/11/javafx-and-mvp-a-smorgasbord-of-design-patterns/
 * 
 * Logic stuff goes here.
 *
 * @author kurtis
 */
public class GamePresenter {
    private final GameView view;
    // Singleton for now, not needed here
    // Service is usually responsible for CRUD operations of databases. In our case, networking.
    // Everything we will do will require service eventually.
//    private GameService gameService;
    // Presenters for sub-views go here
    private DicePresenter dicePresenter;
    
    public GamePresenter(final GameView view /* service and Other presenters here */) {
        this.view = view;
	this.view.setPresenter(this);
	
	// Update view
	Game model = GameService.getInstance().getGame();
	view.setGame(model);
    }
    
    
    
    public GameView getView() {
        return view;
    }
    
//    public DiceView getDiceView() {
//	return dicePresenter.getView();
//    }
//    
    public void setDicePresenter(DicePresenter presenter) {
	dicePresenter = presenter;
	
	// Add the DiceView to the game view
	view.addDiceView(presenter.getView());
    }
    
    public void handleGamePlayEvents(Object someFutureObjectClass) {
	// Hook up to game.getGamePlay().someEvent?
	// then update view
    }
    
    // Presenter operations go here
    // i.e. what happens when a button is pressed...
    // public void rollDice();

    public void endPlayerTurn() {
	GameService.getInstance().endTurn();
	
	Game game = GameService.getInstance().getGame();
	
	// Not a glitch, UI displays player that will execute game logic next
	view.setGame(game);
    }
}
