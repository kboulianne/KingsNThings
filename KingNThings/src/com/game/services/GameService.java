/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.game.services;

import com.model.Creature;
import com.model.DesertCreature;
import com.model.Hex;
import com.model.MountainCreature;
import java.util.ArrayList;
import java.util.List;
import com.model.Player;
import com.model.SwampCreature;
import com.model.Thing;
import com.model.game.Game;

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
//	test_CreateOpponents();
	test_CreatePlayers();
        test_AddThingsAndCreatures();
//	game.test_PlayerOrder();
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
    private void test_CreatePlayers() {
	game.setCurrentPlayer(new Player(Player.PlayerId.ONE, "Bob"));
	game.setOpponent1(new Player(Player.PlayerId.TWO,"Frank"));
	game.setOpponent2(new Player(Player.PlayerId.THREE,"Joe"));
	game.setOpponent3(new Player(Player.PlayerId.FOUR,"Roxanne"));
	
	// Initial PlayerOrder.
	List<Player> players = new ArrayList<>();
	players.add(game.getOpponent2());
	players.add(game.getCurrentPlayer());
	players.add(game.getOpponent3());
	players.add(game.getOpponent1());
	
	game.setPlayerOrder(players);
//	game.setCurrent(game.getPlayer());
    }
    
    private void test_AddThingsAndCreatures() {
        Thing thing = new SwampCreature("ghost");
        Creature goblin = new MountainCreature("goblins");
        Creature dragon = new DesertCreature("olddragon");
        List<Hex> hexes = game.getBoard().getHexes();
        
        hexes.get(0).addThingToArmy(goblin, game.getCurrentPlayer().getId());
        hexes.get(0).addThingToArmy(dragon, game.getCurrentPlayer().getId());
        game.getCurrentPlayer().getBlock().addThing(thing,game.getCurrentPlayer().getName());
        game.getCurrentPlayer().getBlock().addThing(goblin,game.getCurrentPlayer().getName());
        for(int i=0;i<10;i++){
                hexes.get(0).addThingToArmy(dragon, game.getOpponent1().getId());
        }
        for(int i=0;i<5;i++){
                hexes.get(0).addThingToArmy(dragon, game.getOpponent2().getId());
        }
        for(int i=0;i<8;i++){
                hexes.get(0).addThingToArmy(dragon, game.getOpponent3().getId());
        }
        for(int i=0;i<7;i++){
                hexes.get(0).addThingToArmy(dragon, game.getCurrentPlayer().getId());
        }
    }
    
    
    public Game getGame() {
	return game;
    }
    
    public void roll() {
	// TODO remove rollDice(), allows the service to be bypassed.
	game.rollDice();
	
	// Fire towards network.
    }
    
    public void endTurn() {
	// TODO remove this from game class.
	game.endTurn();
    }
}
