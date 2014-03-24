/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase;

import com.main.KNTAppFactory;
import com.main.NetworkedMain;
import com.model.Board;
import com.model.Game;
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

	// Same instance for every phase.
	protected static Game game;
//	protected GameView gv;

	protected AbstractPhaseStrategy(final GamePlay context) {
		this.context = context;
//		try {
//			game = context.getService().refreshGame(NetworkedMain.getRoomName());
//		} catch (JSONRPC2Error e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public abstract String getActionText();
	
	@Override
	public void phaseStart(Game game) {
//		// Always refresh the local game instance
//		try {
//			game = context.getService().refreshGame(NetworkedMain.getRoomName());
//		} catch (JSONRPC2Error e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@Override
	public void turnStart(Game game) {
		Util.playClickSound();
		
		//top label
		// OK
//		getGamePresenter().getView().getCurrentPlayerLbl()
//				.setText(game.getCurrentPlayer().getName()+"'s Turn: ");
//		getPlayerInfoPresenter().getView().setPlayer(game.getCurrentPlayer());
//		getSidePanePresenter().getView().setOpponents(game.getOpponentsForCurrent());
		
	}
	
	@Override
	public void turnEnd(Game game) {
		Board b = game.getBoard();
		b.reset();	
		getBoardPresenter().getView().setBoard(b);
		getSidePanePresenter().getView().clearDetailsView();
		getPopupPresenter().dismissPopup();

	}
}
