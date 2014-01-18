package model.com.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.com.Board;
import model.com.Die;
import model.com.Hex;
import model.com.Player;
import model.com.game.phase.Phase;

/** Main entry point for the game logic.
 *
 * @author kurtis
 */
public final class Game {
    // Simplifies databinding.
    private Player opponent1;
    private Player opponent2;
    private Player opponent3;
    /** The player owning "this" Game instance. */
    private Player player;
    /** The player who's is currently playing his/her turn. */
    private Player current;
    /** The game board representing hex tiles and all their contents. */
    private Board board;
    // Easier for data binding.
    private Die die1;
    private Die die2;
    private int mode;
    public static final int 
	    MODE_FOUR_PLAYER = 1,
	    MODE_TWO_THREE_PLAYER = 2;
    
    private List<Phase> initPhases;
    
    // Constructors & Initializer Methods ==============================================================================
    /**
     *	Creates a new Game instance defaulting to Four player mode.
     */
    public Game() {
	mode = MODE_FOUR_PLAYER;
	
	// TODO: Factory for 2 or 4 player.
	board = new Board(Board.NumberOfHexes.THIRTY_SEVEN);
	
	// Initialize the dice
	die1 = new Die();
	die2 = new Die();
	
	initializeBoard();
    }
    
    /**
     * Initialization logic for the game board.
     */
    private void initializeBoard() {
	/* Create all hex instances. The board determines if they are face down or up.  Put all instances in a pool so
	 * they can be picked and assigned at random.
	 * 
	 */
	List<Hex> hexPool = new ArrayList<>();
	// 48 tiles. Need to "set aside" 4 sea hexes, distribute rest randomly
	// Sea hexes
	for (int i = 0 ; i < 4 ; i ++) {
	    hexPool.add(new Hex(Hex.SEA));
	}
	
	// Others
	// TODO: Should be 44 tiles instead of 42?
	for (int i = 0 ; i < 6 ; i ++) {
	    hexPool.add(new Hex(Hex.DESERT));
	    hexPool.add(new Hex(Hex.FOREST));
	    hexPool.add(new Hex(Hex.FROZEN_WASTE_HEX));
	    hexPool.add(new Hex(Hex.JUNGLE_HEX));
	    hexPool.add(new Hex(Hex.MOUNTAIN));
	    hexPool.add(new Hex(Hex.PLAINS));
	    hexPool.add(new Hex(Hex.SWAMP));
	}
	
	// Choose Hexes at random from the pool and add to the board.
	int rand = 0;
	Random rnd = new Random();
	
	for (int i = 0 ; i < board.getHexNum() ; i ++) {
	    rand = rnd.nextInt(hexPool.size());
	    
	    // Add hex to position i in board hex array.
	    board.setHex(hexPool.get(rand), i);
	    System.out.println("Picked: " + hexPool.get(rand).getType());
	    
	    hexPool.remove(rand);
	}
	
    }
    
    // Getters and Setters =============================================================================================
    /**
     *	Gets the player owning this Game instance. (Player "playing" this game)
     * @return The player
     */
    public final Player getPlayer() { return player; }

    /**
     *	Sets the player owning this instance of the Game.
     * @param player The player to set.
     */
    public void setPlayer(final Player player) { this.player = player; }
    
    /**
     *	Gets the Player which is currently executing their turn.
     * @return The current player.
     */
    public final Player getCurrent() { return current; }
    
    /**
     *	Sets the Player which is currently executing their turn.
     * @param current  The new player to set.
     */
    public final void setCurrent(final Player current) { this.current = current; }

    /**
     *	Gets the first die instance.
     * @return The die.
     */
    public final Die getDie1() { return die1; }
    
    /**
     * Sets the first die instance.
     * @param die The new die.
     */
    public final void setDie1(final Die die) { this.die1 = die; }
    
    /**
     * Gets the second die instance.
     * @return The die.
     */
    public final Die getDie2() { return die2; }
    
    /**
     * Sets the second die instance.
     * @param die The new die.
     */
    public final void setDie2(final Die die) { die2 = die; }

    /**
     * Gets the board instance for this Game.
     * @return The board.
     */
    public final Board getBoard() { return board; }

    /**
     * Sets the new board instance for this Game.
     * @param board The new Board.
     */
    public final void setBoard(final Board board) { this.board = board; }

    /**
     * Gets the current play mode.
     * <pre>
     * Either MODE_FOUR_PLAYER (default) or MODE_TWO_THREE_PLAYER.
     * </pre>
     * @return The play mode.
     */
    public final int getMode() { return mode; }

    /**
     * Sets the current play mode.
     * @param mode The new mode to set.
     */
    public final void setMode(final int mode) {
	if (mode == MODE_TWO_THREE_PLAYER || mode == MODE_FOUR_PLAYER)
	    this.mode = mode;
    }
    
    /**
     * Gets the first opponent.
     * @return The opponent.
     */
    public final Player getOpponent1() { return opponent1; }

    /**
     * Sets the first opponent instance.
     * @param opponent1 The new opponent.
     */
    public final void setOpponent1(final Player opponent1) { this.opponent1 = opponent1; }

    /**
     * Gets the second opponent.
     * @return The opponent.
     */
    public final Player getOpponent2() { return opponent2; }

    /**
     * Sets the second opponent instance.
     * @param opponent2 The new opponent.
     */
    public final void setOpponent2(final Player opponent2) { this.opponent2 = opponent2; }

    /**
     * Gets the third opponent.
     * @return The opponent.
     */
    public final Player getOpponent3() { return opponent3; }

    /**
     * Sets the third opponent instance.
     * @param opponent3 The new opponent.
     */
    public void setOpponent3(Player opponent3) { this.opponent3 = opponent3; }
    
       
    
    
    
    /**
     * Rolls the dice and notifies the server.
     */
    public void rollDice() {
	die1.roll();
	die2.roll();
    }
    
    private void createInitPhases() {
	initPhases = new ArrayList<>();
	
	
    }
}
