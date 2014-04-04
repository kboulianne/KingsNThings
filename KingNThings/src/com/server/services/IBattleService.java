package com.server.services;

import java.util.List;

import com.model.Battle;
import com.model.Hex;
import com.model.Player;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public interface IBattleService {
	Battle startBattle(String roomName, Player attacker, List<Player> defenders, Hex hex) throws JSONRPC2Error;
}
