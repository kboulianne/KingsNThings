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
		
		// For testing, set to these phases if initPhases is empty
		if (initPhases.isEmpty())
			phaseIt = gamePhases.iterator();
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

		// Phase start
//		phaseLogic.phaseStart(null);
	}

	/**
	 * Signals the start of game.
	 */
//	@Deprecated
//	public void start() {
//		//TODO: Check if already started.
//		// Initial phase start
//		phaseLogic = phaseIt.next();
//		phaseLogic.phaseStart(null);
//
//		// First player's turn.
//		startTurn();
//	}

//	/**
//	 * Exectues turnStart logic for the current player.
//	 */
//	@Deprecated
//	public final void startTurn() {
//		// Execute logic then go to startTurn player / phase
//		phaseLogic.turnStart(null);
//
//	}

	/**
	 * Called when a player has pressed Finished Turn. Executes turnEnd() logic,
	 * switches to the next phase if it is the last player in turn order,
	 * switches to the next player and starts their turn.
	 */
//	@Deprecated
//	public void endTurn() {
//		// End the player's turn and go to startTurn player.
//		phaseLogic.turnEnd(null);
//		// State modification. Call method in game
////		Game game = GameService.getInstance().getGame();
//
//		// When turn ends, local copy of game is up to date.
//		Game game = AbstractPhaseStrategy.game; 
//		
//		// Signal end of phase if last player or when no more cycles are left
//		// TODO: Create rpc call for this check. Would be more efficient.
//		if (game.isLastPlayer()) {
//			if (cycles == 0) {
//				phaseLogic.phaseEnd();
//				nextPhase();
//			} else {
//				// Decrement cycle count
//				cycles--;
//			}
//		}
//
//		// Switch to the next player and start the turn.
//		game.nextPlayer();
//		
//		// For now, until notifications are implemented.
//		// Fire changes towards server
//		try {
//			gameSvc.updateGame(NetworkedMain.getRoomName(), game);
////			gameSvc.endTurn(NetworkedMain.getRoomName(), NetworkedMain.getPlayer());
//
//			// Tell game presenter that the turn has ended.
////			getGamePresenter().onTurnEnded();
//		} catch (JSONRPC2Error e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public AbstractPhaseStrategy getPhaseLogic() {
		return phaseLogic;
	}
	
//	public IGameService getService() {
//		return gameSvc;
//	}
}
