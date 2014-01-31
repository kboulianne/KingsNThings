/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.game.Game;
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public abstract class AbstractPhaseStrategy<T> implements IPhaseStrategy<T> {

	protected final GamePlay context;

	Game game;
	GameView gv;
	
	// TODO Avoid calling this in subclases. Don't remember if it is possible.
	protected AbstractPhaseStrategy(final GamePlay context) {
		this.context = context;
		
		game =  GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();
	}
	
	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		//top label
		gv.getCurrentPlayerLbl().setText(game.getCurrentPlayer().getName()+"'s Turn: ");
		
		KNTAppFactory.getPlayerinfopresenter().getView().setPlayer(game.getCurrentPlayer());
		KNTAppFactory.getSidepanepresenter().getView().setOpponents(game.getOpponent1(), game.getOpponent2(), game.getOpponent3());
		
	}
}
