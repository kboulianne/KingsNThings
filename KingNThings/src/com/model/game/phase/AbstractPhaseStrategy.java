/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.main.NetworkedMain;
import com.model.Board;
import com.model.game.Game;
import com.presenter.Util;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.view.GameView;

import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
public abstract class AbstractPhaseStrategy implements IPhaseStrategy {

	protected final GamePlay context;

	protected Game game;
//	protected GameView gv;

	protected AbstractPhaseStrategy(final GamePlay context) {
		this.context = context;
		try {
			game = context.gameSvc.refreshGame(NetworkedMain.getRoomName());
		} catch (JSONRPC2Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void turnStart() {
		Util.playClickSound();
		//top label
		getGamePresenter().getView().getCurrentPlayerLbl()
				.setText(game.getCurrentPlayer().getName()+"'s Turn: ");
//		getPlayerInfoPresenter().getView().setPlayer(game.getCurrentPlayer());
//		getSidePanePresenter().getView().setOpponents(game.getOpponentsForCurrent());
		
	}
	
	@Override
	public void turnEnd() {
		Board b = game.getBoard();
		b.reset();	
		getBoardPresenter().getView().setBoard(b);
		getSidePanePresenter().getView().clearDetailsView();
		getPopupPresenter().dismissPopup();
	}
}
