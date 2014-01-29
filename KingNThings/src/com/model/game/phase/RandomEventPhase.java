/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.game.services.GameService;

/**
 *
 * @author kurtis
 */
public class RandomEventPhase extends AbstractPhaseStrategy<Object> {

	public RandomEventPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		System.out.println("Game Phase: Start of Random Events Phase");
	}

	/*
	@Override
	public void executePhase(Object input) {
		System.out.println("Game Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
	}
	*/

	@Override
	public void phaseEnd() {
		System.out.println("Game Phase: End of Random Events Phase");
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
