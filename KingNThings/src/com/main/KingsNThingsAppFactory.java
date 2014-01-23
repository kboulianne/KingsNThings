/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.main;

import com.presenter.DicePresenter;
import com.presenter.GamePresenter;
import com.presenter.BoardPresenter;
import com.presenter.DetailsPresenter;
import com.presenter.SidePanePresenter;
import view.com.BoardView;
import view.com.DetailsView;
import view.com.DiceView;
import view.com.GameView;
import view.com.SidePaneView;

/**
 *
 * @author kurtis
 */
public class KingsNThingsAppFactory {
    private GamePresenter mainPresenter;
    private DicePresenter dicePresenter;
    private SidePanePresenter sidePanePresenter;
    private BoardPresenter boardPresenter;
    private DetailsPresenter detailsPresenter;
    
    // TODO refactor into a better factory? Or is this sufficient?
    public GamePresenter getMainPresenter() {
	if (mainPresenter == null) {
	    GameView view = new GameView();
	    mainPresenter = new GamePresenter(
		    view,
		    getDicePresenter(),
		    getSidePanePresenter(),
		    getBoardPresenter()
	    );
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
    
    public SidePanePresenter getSidePanePresenter() {
	if (sidePanePresenter == null) {
	    SidePaneView view = new SidePaneView();
	    sidePanePresenter = new SidePanePresenter(view, getDetailsPresenter());    
	}
	
	return sidePanePresenter;
    }
    
    public BoardPresenter getBoardPresenter() {
	if (boardPresenter == null) {
	    BoardView view = new BoardView();
	    boardPresenter = new BoardPresenter(
		    view,
		    getDetailsPresenter(),
		    mainPresenter
	    );
	}
	
	return boardPresenter;
    }
    
    public DetailsPresenter getDetailsPresenter() {
	if (detailsPresenter == null) {
	    DetailsView view = new DetailsView();
	    detailsPresenter = new DetailsPresenter(view);
	}
	
	return detailsPresenter;
    }
}
