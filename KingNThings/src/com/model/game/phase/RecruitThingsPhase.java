/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.game.services.GameService;
import com.model.Hex;
import com.model.Player;
import com.model.game.Game;

/**
 *
 * @author kurtis
 */
public class RecruitThingsPhase extends AbstractPhaseStrategy<Object> {

	public RecruitThingsPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		System.out.println("Game Phase: Start of Recruiting Things Phase");
	}

	@Override
	public void preExecutePhase(Object input) {

	}

	@Override
	public void executePhase(Object input) {
		System.out.println("Game Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
		
		int hexCount = 0;
		Game game = GameService.getInstance().getGame();
		Player player = game.getCurrentPlayer();
		
		for (Hex h : game.getBoard().getHexes()) {
			if ((h != null) && (h.getOwner() == player)) {
				hexCount++;
			}
		}
	}

	@Override
	public void postExecutePhase(Object input) {

	}

	@Override
	public void phaseEnd() {
		System.out.println("Game Logic: End of Recruiting Things Phase");
	}

}
