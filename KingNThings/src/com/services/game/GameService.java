/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.services.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import model.com.Board;
import model.com.Hex;
import model.com.Player;
import model.com.game.Game;

/**
 *
 * @author kurtis
 */
public class GameService {
    /** The system-wide instance of the Game class, sync'd via
     *	the network.
     * 
     * Will use the observer pattern to automatically fire changes
     * to the server.
     */
    private final Game game;
    
    /**
     * Creates a new GameService instance. Private for singleton
     * pattern.
     */
    private GameService() {
	game = new Game();
	
	initialize();
    }
    
    /**
     *  Responsible for game state initialization.
     */
    private void initialize() {
	test_CreateOpponents();
	test_CreatePlayer();
	game.test_PlayerOrder();
    }

    
    
    /**
     * Inner class responsible for holding singleton instance.
     * Initialized once.
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
    private void test_CreatePlayer() {
	game.setPlayer(new Player(Player.PlayerId.ONE, "Bob"));
	game.setCurrent(game.getPlayer());
    }
    
    private void test_CreateOpponents() {
	game.setOpponent1(new Player(Player.PlayerId.TWO,"Frank"));
	game.setOpponent2(new Player(Player.PlayerId.THREE,"Joe"));
	game.setOpponent3(new Player(Player.PlayerId.FOUR,"Roxanne"));
    }
    
    public Game getGame() {
	return game;
    }
}
