/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.services.game;

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
	
	public static GameService getInstance() {
	    return SingletonHolder.INSTANCE;
	}
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
	throw new CloneNotSupportedException("This is a singleton!");
    }
    
    /**
     *  Responsible for game state initialization.
     */
    public void initialize() {
	
    }
    
}
