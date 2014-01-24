/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import com.model.Hex;
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
    
    public SidePanePresenter(SidePaneView view, HexDetailsPresenter detailsPresenter) {
	this.view = view;
	this.view.setPresenter(this);
	this.hexDetailsPresenter = detailsPresenter;
	
	Game game = GameService.getInstance().getGame();
	
	view.setOpponents(game.getOpponent1(), game.getOpponent2(), game.getOpponent3());
    }
    
    public SidePaneView getView() {
	return view;
    }
    
    
    
    // Handlers go here.
    public void showHexDetailsFor(Hex h) {
	view.showHexDetailsView(hexDetailsPresenter.getView());
	
	// make the presenter update the view
	hexDetailsPresenter.showHex(h);
    }
}
