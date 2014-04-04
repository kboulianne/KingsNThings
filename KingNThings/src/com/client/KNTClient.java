package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.client.proxy.BattleServiceProxy;
import com.client.proxy.GameRoomServiceProxy;
import com.client.proxy.GameServiceProxy;
import com.client.proxy.PlayerServiceProxy;
import com.server.services.IBattleService;
import com.server.services.IGameRoomService;
import com.server.services.IGameService;
import com.server.services.IPlayerService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Message;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.Dispatcher;

public class KNTClient {
	private Socket socket;
	private Set<UUID> pendingRequests;
	private final ExecutorService socketThreads = Executors.newFixedThreadPool(2);
	private final PlayerServiceProxy playerProxy;
	private final GameRoomServiceProxy gameRoomProxy;
	private final GameServiceProxy gameProxy;
	private final BattleServiceProxy battleProxy;
	private final LinkedBlockingQueue<JSONRPC2Request> outputMessages;
	private final LinkedBlockingQueue<JSONRPC2Response> inputMessages;
	
	private static final Dispatcher DISPATCHER;
	
	private static final int DEFAULT_PORT = 6868;
	
	static {
		DISPATCHER = new Dispatcher();
		
		DISPATCHER.register(new GameRoomNotificationHandler());
		DISPATCHER.register(new GameNotificationHandler());
//		DISPATCHER.register(new Battle);
	}
	
	/**
	 * Creates a new connection to the game server with specified host and port.
	 * 
	 * @param host The host to connect to.
	 * @param port The destination port.
	 */
	public KNTClient(String host, Integer port) {
		pendingRequests = Collections.synchronizedSet(new HashSet<UUID>());
		inputMessages = new LinkedBlockingQueue<>();
		outputMessages = new LinkedBlockingQueue<>();
		
		PlayerServiceProxy playerProxy = null;
		GameRoomServiceProxy gameRoomProxy = null;
		GameServiceProxy gameProxy = null;
		BattleServiceProxy battleProxy = null;
		
		try {
			if (port == null)
				port = DEFAULT_PORT;
			
			socket = new Socket(host, port);
			
			playerProxy = new PlayerServiceProxy(inputMessages, outputMessages);
			gameRoomProxy = new GameRoomServiceProxy(inputMessages, outputMessages);
			gameProxy = new GameServiceProxy(inputMessages, outputMessages);
			battleProxy = new BattleServiceProxy(inputMessages, outputMessages);
			
			socketThreads.execute(createReadRunnable());
			socketThreads.execute(createWriteRunnable());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.playerProxy = playerProxy;
		this.gameRoomProxy = gameRoomProxy;
		this.gameProxy = gameProxy;
		this.battleProxy = battleProxy;
	}
	
	private Runnable createReadRunnable() {
		return new Runnable() {
			
			@Override
			public void run() {

				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					// Process data continuously.
					while (true) {
						// Puts responses in the queue.
						// Process the response and determine if it is a notification or response
						String json = reader.readLine();
						if (json == null) continue;
						
						JSONRPC2Message message = JSONRPC2Message.parse(json);
						
						if (message instanceof JSONRPC2Response) {
							JSONRPC2Response res = (JSONRPC2Response)message;
							pendingRequests.remove(UUID.fromString((String)res.getID()));
							// Add the response to the queue of received responses.
							inputMessages.put(res);
						}
						else if (message instanceof JSONRPC2Notification){
							//TODO: Check if notification requires a request to complete first.
							DISPATCHER.process((JSONRPC2Notification)message, null);
						}
						else {
							System.err.println("ERROR");
							// Error
//							JSONRPC2Error error = message.;
//							
//							throw error;
						}
						
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONRPC2ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
	}
	
	/**
	 * Runnable which processes outgoing messages.
	 * @return
	 */
	private Runnable createWriteRunnable() {
		return new Runnable() {
			
			@Override
			public void run() {
				try {
					PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
					
					while (true) {
						JSONRPC2Request req = outputMessages.take();
						// Mark request as pending
						pendingRequests.add(UUID.fromString((String)req.getID()));
						
						// Fires requests to the server.
						writer.println(req.toJSONString());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}
	
	public final IPlayerService getPlayerService() {
		return playerProxy;
	}
	
	public final IGameRoomService getGameRoomService() {
		return gameRoomProxy;
	}
	
	public final IGameService getGameService() {
		return gameProxy;
	}
	
	public final IBattleService getBattleService() {
		return battleProxy;
	}
}
