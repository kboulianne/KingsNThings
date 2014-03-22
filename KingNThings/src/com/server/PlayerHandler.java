package com.server;

import com.model.Player;
import com.presenter.Util;
import com.server.services.IPlayerService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;

import static com.server.KNTServer.*;

public class PlayerHandler extends KNTRequestHandler implements IPlayerService {

	@Override
	public String[] handledRequests() {
		return new String[] {"connect"};
	}

	// This handler is special. It needs the context.
	@Override
	public JSONRPC2Response process(JSONRPC2Request request, MessageContext requestCtx) {
		// If method is connect, set the player in context to be the result of a call to connect
	
		JSONRPC2Response res;
		try {
			res = super.respond(request);
			
			if (request.getMethod().equals("connect")) {
				// Set player contained in context
				RequestContext ctx = (RequestContext)requestCtx;
				Player p = Util.GSON.fromJson((String)res.getResult(), Player.class);
				// Map player to its connection.
				PLAYERS.put(p.getName(), ctx.getPlayerConnection());
			}

		} catch (JSONRPC2Error e) {
			res = new JSONRPC2Response(e, request.getID());
		}
		
		return res;
	};
	
	
	@Override
	public Player connect(String name) {
		Player p = new Player(name);
		return p;
	}

}
