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

public class KNTServer {
	private static final ServerSocket SERVER;
	// TODO Move me to a common class so that client and server can use me.
	private static final ExecutorService THREAD_POOL;
	
	static final Map<String, GameRoom> GAME_ROOMS;
	// It does not make sense to keep Game in GameRoom on the client side.
	// Track game instances here.
//	static final Map<String, Game> GAMES;
	
	// TODO Need to handle client disconnections and thread pool cleanup
	static {
		THREAD_POOL = Executors.newFixedThreadPool(100);
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(6868);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GAME_ROOMS = Collections.synchronizedMap(new HashMap<String, GameRoom>());
//		GAMES = Collections.synchronizedMap(new HashMap<String, Game>());
		
		SERVER = server;
	}
	
	public void listen() throws IOException {
		System.out.println("Server listening on port 6868");
		while (true) {
			Socket playerSocket = SERVER.accept();
			
			System.out.println("Client connected: " + playerSocket.getInetAddress().getHostAddress());

			// TODO: Cleanup threads when clients disconnect
			PlayerRunnable playerThread = new PlayerRunnable(playerSocket);
			
			
			THREAD_POOL.execute(playerThread);
		}
	}
	
	//TODO: KNTServer#shutdown()
	
	public static void main(String[] args) throws IOException {
		KNTServer server = new KNTServer();
		
		server.listen();
	}
}