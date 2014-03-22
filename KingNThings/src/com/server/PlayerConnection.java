package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import com.model.Player;
import com.presenter.Util;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Notification;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.server.Dispatcher;

public class PlayerConnection {

	// The player owning this connection.
	private Socket client;
	private Runnable readRunnable;
	private Runnable writeRunnable;
	// Messages to send to the end-point.
	private final LinkedBlockingQueue<String> messages;
	private static final Dispatcher DISPATCHER;
	
	static {
		DISPATCHER = new Dispatcher();
		DISPATCHER.register(new PlayerHandler());
		DISPATCHER.register(new GameRoomHandler());
	}
	
	public PlayerConnection(Socket socket) {
		client = socket;
		messages = new LinkedBlockingQueue<>();
		
		readRunnable = createReadRunnable();
		writeRunnable = createWriteRunnable();
	}
	
	/**
	 * Create the Runnable that reads from the socket.
	 * 
	 * @return The Runnable.
	 */
	private Runnable createReadRunnable() {
		return new Runnable() {
			
			@Override
			public void run() {
				try {
					BufferedReader  reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

					// Wait for client to send Player name
//					System.out.println("Waiting for player name.");
					String json = reader.readLine();
					// Null means client disconnected.
					if (json == null) return;
					
					JSONRPC2Request req = JSONRPC2Request.parse(json);
					String res = DISPATCHER.process(req, new RequestContext(PlayerConnection.this)).toJSONString();
					// Put the response on the queue
					messages.put(res);
					
					// Now ready for request processing.
					while (true) {
						
						try {
//							System.out.println("WAITING FOR DATA");
							json = reader.readLine();
							System.out.println("Read: " + json);
							
							req = JSONRPC2Request.parse(json);
							res = DISPATCHER.process(req, new RequestContext(PlayerConnection.this)).toJSONString();
							messages.put(res);
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONRPC2ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				

			}
		};
	}
	
	/**
	 * Creates the Runnable that writes to the socket. Waits for messages to be put on the queue.
	 * @return
	 */
	private Runnable createWriteRunnable() {
		return new Runnable() {
			
			@Override
			public void run() {
				try {
					PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
				
					// TODO Auto-generated method stub
					while (true) {
						// Lock until there is something to write.
//						System.out.println("Waiting for messages to be put in queue.");
						String message = messages.take();
						writer.println(message);
						System.out.println("Wrote: " + message);

						
					}
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		};
	}
	
	public void notifyClient(JSONRPC2Notification notify) {
		try {
			messages.put(notify.toJSONString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startOn(final ExecutorService service) {
		service.execute(readRunnable);
		service.execute(writeRunnable);
	}
}
