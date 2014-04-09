/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase;

import com.model.Board;
import com.model.Game;
import com.util.Util;
import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
public abstract class AbstractPhaseStrategy implements IPhaseStrategy {

	protected final GamePlay context;

	protected AbstractPhaseStrategy(final GamePlay context) {
		this.context = context;
	}
	
	public abstract String getActionText();
	
	@Override
	public void phaseStart(Game game) {
	}
	
	@Override
	public void turnStart(Game game) {
		Util.playClickSound();
	}
	
	@Override
	public void turnEnd(Game game) {
		Board b = game.getBoard();
		b.reset();	
		getSidePanePresenter().getView().clearDetailsView();
		getPopupPresenter().dismissPopup();

	}
}
