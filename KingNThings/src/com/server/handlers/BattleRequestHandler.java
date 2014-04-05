package com.server.handlers;

import java.util.List;

import com.model.Battle;
import com.model.Hex;
import com.model.Player;
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
			
			// Notify opponents
			notifyOtherClients(room, Notifications.BATTLE_UPDATED, room.getGame().getCurrentPlayer());
		}
	}

	@Override
	public void battleTurnEnded(String roomName, Battle battle) throws JSONRPC2Error {
		synchronized (GAME_ROOMS.get(roomName)) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			room.setBattle(battle);
			// Current was swapped in client, notify new current that turn has ended.

			notifyClients(room, Notifications.BATTLE_TURN_ENDED, room.getBattle().getCurrentPlayer());
		}
	}
}
