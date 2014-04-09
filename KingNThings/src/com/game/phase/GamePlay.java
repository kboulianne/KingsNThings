/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.phase;

import java.util.Iterator;
import java.util.LinkedHashSet;
import com.game.phase.init.ExchangePhase;
import com.game.phase.init.PlayerOrderPhase;
import com.game.phase.init.StartingForcesPhase;
import com.game.phase.init.StartingKingdomPhase;
import com.game.phase.init.StartingPosPhase;
import com.game.phase.init.StartingTowerPhase;

/**
 * The Context class in the Strategy Pattern. This is the Game's "Behaviour" or
 * logic and does not need to be coupled with the game.
 * 
 * @author kurtis
 */
public final class GamePlay {

	/**
	 * Current gameplay logic to execute.
	 */
	private AbstractPhaseStrategy phaseLogic;
	
	/**
	 * Initial phases
	 */
	private final LinkedHashSet<AbstractPhaseStrategy> initPhases;
	private final LinkedHashSet<AbstractPhaseStrategy> gamePhases;
	// Current phase being played by the players.
	private Iterator<AbstractPhaseStrategy> phaseIt;

	/**
	 * Creates a new, default instance of the game logic.
	 */
	public GamePlay() {
		initPhases = new LinkedHashSet<>();
		gamePhases = new LinkedHashSet<>();
		createInitPhases();
		createGamePhases();
	}

	/**
	 * Creates phases responsible for setting up the game.
	 */
	private void createInitPhases() {
		initPhases.add(new PlayerOrderPhase(this));
		initPhases.add(new StartingPosPhase(this));
		initPhases.add(new StartingKingdomPhase(this));
		initPhases.add(new StartingTowerPhase(this));
		initPhases.add(new StartingForcesPhase(this));
		initPhases.add(new ExchangePhase(this));

		// Set current phase logic/strategy
		phaseIt = initPhases.iterator();
	}

	/**
	 * Creates the phases responsible for normal gameplay.
	 */
	private void createGamePhases() {
		gamePhases.add(new GoldCollectPhase(this));
		gamePhases.add(new RecruitCharPhase(this));
		gamePhases.add(new RecruitThingsPhase(this));
		gamePhases.add(new RandomEventPhase(this));
		gamePhases.add(new MovementPhase(this));
		gamePhases.add(new CombatPhase(this));
		gamePhases.add(new ConstructionPhase(this));
		gamePhases.add(new SpecialPowersPhase(this));
		gamePhases.add(new ChangePlayOrderPhase(this));
	}

	public final String getActionText() {
		if (phaseLogic == null) return null;
		
		return phaseLogic.getActionText();
	}
	
	/**
	 * Called when the last player in turn order has executed his turn. Switches
	 * to the startTurn phase.
	 */
	public void nextPhase() {

		if (!phaseIt.hasNext()) {
			// Move iterator to gamePhases.
			phaseIt = gamePhases.iterator();
		}
		phaseLogic = phaseIt.next();

	}

	public AbstractPhaseStrategy getPhaseLogic() {
		return phaseLogic;
	}
}
