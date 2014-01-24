/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import com.model.game.Game;
import com.view.PlayerInfoView;
import com.view.ThingEvent;

/**
 *
 * @author kurtis
 */
public class PlayerInfoPresenter {
    
    private PlayerInfoView view;
    private SidePanePresenter sidePanePresenter;
    
    
    public PlayerInfoPresenter(PlayerInfoView view, SidePanePresenter sidePanePresenter) {
	this.view = view;
	this.view.setPresenter(this);
	
	this.sidePanePresenter = sidePanePresenter;
	
	Game game = GameService.getInstance().getGame();
	view.setPlayer(game.getCurrentPlayer());
    }
    
    public PlayerInfoView getView() {
	return view;
    }
    
    //TODO ViewCup should be managed by GamePresenter

    public void handleRackClick(ThingEvent t) {
	System.out.println("I clicked a thing and I liked it!");
	System.out.println(t.getThing().toString());
	
	// Show thing in detailsview
	sidePanePresenter.showThingDetailsFor(t.getThing());
    }
}
