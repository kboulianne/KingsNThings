package com.server.services;

import com.model.Player;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public interface IPlayerService {
	Player connect(String name) throws JSONRPC2Error;
}
