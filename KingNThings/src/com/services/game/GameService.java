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
    // This class is responsible for managing game state and networking
    
    private Game game;
    
    private GameService() {
	game = new Game();
    }
    
    private static class SingletonHolder {
	public static final GameService INSTANCE = new GameService();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
	throw new CloneNotSupportedException("This is a singleton!");
    }
    
    /**
     *  Responsible for game state initialization.
     */
    public void initialize() {
	game.setOpponents(test_CreateOpponents());
	game.setPlayer(test_CreatePlayer());
    }
    
    
    private List<Player> test_CreateOpponents() {
	LinkedList<Player> players = new LinkedList<>();
	
	players.add(new Player("Frank"));
	players.add(new Player("Joe"));
	players.add(new Player("Roxanne"));
	
	
//		String currentPlayersName = "Frank";
//	String otherPlayerName1 = "Joe";
//	String otherPlayerName2 = "Roxanne";
//	String otherPlayerName3 = "Henry";
	
	return players;
    }
    
    private Player test_CreatePlayer() {
	return new Player("Henry");
    }
    
    public Game getGame() {
	return game;
    }
    
    public static GameService getInstance() {
	return SingletonHolder.INSTANCE;
    }
}
