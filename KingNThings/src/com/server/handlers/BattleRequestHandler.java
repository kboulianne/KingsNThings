package com.server.handlers;

import java.util.List;

import com.model.Battle;
import com.model.Fort;
import com.model.Hex;
import com.model.Player;
import com.model.Battle.BattlePhase;
import com.server.Notifications;
import com.server.ServerGameRoom;
import com.server.services.IBattleService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

import static com.server.KNTServer.*;

public class BattleRequestHandler extends BaseRequestHandler implements IBattleService {

	@Override
	public String[] handledRequests() {
		return new String[] { "startBattle", "getOngoingBattle", "updateBattle", "battleTurnEnded", "retreat" };
	}

	@Override
	public Battle startBattle(String roomName, Player attacker,
			List<Player> defenders, Hex hex) throws JSONRPC2Error {
		
		synchronized (GAME_ROOMS.get(roomName)) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			
			// Create the object for the current battle.
			//TODO: Change boolean.
			Battle battle = new Battle(attacker, defenders.get(0), hex, false);
			
			room.setBattle(battle);
			
			// Allows opponents to watch the ongoing battle.
			notifyClientsExclude(room, Notifications.BATTLE_STARTED, battle.getCurrentPlayer());
			
			return battle;
		}
	}

	@Override
	public Battle getOngoingBattle(String roomName) throws JSONRPC2Error {
		synchronized (GAME_ROOMS.get(roomName)) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			
			return room.getBattle();
		}
	}
	
	@Override
	public void updateBattle(String roomName, Battle battle) throws JSONRPC2Error {
		synchronized (GAME_ROOMS.get(roomName)) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			
			room.setBattle(battle);
			
			// Evaluate winning conditions.
			if (battle.isOffenderEliminated() || battle.isDefenderEliminated()) {
				determineWinner(room);
			}
			else {
				// Notify opponents
				notifyClientsExclude(room, Notifications.BATTLE_UPDATED, battle.getCurrentPlayer());
			}
		}
	}

	@Override
	public void battleTurnEnded(String roomName, Battle battle) throws JSONRPC2Error {
		synchronized (GAME_ROOMS.get(roomName)) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			room.setBattle(battle);

			notifyClients(room, Notifications.BATTLE_TURN_ENDED, room.getBattle().getCurrentPlayer());
		}
	}
	
	private void determineWinner(ServerGameRoom room) {
		Battle battle = room.getBattle();
		determineWinner(room, (battle.isOffenderEliminated() ? 
			  	battle.getDefender() : 
				battle.getOffender()));
	}

	private void determineWinner(ServerGameRoom room, Player winner) {
		Battle battle = room.getBattle();
		battle.setWinner(winner);
			
		Hex associatedHex = battle.getAssociatedHex();
		
		// Update the hex
		if(associatedHex.getOwner() == null && associatedHex.getKedabCreatures().isEmpty()){
			associatedHex.setOwner(winner);
			associatedHex.setConflict(false);
		}
		else if(!associatedHex.getKedabCreatures().isEmpty() && associatedHex.getArmies().size()<2){
			associatedHex.setConflict(false);
		}
		if(associatedHex.getArmies().keySet().size()==1 && associatedHex.getKedabCreatures().isEmpty()){ 	
			associatedHex.setOwner(winner);
			associatedHex.setConflict(false);
		}
		if((associatedHex.getFort() != null) && (associatedHex.getFort().getFortType() == Fort.FortType.CITADEL))	{
			if(winner.isCitadelOwner())	{
				notifyAllClients(room, Notifications.GAME_ENDED);
				return;
			} else	{
				winner.setCitadelOwner(true);
			}
		}
		
		notifyAllClients(room, Notifications.BATTLE_ENDED);		
	}
	
	@Override
	public void retreat(String roomName) throws JSONRPC2Error {
		synchronized (GAME_ROOMS.get(roomName)) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			
			Battle battle = room.getBattle();
			
			if (!battle.isOffenderEliminated() && !battle.isDefenderEliminated()) {
				Player p = null;
				// Current is the one that retreated.
				if (battle.getCurrentPlayer().equals(battle.getOffender()))
					p = battle.getDefender();
				else 
					p = battle.getOffender();
				
				determineWinner(room, p);
			}
			
			
		}
	}
}
