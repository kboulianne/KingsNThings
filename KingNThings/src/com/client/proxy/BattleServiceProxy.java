package com.client.proxy;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.model.Battle;
import com.model.Hex;
import com.model.Player;
import com.server.services.IBattleService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

public class BattleServiceProxy extends ProxyBase implements IBattleService {

	public BattleServiceProxy(LinkedBlockingQueue<JSONRPC2Response> in,
			LinkedBlockingQueue<JSONRPC2Request> out) {
		super(in, out);
	}

	@Override
	public Battle startBattle(String roomName, Player attacker,
			List<Player> defenders, Hex hex) throws JSONRPC2Error {
		return invokeOnServer("startBattle", Battle.class, attacker, defenders, hex);
	}
}