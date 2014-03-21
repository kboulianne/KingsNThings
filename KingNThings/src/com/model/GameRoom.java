package com.model;

import java.util.ArrayList;
import java.util.List;

import com.model.Player.PlayerId;

public class GameRoom {
	/** The name of this game room. */
	private String name;
	/** The player hosting this game room.*/
	protected Player host;
	/** Guest players. */
	//TODO: Keep these in Game?
	protected List<Player> players;
	/** Transient so that GSON does not serialize it. In here for simplicity,
	 * GameService is responsible for accessing this. 
	 */
//	private transient Game game;
	
	private boolean started;
	
	/**
	 * A game room requires a name, and Player instance.
	 * @param name
	 */
	public GameRoom(String name, Player host) {
		this.name = name;
		this.host = host;
		
		// Assign ID 1 to host
		host.setPlayerID(PlayerId.ONE);
		
		players = new ArrayList<>();
	}
	

	/**
	 * Gets the unique name for this game room.
	 * @return The name of this GameRoom instance.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Adds the specified player to this game room.
	 * 
	 * @return Returns true if the game room is full.
	 */
	public void addPlayer(final Player p) {
		// Cannot have more than 3 opponents.
		if (!isFull()) {
			// Assign ID according to player size
			p.setPlayerID(PlayerId.values()[players.size() + 1]);
			players.add(p);
		}
	}
	
	/**
	 * Determines whether or not this instance has the maximum number
	 * of players.
	 * @return
	 */
	public boolean isFull() {
		return players.size() >= 3;
	}
	
	/**
	 * Gets the player hosting this Game.
	 * @return The host player.
	 */
	public final Player getHost() {
		return host;
	}
	
	/**
	 * Gets the players which joined the GameRoom hosted by the player.
	 * @return The list of players which joined the game.
	 */
	public final List<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Determines whether the game is 
	 * @return
	 */
	public final boolean hasStarted() {
		return started;
	}
	
	/**
	 * Marks this GameRoom as started. This means the game is in progress.
	 * @param s The started state.
	 */
	public final void setStarted(boolean s) {
		started = s;
	}
	
	@Override
	public String toString() {
		return name + " hosted by: " + host.getName();
	}
}