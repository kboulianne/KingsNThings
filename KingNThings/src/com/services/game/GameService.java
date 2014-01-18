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
	test_CreateOpponents();
	test_CreatePlayer();
	
	// Initialize
	initializeBoard();
    }

    private void initializeBoard() {
	// Send event to server, wait for response
	// use response and build board.
	
	Board board = game.getBoard();
	
	
	
	// Create hexes, all face down. Ui handles placement.
	// Hex pool to choose tile from.
	List<Hex> hexPool = new ArrayList<>();
	// 48 tiles. Need to "set aside" 4 sea hexes, distribute rest
	// randomly
	
	// Sea hexes
	for (int i = 0 ; i < 4 ; i ++) {
	    hexPool.add(new Hex(Hex.SEA));
	}
	
	// Others (Should be 44 tiles instead of 42?)
	for (int i = 0 ; i < 6 ; i ++) {
	    hexPool.add(new Hex(Hex.DESERT));
	    hexPool.add(new Hex(Hex.FOREST));
	    hexPool.add(new Hex(Hex.FROZEN_WASTE_HEX));
	    hexPool.add(new Hex(Hex.JUNGLE_HEX));
	    hexPool.add(new Hex(Hex.MOUNTAIN));
	    hexPool.add(new Hex(Hex.PLAINS));
	    hexPool.add(new Hex(Hex.SWAMP));
	}
	
	
	
	//FIXME: Hardcoded
	int rand = 0;
	Random rnd = new Random();
	// Choose Hexes at random from the pool and add to the board.
	for (int i = 0 ; i < board.getHexNum() ; i ++) {
	    rand = rnd.nextInt(hexPool.size());
	    
	    // Add hex to position i in board hex array.
	    board.setHex(hexPool.get(rand), i);
	    System.out.println("Picked: " + hexPool.get(rand).getType());
	    
	    hexPool.remove(rand);
	}
	
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
	
	game.setOpponent1(new Player("Frank"));
	game.setOpponent2(new Player("Joe"));
	game.setOpponent3(new Player("Roxanne"));
	
	return players;
    }
    
    private void test_CreatePlayer() {
	game.setPlayer(new Player("Bob"));
	game.setCurrent(game.getPlayer());
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
	
	// send ROLL event.
    }
    
    public void endPhase() {

    }
    
    
    public static void main(String[] args) {
//	GameService.getInstance().initializeBoard();
    }
}
