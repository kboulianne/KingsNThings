/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Board;
import com.model.game.Game;
import com.presenter.Util;
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public abstract class AbstractPhaseStrategy implements IPhaseStrategy {

	protected final GamePlay context;

	protected Game game;
	protected GameView gv;
	
	// TODO Avoid calling this in subclases. Don't remember if it is possible.
	protected AbstractPhaseStrategy(final GamePlay context) {
		this.context = context;
		game =  GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();	
	}
	
	@Override
	public void turnStart() {
		Util.playClickSound();
		//top label
		gv.getCurrentPlayerLbl().setText(game.getCurrentPlayer().getName()+"'s Turn: ");
		KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(game.getCurrentPlayer());
		KNTAppFactory.getSidePanePresenter().getView().setOpponents(game.getOpponent1(), game.getOpponent2(), game.getOpponent3());
		
	}
	
	@Override
	public void turnEnd() {
		Board b = game.getBoard();
		b.reset();	
		KNTAppFactory.getBoardPresenter().getView().setBoard(b);
		KNTAppFactory.getSidePanePresenter().getView().clearDetailsView();
		KNTAppFactory.getPopupPresenter().dismissPopup();
	}
}
