package model.com.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import model.com.Board;
import model.com.Die;
import model.com.Hex;
import model.com.Player;
import model.com.game.phase.IPhaseStrategy;
import model.com.game.phase.GamePlay;
import model.com.game.phase.init.PlayerOrderPhase;
import model.com.game.phase.init.StartingPosPhase;

/**
 *  This is the game model. Represents the Game's "State"
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
    private Player currentPlayer;
    /** The game board representing hex tiles and all their contents. */
    private Board board;
    // Easier for data binding.
    private Die die1;
    private Die die2;
    private int mode;
    public static final int 
	    MODE_FOUR_PLAYER = 1,
	    MODE_TWO_THREE_PLAYER = 2;
    private GamePlay gamePlay;
//    private Set<IPhaseStrategy> initPhases;
    private Iterator<Player> nextPlayerIt;
    private List<Player> playerOrder;
    
    // Constructors & Initializer Methods ==============================================================================
    /**
     *	Creates a new Game instance defaulting to Four player mode.
     */
    //TODO not supposed to expose this (make protected?)
    public Game() {
	mode = MODE_FOUR_PLAYER;
	
	// Initialize the dice
	die1 = new Die();
	die2 = new Die();
	
	// Player order is arbitrary in the beginning.
	playerOrder = new ArrayList<>();
//	nextPlayerIt = playerOrder.iterator();
	
	gamePlay = new GamePlay();
	
	// TODO: Factory for 2 or 4 player.
	board = new Board(Board.NumberOfHexes.THIRTY_SEVEN);
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
	    board.addHex(hexPool.get(rand));
//	    System.out.println("Picked: " + hexPool.get(rand).getType());
	    
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
    public final Player getCurrentPlayer() { return currentPlayer; }
    
    /**
     *	Sets the Player which is currently executing their turn.
     * @param current  The new player to set.
     */
    public final void setCurrent(final Player current) { this.currentPlayer = current; }

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
     * Gets the phase that the players must currently go through.
     * @return The current phase.
     */
    public final GamePlay getPhase() { return gamePlay; }
    
    /**
     * Sets the new phase that the players will go through.
     * @param phase The phase
     */
    public final void setPhase(final GamePlay phase) { this.gamePlay = phase; }
    
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
    
    public final int diceTotal() {
	return die1.getValue() + die2.getValue();
    }
    
    
    // Behaviour Methods ===============================================================================================
    /**
     * Rolls the dice and notifies the server.
     */
    public void rollDice() {
	die1.roll();
	die2.roll();
    }
    
    public final void nextPlayer() {
	
	
	// Get next in iterator
	if (!nextPlayerIt.hasNext()) {
	    nextPlayerIt = playerOrder.iterator();
	    // Notify GamePlay that it should switch phases?
	}
	currentPlayer = nextPlayerIt.next();
    }
    
//    public final void nextPhase() {
//	// Update the player order if phase is PlayerOrderPhase
//	// TODO put in phase end method in IPhaseStrategy?
//	if (currentPhase.getPhaseLogic() instanceof PlayerOrderPhase) {
//	    PlayerOrderPhase phase = (PlayerOrderPhase)currentPhase.getPhaseLogic();
//	    playerOrder = phase.getPlayerOrder();
//	    // Update the iterator
//	    nextPlayerIt = playerOrder.iterator();
//	    
//	    currentPlayer = nextPlayerIt.next();
//	}
//	
//	
//	
//	// Simply change the behaviour of the phase.
//	
//	// TODO Avoid using instanceof
//	// Starting Pos GamePlay.
////	if (phase. instanceof StartingPosPhase)
//	
//	// Keep an iterator and reset it when reaches the end of set?
//    }
  
    // TODO use Phase class to pass data to GamePlay and next?
    public void endTurn() {
	// Execute the phase logic
	//TODO Rename, ambiguous since it modifies this instance.
	gamePlay.next();
    }
    
//    public final void determinePlayerOrder() {
//	// TODO wrap into a template pattern? Repeated several times
//	// or simply do executePhase logic
////	if (gamePlay.getPhaseLogic() instanceof PlayerOrderPhase) {
////	    
////	    // TODO Tell user to roll. Binding on "Action" object?
////	    // When it changes, display in UI.
////	    gamePlay.getPhaseLogic().executePhase(null);
////	    
////	}
////	else {
////	    
////	}
//    }
    
    public final void selectStartPosition(final Hex start) {
//	// Make sure the current phase is StartingPosPhase then executePhase to update game.
//	if (gamePlay.getPhaseLogic() instanceof StartingPosPhase) {
//	    // FIXME why doesn't the executePhase parameter take Hex? It takes Object
//	    // It does throw an exception if it is not a Hex
//	    gamePlay.getPhaseLogic().executePhase(start);
//	}
//	else {
//	    // throw exception
//	}
    }

    //TODO startGame() -> signals start of phase?
    
    public final boolean isLastPlayer() {
	// If iterator has no next, then last player?
	return !nextPlayerIt.hasNext();
    }
    
    public final void setPlayerOrder(final Collection<Player> playersHighToLow) {
	// Contains all four players.
	this.playerOrder = new ArrayList<>(playersHighToLow);
	// Reset Iterator to the new playerOrder
	nextPlayerIt = playerOrder.iterator();
	
	// TODO change the player ID's? That way GamePlay can check if it is player 4's turn and move to the next phase.
	
	// Set current to the first player in the list if needed
	if (currentPlayer == null)
	    currentPlayer = nextPlayerIt.next();
    }
}
