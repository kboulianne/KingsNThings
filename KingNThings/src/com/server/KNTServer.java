package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.model.GameRoom;
import com.model.Player;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;

public class KNTServer {
	private static ServerSocket SERVER;
	// TODO Move me to a common class so that client and server can use me.
	private static final ExecutorService THREAD_POOL;
	
	/** Maps players to their connection handlers. */
	public static final Map<String, PlayerConnection> PLAYERS;
	public static final Map<String, GameRoom> GAME_ROOMS;
	/** Notifications generated by the request with UUID. */
	
	// TODO Need to handle client disconnections and thread pool cleanup
	static {
		THREAD_POOL = Executors.newFixedThreadPool(200);
		
		PLAYERS = Collections.synchronizedMap(new HashMap<String, PlayerConnection>());
		GAME_ROOMS = Collections.synchronizedMap(new HashMap<String, GameRoom>());
	}
	
	public static void listen(int port) throws IOException, InterruptedException {
		System.out.println("Server listening on port " + port);
		
		try {
			SERVER = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (true) {
			Socket playerSocket = SERVER.accept();
			
			System.out.println("Client connected: " + playerSocket.getInetAddress().getHostAddress());

			PlayerConnection connection = new PlayerConnection(playerSocket);
			connection.startOn(THREAD_POOL);

		}
	}
	
	public static void notifyAllClients(GameRoom room, JSONRPC2Notification notification) {
		synchronized (PLAYERS) {
			PLAYERS.get(room.getHost().getName()).notifyClient(notification);
			
			for (Player p : room.getPlayers()) {
				PLAYERS.get(p.getName()).notifyClient(notification);
			}
		}
	}
	
	public static void notifyClients(ServerGameRoom room, JSONRPC2Notification not, Player...players) {
		synchronized (PLAYERS) {
			for (Player p : players) {
				PLAYERS.get(p.getName()).notifyClient(not);
			}
		}
	}
	
	public static void notifyClientsExclude(ServerGameRoom room, JSONRPC2Notification notification, Player exclude) {
		// Exclude notifying playerName
		if (!room.getHost().getName().equals(exclude.getName())) {
			synchronized (PLAYERS) {
				PLAYERS.get(room.getHost().getName()).notifyClient(notification);
			}
		}
		
		synchronized (PLAYERS) {
			for (Player p : room.getPlayers()) {
				if (!p.getName().equals(exclude.getName())) {
						PLAYERS.get(p.getName()).notifyClient(notification);			
				}
			}
		}
	}
	
	//TODO: KNTServer#shutdown()
	
	public static void main(String[] args) throws IOException, InterruptedException {
//		KNTServer server = new KNTServer();
		int port = 6868;
		
		// Loop the args and parse --port=
		for (String s : args) {
			if (s.startsWith("--port=")) {
				port = Integer.parseInt(s.replace("--port=", ""));
			}
		}
		
		KNTServer.listen(port);
	}
}