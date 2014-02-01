/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.presenter.Util;

/**
 *
 * @author kurtis
 */
public class ChangePlayOrderPhase extends AbstractPhaseStrategy{

	public ChangePlayOrderPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Changing Player Order Phase");
	}
/*
	@Override
	public void executePhase(Object input) {
		Util.log("Game Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
	}
*/
	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Changing Player Order Phase");
	}

	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addHandlers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeHandlers() {
		// TODO Auto-generated method stub
		
	}
}
