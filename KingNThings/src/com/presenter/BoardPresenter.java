/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import com.model.game.Game;
import view.com.BoardView;

/**
 *
 * @author kurtis
 */
public class BoardPresenter {
    private final BoardView view;
    private GamePresenter mainPresenter;
    
    public BoardPresenter(BoardView view, GamePresenter main) {
	this.view = view;
	this.view.setPresenter(this);
	this.mainPresenter = main;
	
	// Set initial model (usually uses a service.
	Game game = GameService.getInstance().getGame();
	
	view.setBoard(game.getBoard());
    }
    
    public BoardView getView() {
	return view;
    }
    
    // UI Logic here.
}
