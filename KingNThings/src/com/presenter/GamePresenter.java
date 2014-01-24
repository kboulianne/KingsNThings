/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import com.model.game.Game;
import com.view.DiceView;
import com.view.GameView;

/**
 * OPTION 1 of:
 * http://www.zenjava.com/2011/12/11/javafx-and-mvp-a-smorgasbord-of-design-patterns/
 * 
 * Logic stuff for the game goes here. Sub-views have their own presenters
 * to encapsulate their logic.
 *
 * @author kurtis
 */
public class GamePresenter {
    private final GameView view;
    // Singleton for now, not needed here
//    private GameService gameService;
    private DicePresenter dicePresenter;
    private SidePanePresenter sidePanePresenter;
    private BoardPresenter boardPresenter;
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
	this.dicePresenter = dicePresenter;
	// TODO WHAT AM I DOING? JUST ADD VIEW FROM PRESENTER
	this.view.addDiceView(dicePresenter.getView());
	
	// SidePane initialization
	this.sidePanePresenter = sidePanePresenter;
	this.view.addSidePaneView(sidePanePresenter.getView());
	
	// Board initialization
	this.boardPresenter = boardPresenter;
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
     * @return 
     */
    public GameView getView() {
        return view;
    }
    
    public void handleGamePlayEvents(Object someFutureObjectClass) {
	// Hook up to game.getGamePlay().someEvent?
	// then update view
    }

    // UI Logic stuff is done here. Access service for model.
    
    public void endPlayerTurn() {
	GameService.getInstance().endTurn();
	
	Game game = GameService.getInstance().getGame();
	view.setGame(game);
    }
    
    public void showCup() {
	System.out.println("Showing Cup");
	
	// TESTING
	popupPresenter.showPopup();
    }
}
