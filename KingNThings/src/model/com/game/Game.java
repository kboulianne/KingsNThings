/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game;

import java.util.LinkedList;
import java.util.List;
import model.com.Board;
import model.com.Die;
import model.com.Player;

/** Main entry point for the game logic.
 *
 * @author kurtis
 */
public class Game {
    // Simplifies databinding.
    private Player opponent1;
    private Player opponent2;
    private Player opponent3;
    /** The player owning "this" Game instance. */
    private Player player;
    /** The player who's is currently playing his/her turn. */
    private Player current;
    private Board board;
    // Easier for data binding.
    private Die die1;
    private Die die2;
    
    private int mode;
    public static final int MODE_FOUR_PLAYER = 1,
	    MODE_TWO_THREE_PLAYER = 2;
    
    public Game() {
	mode = MODE_FOUR_PLAYER;
	
	// TODO: Factory for 2 or 4 player.
	board = new Board();
	
	// Initialize the dice
	die1 = new Die();
	die2 = new Die();
    }
    
    public final Player getPlayer() {
	return player;
    }

    public void setPlayer(final Player player) {
	this.player = player;
    }
    
    public Player getCurrent() {
	return current;
    }
    
    public void setCurrent(final Player current) {
	this.current = current;
    }
    
    public Die getDie1() {
	return die1;
    }
    
    public final void setDie1(final Die die) {
	this.die1 = die;
    }
    
    public Die getDie2() {
	return die2;
    }
    
    public final void setDie2(final Die die) {
	die2 = die;
    }

    public Board getBoard() {
	    return board;
    }

    public void setBoard(Board board) {
	    this.board = board;
    }

    public int getMode() {
	    return mode;
    }

    public final void setMode(int mode) {
	    this.mode = mode;
    }
    
    public Player getOpponent1() {
	return opponent1;
    }

    public void setOpponent1(Player opponent1) {
	this.opponent1 = opponent1;
    }

    public Player getOpponent2() {
	return opponent2;
    }

    public void setOpponent2(Player opponent2) {
	this.opponent2 = opponent2;
    }

    public Player getOpponent3() {
	return opponent3;
    }

    public void setOpponent3(Player opponent3) {
	this.opponent3 = opponent3;
    }
}
