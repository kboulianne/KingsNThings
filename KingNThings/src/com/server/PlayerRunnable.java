package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.server.KNTServer.GameHandler;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.server.Dispatcher;

public class PlayerRunnable implements Runnable {
	/** Server-side of the socket for this player. */
	private final Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	// Dispatcher which delegates processing to handlers
	private static final Dispatcher DISPATCHER;
	
	static {
		DISPATCHER = new Dispatcher();
		
		DISPATCHER.register(new GameRoomHandler());
		DISPATCHER.register(new GameHandler());
	}
	
	public PlayerRunnable(Socket player) {
		this.socket = player;
		
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
		
		while (true) {
			try {
				String jsonRpc = reader.readLine();
				
				// Happens when socket is closed, unexpected or otherwise.
				if (jsonRpc == null) break;
				
				// Parse the request and have dispatcher call the required handler for the method name.
				JSONRPC2Request req = JSONRPC2Request.parse(jsonRpc);
				// Send the response.
				writer.println(DISPATCHER.process(req, null).toJSONString());
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