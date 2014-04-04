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
		return new String[] { "startBattle", "getOngoingBattle" };
	}

	@Override
	public Battle startBattle(String roomName, Player attacker,
			List<Player> defenders, Hex hex) throws JSONRPC2Error {
		
		synchronized (GAME_ROOMS.get(roomName)) {
			ServerGameRoom room = (ServerGameRoom) GAME_ROOMS.get(roomName);
			
			// Create the object for the current battle.
			Battle battle = new Battle(attacker, defenders.get(0), hex);
			
			room.setBattle(battle);
			
			// Allows opponents to watch the ongoing battle.
			notifyOtherClients(room, Notifications.BATTLE_STARTED, room.getGame().getCurrentPlayer());
			
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
}
