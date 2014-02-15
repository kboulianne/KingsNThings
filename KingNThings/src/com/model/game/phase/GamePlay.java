/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import com.game.services.GameService;
import com.presenter.Util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.SortedMap;
import java.util.TreeMap;

import com.model.Player;
import com.model.game.Game;
import com.model.game.phase.init.ExchangePhase;
import com.model.game.phase.init.PlayerOrderPhase;
import com.model.game.phase.init.StartingForcesPhase;
import com.model.game.phase.init.StartingKingdomPhase;
import com.model.game.phase.init.StartingPosPhase;
import com.model.game.phase.init.StartingTowerPhase;

/**
 * The Context class in the Strategy Pattern. This is the Game's "Behaviour" or logic and does not need to be coupled
 * with the game.
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

    // TODO Add a model?
	// Maps dice total to the player that made the roll.
	private final SortedMap<Integer, Player> rolls;

	// Used by Starting Kingdoms Phase to cycle player order before ending phase.
	private int cycles = 0;
	
	/**
	 * Inner class responsible for holding singleton instance. Initialized once.
	 */
	private static class SingletonHolder {

		public static final GamePlay INSTANCE = new GamePlay();
	}

	public static GamePlay getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * Creates a new, default instance of the game logic.
	 */
	private GamePlay() {
		initPhases = new LinkedHashSet<>();
		gamePhases = new LinkedHashSet<>();
		rolls = new TreeMap<>(new Util.ReverseIntegerSortComparator());

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
		phaseLogic = phaseIt.next();
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
		
		// FOR TESTING: The one in initPhases is correct.
		//		phaseIt = gamePhases.iterator();
		//		phaseLogic = phaseIt.next();
	}
	
	public void setCycleCount(int cycle) {
		this.cycles = cycle;
	}
	
	/**
	 * Adds the specified dice total for the player.
	 * @param total The total to add
	 * @param p The player that rolled this total.
	 */
	public void addPlayerRoll(int total, Player p) {
		// TODO Handle case where two players have the same dice total.
		rolls.put(total, p);
	}

	/**
	 * Clears the rolls previously added to the map.
	 */
	public void clearRolls() {
		rolls.clear();
	}

	/**
	 * Gets the the players ordered by their dice totals in descending order.
	 * @return The collection of players, in descending order.
	 */
	public final Collection<Player> getPlayersHighToLow() {
		return rolls.values();
	}

	/**
	 * Called when the last player in turn order has executed his turn. Switches to the startTurn phase.
	 */
	private void nextPhase() {

		if (!phaseIt.hasNext()) {
			// Move iterator to gamePhases.
			phaseIt = gamePhases.iterator();
		}
		phaseLogic = phaseIt.next();

		// Phase start
		phaseLogic.phaseStart();
	}

	/**
	 * Signals the start of game.
	 */
	public void start() {
		// Initial phase start
		phaseLogic.phaseStart();
		// Needed once player order is set
		Game game = GameService.getInstance().getGame();
		game.nextPlayer();
		// First player's turn.
		startTurn();
	}
	
	/**
	 * Exectues turnStart logic for the current player.
	 */
	public final void startTurn() {
		//Game game = GameService.getInstance().getGame();

		// Execute logic then go to startTurn player / phase
		phaseLogic.turnStart();
		
		// FOR NOW! Automatic phase skipping!
		if (
				false 	// For testing, easier to comment out
				|| phaseLogic instanceof PlayerOrderPhase
				|| phaseLogic instanceof StartingPosPhase
				|| phaseLogic instanceof StartingKingdomPhase
				|| phaseLogic instanceof StartingTowerPhase
//				|| phaseLogic instanceof StartingForcesPhase
				|| phaseLogic instanceof ExchangePhase
//				|| phaseLogic instanceof GoldCollectPhase
				|| phaseLogic instanceof RecruitCharPhase
//				|| phaseLogic instanceof RecruitThingsPhase
				|| phaseLogic instanceof RandomEventPhase
//				|| phaseLogic instanceof MovementPhase
				|| phaseLogic instanceof ConstructionPhase
				|| phaseLogic instanceof SpecialPowersPhase
				|| phaseLogic instanceof ChangePlayOrderPhase
				)
			endTurn();
	}
	
	/**
	 * Called when a player has pressed Finished Turn. Executes
	 * turnEnd() logic, switches to the next phase if it is the last player
	 * in turn order, switches to the next player and starts their turn.
	 */
	public void endTurn() {
		// End the player's turn and go to startTurn player.
		phaseLogic.turnEnd();
		// State modification. Call method in game
		Game game = GameService.getInstance().getGame();
		
		// Signal end of phase if last player or when no more cycles are left
		if (game.isLastPlayer() ) {
			if (cycles == 0) {
				phaseLogic.phaseEnd();
				nextPhase();
			}
			else {
				// Decrement cycle count
				cycles --;
			}
		}
		
		// Switch to the next player and start the turn.
		game.nextPlayer();
		startTurn();
	}

	public SortedMap<Integer, Player> getRolls() {
		return rolls;
	}
}

