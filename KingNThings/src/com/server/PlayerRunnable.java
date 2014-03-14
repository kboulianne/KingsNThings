package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.server.KNTServer.GameHandler;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.server.Dispatcher;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;

public class PlayerRunnable implements Runnable {
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	
	// Dispatcher which delegates processing to handlers
	private static final Dispatcher DISPATCHER;
	
	private static final Logger LOGGER = Logger.getLogger(PlayerRunnable.class.getName()); 
	
	static {
		DISPATCHER = new Dispatcher();
		
		DISPATCHER.register(new GameRoomHandler());
//		DISPATCHER.register(new GameHandler());
	}
	
	public PlayerRunnable(Socket player) {
		socket = player;
		
		try {
			reader = new BufferedReader(new InputStreamReader(player.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(player.getOutputStream()), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// Acknowledge connection
		writer.println(0);
		// TODO: Wait for UUID that identifies the player session uniquely?
		
		while (true) {
			try {
				String jsonRpc = reader.readLine();
				
				// Happens when socket is closed, unexpected or otherwise.
				if (jsonRpc == null) break;
				// Log the request
				LOGGER.info(jsonRpc);
				
				// Parse the request and have dispatcher call the required handler for the method name.
				JSONRPC2Request req = JSONRPC2Request.parse(jsonRpc);
				
				String res = DISPATCHER.process(req, null).toJSONString();
				LOGGER.info("Response: " + res);
				
				// Send the response.
				writer.println(res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONRPC2ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
