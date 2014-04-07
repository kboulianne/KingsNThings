package com.server.phase;

import java.util.Iterator;
import java.util.LinkedHashSet;
import com.model.Game;
import com.server.Notifications;
import com.server.ServerGameRoom;

import static com.server.KNTServer.*;

public final class ServerGamePlay {

	private IServerPhaseStrategy phase;
	// These are unique, put in set
	private LinkedHashSet<IServerPhaseStrategy> phases;
	private Iterator<IServerPhaseStrategy> phaseIt;
	protected ServerGameRoom room;
	
	public ServerGamePlay(ServerGameRoom room) {
		this.room = room;
		phases = new LinkedHashSet<>();
		
		phases.add(new ServerPlayerOrderPhase(this));		// PlayerOrderPhase
		phases.add(new ServerStartingPosPhase(this));		// StartingPosPhase
		phases.add(new ServerStartingKingdomPhase(this));	// StartingKingdomPhase
		phases.add(new ServerNOOPPhase(this));				// StartingTowerPhase
		phases.add(new ServerStartingForcesPhase(this));		// StartingForcesPhase
		phases.add(new ServerNOOPPhase(this));				// ExchangePhase
		
		// Game Phases
		phases.add(new ServerNOOPPhase(this));				// GoldCollectPhase
		phases.add(new ServerNOOPPhase(this));				// RecruitCharPhase
		phases.add(new ServerNOOPPhase(this));				// RecruitThingsPhase
		phases.add(new ServerNOOPPhase(this));				// RandomEventsPhase
		phases.add(new ServerMovementPhase(this));			// MovementPhase
		phases.add(new ServerNOOPPhase(this));				// CombatPhase
		phases.add(new ServerConstructionPhase(this));				// ConstructionPhase
		phases.add(new ServerNOOPPhase(this));				// SpecialPowersPhase
		phases.add(new ServerNOOPPhase(this));		// ChangePlayerOrderPhase
		
		phaseIt = phases.iterator();
	}
	
	public final void next() {
		// Start of first phase. Don't want to notify phaseEnd here.
		if (phase == null) phase = phaseIt.next();
		
		// Move to the next phase if the last player played his/her turn.
		if (room.getGame().isLastPlayer()) {
			// Only notify phase end at this point this implies the turn ended as well.
			
			if (phase.mustCycle()) {
				phase.decrementCycles();
			} else {
				phase.turnEnd();
				phase.phaseEnd();
				nextPhase();
			}
		}
		else {
			
		}
		// Next player's turn.
		nextTurn();			
		
	}
	
	private final void nextPhase() {
		// Cycle phases and loop back to gold collection
		if (phaseIt.hasNext()) {
			
			phase = phaseIt.next();
			phase.phaseStart();
		}
		else {
			// End of phases, reset.
			phaseIt = phases.iterator();
			
			// Advance the iterator to gold collection.
			int i = 0;
			while (i <= 6) {
				phaseIt.next();
				i++;
			}
		}
		
		notifyAllClients(room, Notifications.PHASE_ENDED);
	}
	
	private final void nextTurn() {
		Game game = room.getGame();
		// Execute any server-side logic to end turn in the current phase.
		phase.turnEnd();
		game.nextPlayer();
		phase.turnStart();
		
		//TODO: Don't send when phaseEnd sent.
		// Notify clients that a new player is now playing a turn
		notifyAllClients(room, Notifications.TURN_ENDED);
	}

	public void skipPhase() {
		// Just to circumvent bug if skipping first phase
		if (phase == null) phase = phaseIt.next();

		// Equivalent to phase end but uses defaults for testing
		phase.skipPhase();
		nextPhase();
//		phase.turnStart();
	}
}
