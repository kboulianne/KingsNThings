/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.services;

import java.util.ArrayList;
import java.util.List;
import com.model.Player;
import com.model.game.Game;
import com.model.game.phase.GamePlay;

/**
 *
 * @author kurtis
 */
public class GameService {

	/**
	 * The system-wide instance of the Game class, sync'd via the network.
	 *
	 * Will use the observer pattern to automatically fire changes to the server.
	 */
	private final Game game;

	/**
	 * Creates a new GameService instance. Private for singleton pattern.
	 */
	private GameService() {
		game = new Game();
		initialize();
	}

	/**
	 * Responsible for game state initialization.
	 */
	private void initialize() {
		//	test_CreateOpponents();
		test_CreatePlayers();
		//	game.test_PlayerOrder();
	}

	/**
	 * Inner class responsible for holding singleton instance. Initialized once.
	 */
	private static class SingletonHolder {

		public static final GameService INSTANCE = new GameService();
	}

	public static GameService getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("This is a singleton! Can't clone.");
	}

	// Test methods
	private void test_CreatePlayers() {
		game.setCurrentPlayer(new Player(Player.PlayerId.ONE, "Player 1"));
		game.setOpponent1(new Player(Player.PlayerId.TWO, "Player 2"));
		game.setOpponent2(new Player(Player.PlayerId.THREE, "Player 3"));
		game.setOpponent3(new Player(Player.PlayerId.FOUR, "Player 4"));

		// Initial PlayerOrder.
		List<Player> players = new ArrayList<>();
		players.add(game.getCurrentPlayer());
		players.add(game.getOpponent1());
		players.add(game.getOpponent2());
		players.add(game.getOpponent3());
		

		game.setPlayerOrder(players);
//	game.setCurrent(game.getPlayer());
	}

	// TODO remove me and handle/ return stuff from service.
	public Game getGame() {
		return game;
	}

	public void startGame() {
		// Start executing first phase.
		GamePlay.getInstance().next();
	}

	public void roll() {
		game.rollDice();
		// Check the required action in gameplay, then roll
		// TODO Abstract this
		// TESTING FOR NOW
//		synchronized (GamePlay.getInstance().actions) {
//			try {
//				if (GamePlay.getInstance().actions.take() == GameAction.ROLL) {
//					// Roll and unlock
//					game.rollDice();
//
//					GamePlay.getInstance().actions.notifyAll();
//				}
//			} catch (InterruptedException ex) {
//				Logger.getLogger(GameService.class.getName()).log(Level.SEVERE, null, ex);
//			}
//		}
	}

	public void endTurn() {
		// TODO remove this from game class.
		//game.endTurn();

	}

}
