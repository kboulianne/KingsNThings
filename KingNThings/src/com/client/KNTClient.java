package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.client.proxy.GameRoomServiceProxy;
import com.model.Player;
import com.model.Player.PlayerId;
import com.server.services.IGameRoomService;

/*
 * Clients will have one instance of this in the Application.
 * 
 * Hold remote proxy(ies) of server objects.
 */
public class KNTClient {
	private Socket socket;
	private GameRoomServiceProxy proxy;
	
	/**
	 * Creates a new connection to the game server with specified host and port.
	 * 
	 * @param host The host to connect to.
	 * @param port The destination port.
	 */
	public KNTClient(String host, int port) {
		try {
			socket = new Socket(host, port);
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			
			// Wait for server to respond connected. Just for synchronization and to avoid potential problems.
			reader.readLine();
			
			proxy = new GameRoomServiceProxy(reader, writer);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// get service methods
	public final IGameRoomService getGameRoomService() {
		return proxy;
	}
	
//	public static void main(String[] args) throws InterruptedException {
//		KNTClient client = new KNTClient("localhost", 6868);
//		
//		client.proxy.createGameRoom("A test room", 
//				new Player(PlayerId.ONE, "the hosting player"));
//		System.out.println(client.proxy.getGameRooms());
//				
//		
//		// Client can't terminate otherwise socket gets closed, readLine() on server goes crazy
//		// by continuously returning null.
//		//TODO For testing. Actual client will not close socket until the object is disposed.
//		while (true) {
//			Thread.sleep(1000);
//		}
//	}
}
