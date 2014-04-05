package com.server.handlers;

import java.util.List;

import com.model.Battle;
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
		return new String[] { "startBattle", "getOngoingBattle", "updateBattle", "battleTurnEnded" };
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
			// For now, only notify opponent
			notifyClients(room, Notifications.BATTLE_STARTED, battle.getDefender());
//			notifyOtherClients(room, Notifications.BATTLE_STARTED, room.getGame().getCurrentPlayer());
			
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
				battle.setWinner((battle.isOffenderEliminated() ? 
				  	battle.getDefender() : 
					battle.getOffender())
				);
				
				Hex associatedHex = battle.getAssociatedHex();
				Player winner = battle.getWinner();
				
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
				
				notifyAllClients(room, Notifications.BATTLE_ENDED);
			}
			else {
				// Notify opponents
				notifyOtherClients(room, Notifications.BATTLE_UPDATED, battle.getCurrentPlayer());
			}
		}
	}

	@Override
	public void battleTurnEnded(String roomName, Battle battle) throws JSONRPC2Error {
		synchronized (GAME_ROOMS.get(roomName)) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			room.setBattle(battle);
			
			// Players decided to continue the battle, check the winning conditions.
//			if (battle.getBattlePhase().equals(BattlePhase.RETREAT) && battle.getCurrentPlayer().equals(battle.getDefender())) {
//				System.out.println("CHECK CONDITIONS");
//			}

			notifyClients(room, Notifications.BATTLE_TURN_ENDED, room.getBattle().getCurrentPlayer());
		}
	}
}
