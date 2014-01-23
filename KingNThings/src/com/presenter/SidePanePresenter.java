/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import com.model.game.Game;
import view.com.SidePaneView;

/**
 *
 * @author kurtis
 */
public class SidePanePresenter {
    private final SidePaneView view;
    private GamePresenter mainPresenter;
    
    public SidePanePresenter(SidePaneView view, GamePresenter mainPresenter) {
	this.view = view;
	this.view.setPresenter(this);
	this.mainPresenter = mainPresenter;
	
	Game game = GameService.getInstance().getGame();
	
	view.setOpponents(game.getOpponent1(), game.getOpponent2(), game.getOpponent3());
    }
    
    public SidePaneView getView() {
	return view;
    }
    
    // Handlers go here.
}
