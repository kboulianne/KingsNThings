/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import com.model.Die;
import com.model.game.Game;
import javafx.application.Platform;
import view.com.DiceView;

/**
 *
 * @author kurtis
 */
public class DicePresenter {
    private final DiceView view;
    // In case we need to do something else in the main screen.
    private GamePresenter mainPresenter;
    
    public DicePresenter(DiceView view, GamePresenter mainPresenter) {
	this.view = view;
	this.view.setPresenter(this);
	this.mainPresenter = mainPresenter;
	
	// Set initial model
	Game game = GameService.getInstance().getGame();
	
	view.setDice(game.getDie1(), game.getDie2());
    }
    
    public DiceView getView() {
	return view;
    }
    
    public void roll() {
	// usually service.something
	GameService.getInstance().roll();
	// Currently possible to do game.rollDice(), bypassing the service.
	

	Game game = GameService.getInstance().getGame();

	// Update the view
	view.setDice(game.getDie1(), game.getDie2());
    }
}
