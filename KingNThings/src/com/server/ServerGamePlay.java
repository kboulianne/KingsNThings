package com.server;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.game.phase.IPhaseStrategy;
import com.model.Game;

import static com.server.KNTServer.*;

public final class ServerGamePlay {

	/** The game instance to operate on. */
//	private Game game;
	private IPhaseStrategy phase;
	// These are unique, put in set
	private Set<IPhaseStrategy> phases;
	private Iterator<IPhaseStrategy> phaseIt;
	protected ServerGameRoom room;
	
	public ServerGamePlay(ServerGameRoom room) {
		this.room = room;
		phases = new LinkedHashSet<>();
		
		phases.add(new ServerPlayerOrderPhase(this));
		phases.add(new ServerStartingPosPhase(this));
		phases.add(new ServerNOOPPhase(this));
		
		phaseIt = phases.iterator();
//		phase = phaseIt.next();
	}
	
	public final void next() {
		// Start of first phase. Don't want to notify phaseEnd here.
		if (phase == null) phase = phaseIt.next();
		
		// Move to the next phase if the last player played his/her turn.
		if (room.getGame().isLastPlayer()) {
			// Only notify phase end at this point this implies the turn ended as well.
			phase.turnEnd(room.getGame());
			phase.phaseEnd();
			nextPhase();
		}
			// Next player's turn.
			nextTurn();			
		
	}
	
	private final void nextPhase() {
		// Stay in NOOP for now until we need more phases. Eventually phases should contain the same number of phases as client's
		// GamePlay
		if (phaseIt.hasNext()) {
			phase = phaseIt.next();
			phase.phaseStart(room.getGame());
		}
//		room.getGame().nextPlayer();
		
		// TODO: Notify clients of phase change. 
		notifyAllClients(room, Notifications.PHASE_ENDED);
	}
	
	private final void nextTurn() {
		Game game = room.getGame();
		// Execute any server-side logic to end turn in the current phase.
		phase.turnEnd(game);
		game.nextPlayer();
		
		// Notify clients that a new player is now playing a turn
		notifyAllClients(room, Notifications.TURN_ENDED);
	}
}
