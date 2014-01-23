/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.main;

import com.presenter.DicePresenter;
import com.presenter.GamePresenter;
import view.com.DiceView;
import view.com.GameView;

/**
 *
 * @author kurtis
 */
public class KingsNThingsAppFactory {
    private GamePresenter mainPresenter;
    private DicePresenter dicePresenter;
    
    // TODO refactor into a better factory? Or is this sufficient?
    public GamePresenter getMainPresenter() {
	if (mainPresenter == null) {
	    GameView view = new GameView();
	    mainPresenter = new GamePresenter(view);
	    mainPresenter.setDicePresenter(getDicePresenter());
	}
	
	return mainPresenter;
    }
    
    public DicePresenter getDicePresenter() {
	/// Create the presenter if needed.
	if (dicePresenter == null) {
	    DiceView view = new DiceView();
	    dicePresenter = new DicePresenter(view, mainPresenter);
	}
	
	return dicePresenter;
    }
}