package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.model.Game;
import com.model.GameRoom;
import com.model.Player;

public class KNTServer {
	private static final ServerSocket SERVER;
	// TODO Move me to a common class so that client and server can use me.
	private static final ExecutorService THREAD_POOL;
	
	/** Maps players to their connection handlers. */
	static final Map<String, PlayerConnection> PLAYERS;
	static final Map<String, GameRoom> GAME_ROOMS;
	// It does not make sense to keep Game in GameRoom on the client side.
	// Track game instances here.
//	static final Map<String, Game> GAMES;
	
	// TODO Need to handle client disconnections and thread pool cleanup
	static {
		THREAD_POOL = Executors.newFixedThreadPool(200);
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(6868);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PLAYERS = Collections.synchronizedMap(new HashMap<String, PlayerConnection>());
		GAME_ROOMS = Collections.synchronizedMap(new HashMap<String, GameRoom>());
		
		SERVER = server;
	}
	
	public void listen() throws IOException, InterruptedException {
		System.out.println("Server listening on port 6868");
		while (true) {
			Socket playerSocket = SERVER.accept();
			
			System.out.println("Client connected: " + playerSocket.getInetAddress().getHostAddress());

			// Split into two threads, one read, one write.
			
			// TODO: Cleanup threads when clients disconnect
//			PlayerRunnable playerThread = new PlayerRunnable(playerSocket);
			PlayerConnection connection = new PlayerConnection(playerSocket);
			connection.startOn(THREAD_POOL);
			
//			Thread.sleep(3000);
//			for (int i = 1 ; i <= 3 ; i ++) {
//				connection.putMessage("Message " + i);
//			}
		}
	}
	
	//TODO: KNTServer#shutdown()
	
	public static void main(String[] args) throws IOException, InterruptedException {
		KNTServer server = new KNTServer();
		
		server.listen();
	}
}