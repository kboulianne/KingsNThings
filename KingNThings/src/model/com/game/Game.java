/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.com.game;

import java.util.List;
import model.com.Board;
import model.com.Player;

/**
 *
 * @author kurtis
 */
public class Game {
    // Game state
    private List<Player> opponents;
    /** The player owning this Game instance. */
    private Player player = new Player("Bob");
    /** The player who's is currently playing his/her turn. */
    private Player current;
    private Board board;
    
    
    public void setOpponents(List<Player> players) {
	this.opponents = players;
    }
    
    public List<Player> getOpponents() {
	return opponents;
    }
    
    public final Player getPlayer() {
	return player;
    }

    public void setPlayer(final Player player) {
	this.player = player;
    }
}
