/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import com.model.Hex;
import com.model.Thing;
import com.model.game.Game;
import view.com.HexDetailsView;
import view.com.SidePaneView;

/**
 *
 * @author kurtis
 */
public class SidePanePresenter {
    private final SidePaneView view;
    private HexDetailsPresenter hexDetailsPresenter;
    private ThingDetailsPresenter thingDetailsPresenter;
    
    public SidePanePresenter(SidePaneView view) {
	this.view = view;
	this.view.setPresenter(this);
        
	Game game = GameService.getInstance().getGame();
	
	view.setOpponents(game.getOpponent1(), game.getOpponent2(), game.getOpponent3());
    }
    
    public SidePaneView getView() {
	return view;
    }
    
    public void setHexDetailsPresenter(final HexDetailsPresenter presenter) {
	if (presenter == null) {
	    throw new NullPointerException("Presenter cannot be null.");
	}
	if (hexDetailsPresenter != null) {
	    throw new IllegalStateException("Presenter has already been set.");
	}
	
	hexDetailsPresenter = presenter;
    }
    
    public void setThingDetailsPresenter(final ThingDetailsPresenter presenter) {
	if (presenter == null) {
	    throw new NullPointerException("Presenter cannot be null.");
	}
	if (thingDetailsPresenter != null) {
	    throw new IllegalStateException("Presenter has already been set.");
	}
	
	thingDetailsPresenter = presenter;
    }
    
    // Handlers go here.
    public void showHexDetailsFor(Hex h) {
	view.showHexDetailsView(hexDetailsPresenter.getView());
	
	// make the presenter update the view
	hexDetailsPresenter.showHex(h);
    }

    void showThingDetailsFor(Thing t) {
        view.showThingDetailsView(thingDetailsPresenter.getView());
    }
}
