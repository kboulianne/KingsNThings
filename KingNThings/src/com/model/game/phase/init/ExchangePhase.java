/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import com.game.services.GameService;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;

/**
 *
 * @author kurtis
 */
public class ExchangePhase extends AbstractPhaseStrategy<Object> {

	public ExchangePhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		System.out.println("Init Phase: Start of Exchange Things Phase");
	}

	/*
	@Override
	public void executePhase(Object input) {
		System.out.println("Init Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
	}
*/
	
	@Override
	public void phaseEnd() {
		System.out.println("Init Phase: End of Exchange Things Phase");
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
