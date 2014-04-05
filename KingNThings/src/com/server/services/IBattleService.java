package com.server.services;

import java.util.List;

import com.model.Battle;
import com.model.Hex;
import com.model.Player;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public interface IBattleService {
	/**
	 * Starts a battle for the specified room in progress, between the attacker and one or more opponents on 
	 * the specified hex.
	 * 
	 * @param roomName The name of the GameRoom for which a battle should be started between the players.
	 * @param attacker The player which triggered the battle.
	 * @param defenders The players being attacked by the attacker.
	 * @param hex The hex on which the battle takes place
	 * @return The created battle state.
	 * @throws JSONRPC2Error
	 */
	Battle startBattle(String roomName, Player attacker, List<Player> defenders, Hex hex) throws JSONRPC2Error;
	/**
	 * Gets the battle which is currently in progress in the specified game room.
	 * @param roomName
	 * @return
	 * @throws JSONRPC2Error
	 */
	Battle getOngoingBattle(String roomName) throws JSONRPC2Error;
	
	void updateBattle(String roomName, Battle battle) throws JSONRPC2Error;
	
	void battleTurnEnded(String roomName, Battle battle) throws JSONRPC2Error;
}
