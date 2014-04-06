package com.client.proxy;

import java.util.concurrent.LinkedBlockingQueue;

import com.model.Player;
import com.server.services.IPlayerService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

public class PlayerServiceProxy extends ProxyBase implements IPlayerService {
	
//	public PlayerServiceProxy(BufferedReader reader, PrintWriter writer) {
//		super(reader, writer);
//	}

	public PlayerServiceProxy(LinkedBlockingQueue<JSONRPC2Response> inputMessages,
			LinkedBlockingQueue<JSONRPC2Request> outMessages) {
		super(inputMessages, outMessages);
	}

	@Override
	public Player connect(String name) throws JSONRPC2Error {
		return invokeOnServer("connect", Player.class, name);
	}

}
