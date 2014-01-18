/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.services.game;

import java.util.LinkedList;
import java.util.List;
import model.com.Player;
import model.com.game.Game;

/**
 *
 * @author kurtis
 */
public class GameService {
    // This class is responsible for managing networking
    
    private final Game game;
    
    private GameService() {
	game = new Game();
	
	initialize();
    }
    
    /**
     *  Responsible for game state initialization.
     */
    private void initialize() {
	game.setOpponents(test_CreateOpponents());
	game.setPlayer(test_CreatePlayer());
    }


    
    private static class SingletonHolder {
	public static final GameService INSTANCE = new GameService();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
	throw new CloneNotSupportedException("This is a singleton! Can't clone.");
    }
    

    
    
    private List<Player> test_CreateOpponents() {
	LinkedList<Player> players = new LinkedList<>();
	
	players.add(new Player(Player.PlayerId.TWO,"Frank"));
	players.add(new Player(Player.PlayerId.THREE,"Joe"));
	players.add(new Player(Player.PlayerId.FOUR,"Roxanne"));
	
	return players;
    }
    
    private Player test_CreatePlayer() {
	return new Player(Player.PlayerId.ONE,"Bob");
    }
    
    //TODO should not expose this, allows service bypassing.
    public Game getGame() {
	return game;
    }
    
    public static GameService getInstance() {
	return SingletonHolder.INSTANCE;
    }
    
    
    public void rollDice() {
	game.getDie1().roll();
	game.getDie2().roll();
    }
}
