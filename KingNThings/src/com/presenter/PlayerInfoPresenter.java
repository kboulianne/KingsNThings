/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import com.model.game.Game;
import view.com.PlayerInfoView;

/**
 *
 * @author kurtis
 */
public class PlayerInfoPresenter {
    
    private PlayerInfoView view;
    
    public PlayerInfoPresenter(PlayerInfoView view) {
	this.view = view;
	this.view.setPresenter(this);
	
	Game game = GameService.getInstance().getGame();
	view.setPlayer(game.getCurrentPlayer());
    }
    
    public PlayerInfoView getView() {
	return view;
    }
    
    //TODO ViewCup should be managed by GamePresenter
}
