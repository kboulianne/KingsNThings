package com.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javafx.scene.image.Image;

import com.game.phase.init.PlayerOrderPhase;
import com.main.KNTAppFactory;
import com.presenter.HexFactory;
import com.util.Util;

/**
 * This is the game model. Represents the current "State" of the game
 *
 * @author kurtis
 */
public final class Game {

	/**
	 * The player who's is currently playing his/her turn.
	 */
	private Player currentPlayer;
	/**
	 * The game board representing hex tiles and all their contents.
	 */
	private Board board;
    private Die die1;
    private Die die2;
    private int mode;
    private static final int 
    	MODE_FOUR_PLAYER = 1,
	    MODE_TWO_THREE_PLAYER = 2;
    private transient List<Hex> hexPool;
    // Iterators are not serializable. Use index instead.
    private int nextPlayerIdx;
    private List<Player> playerOrder;
    //TODO: May need to serialize this.
    private transient Cup cup;
    
  	//private ArrayList<Creature> lastSelectedCreatures;
  
    public final static Image FACE_DOWN_HEX_IMAGE = new Image("view/com/assets/pics/tiles/faceddown.png");
	public final static Image START_HEX_IMAGE = new Image("view/com/assets/pics/tiles/start.png");
  	public final static Image FACE_DOWN_THING_IMAGE = new Image("view/com/assets/pics/icon-inverted.png");
 	public final static Image GOLD_IMAGE = new Image("view/com/assets/pics/gold.png");
 	public final static Image DICE_IMAGE = new Image("view/com/assets/pics/dice.png");
 	public final static Image CROWN_IMAGE = new Image("view/com/assets/pics/crown.png");
    // Constructors & Initializer Methods ==============================================================================
    
 	/**
     *	Creates a new Game instance defaulting to Four player mode.
     */
    public Game() {
		mode = MODE_FOUR_PLAYER;

		// Initialize the dice
		die1 = new Die();
		die2 = new Die();

		// Player order is arbitrary in the beginning.
		playerOrder = new ArrayList<>();
		cup = new Cup();

		// TODO: Factory for 2 or 4 player.
		board = new Board(Board.NumberOfHexes.THIRTY_SEVEN);
		
		
		//Removed for iteration 1
		HexFactory hexFactory = new HexFactory();

		hexPool = hexFactory.createHexPool(Board.NumberOfHexes.THIRTY_SEVEN);

		// Choose Hexes at random from the pool and add to the board.
		int rand = 0;
		Random rnd = new Random();
	
		// TODO: Put in board constructor since board initializes to faceDown
		for (int i = 0 ; i < board.getHexNum() ; i ++) {
		    rand = rnd.nextInt(hexPool.size());
		    Hex hex = hexPool.get(rand);
		    // Add hex to position i in board hex array.
		    board.addHex(hex);
		    hex.setId(i, Board.NumberOfHexes.THIRTY_SEVEN);
		    hex.setFaceDown(board.isFaceDown());
		    hexPool.remove(rand);
		}

		//Cup created with all things required for gameplay
		cup.setListOfThings(Util.getRandomList(Thing.createThings()));
	}

    public Game(Player host, List<Player> guests) {
    		this();
    		
    		currentPlayer = host;
    		playerOrder.add(host);
    		playerOrder.addAll(guests);
    }
    
	// Getters and Setters =============================================================================================    
	/**
	 * Gets the Player which is currently executing their turn.
	 *
	 * @return The current player.
	 */
	public final Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Sets the Player which is currently executing their turn.
	 *
	 * @param current The new player to set.
	 */
	public final void setCurrentPlayer(final Player current) {
		this.currentPlayer = current;
	}

	/**
	 * Gets the first die instance.
	 *
	 * @return The die.
	 */
	public final Die getDie1() {
		return die1;
	}

	/**
	 * Sets the first die instance.
	 *
	 * @param die The new die.
	 */
	public final void setDie1(final Die die) {
		this.die1 = die;
	}

	/**
	 * Gets the second die instance.
	 *
	 * @return The die.
	 */
	public final Die getDie2() {
		return die2;
	}

	/**
	 * Sets the second die instance.
	 *
	 * @param die The new die.
	 */
	public final void setDie2(final Die die) {
		die2 = die;
	}

	/**
	 * Gets the board instance for this Game.
	 *
	 * @return The board.
	 */
	public final Board getBoard() {
		return board;
	}

	/**
	 * Sets the new board instance for this Game.
	 *
	 * @param board The new Board.
	 */
	public final void setBoard(final Board board) {
		this.board = board;
	}

	/**
	 * Gets the current play mode.
	 * <pre>
	 * Either MODE_FOUR_PLAYER (default) or MODE_TWO_THREE_PLAYER.
	 * </pre>
	 *
	 * @return The play mode.
	 */
	public final int getMode() {
		return mode;
	}

	/**
	 * Sets the current play mode.
	 *
	 * @param mode The new mode to set.
	 */
	public final void setMode(final int mode) {
		if (mode == MODE_TWO_THREE_PLAYER || mode == MODE_FOUR_PLAYER) {
			this.mode = mode;
		}
	}

	public final int diceTotal() {
		return die1.getValue() + die2.getValue();
	}

	public Cup getCup() {
		return cup;
	}

	public void setCup(Cup c) {
		cup = c;
	}

	// Behaviour Methods ===============================================================================================
	public final void nextPlayer() {
		// Reset to beginning
		if (isLastPlayer()) {
			nextPlayerIdx = 0;
			currentPlayer = playerOrder.get(0);
			return;
		}
		// Get next player
		currentPlayer = playerOrder.get(++ nextPlayerIdx);
	}

	/**
	 * Returns true if the current player is the last player in the turn order.
	 *
	 * @return The player is last in player order.
	 */
	public final boolean isLastPlayer() {
		return nextPlayerIdx == playerOrder.size() - 1;
	}
	
	public final List<Player> getOpponentsForCurrent() {
		List<Player> opponents = new ArrayList<>(playerOrder);
		// Remove the current player
		opponents.remove(currentPlayer);
		
		return opponents;
	}
	
	public final List<Player> getOpponentsFor(Player p) {
		List<Player> opponents = new ArrayList<>(playerOrder);
		opponents.remove(p);
		
		return opponents;
	}
	
	// TODO: Find a better way if we have time. This is hacky.
	// Returns updated version of p, or p itself if null or not found.
	public final Player getUpdatedPlayer(Player p) {
		for (Player player : playerOrder) {
			if (p.equals(player)) return player;
		}
		
		return p;
	}
	
	/**
	 * Sets the turn order for the players of the Game.
	 *
	 * @param playersHighToLow The new collection containing the player order.
	 */
	public final void setPlayerOrder(final Collection<Player> playersHighToLow) {
		// Contains all four players.
		this.playerOrder = new ArrayList<>(playersHighToLow);
		// Reset the index to the new playerOrder
		//TODO: reset index to -1?
		currentPlayer = playerOrder.get(0);
	}
	
	
	/**
	 * Change Player order Phase requires Player 2 to execute his turn first,
	 * Player 3 to go second... 
	 */
	public final void rotatePlayerOrder() {
		// Add the first player in the list to the tail.
		Player p = playerOrder.remove(0);
		playerOrder.add(p);
	}
	
	
	public void moveThingFromCupToPlayer(Thing t, Player p){
		p.addThing(cup.removeThing(t));
	}

	public List<Thing> getLastSelectedThingsOfCurrentPlayerBlock() {
		List<Thing> list = new ArrayList<Thing>();
		List<Thing> blockList = currentPlayer.getBlock().getListOfThings();
		for(Thing t: blockList){
			if(t.isSelected())
				list.add(t);
		}
		return list;
	}
	
	public List<Thing> getLastSelectedThingsOfCurrentPlayerHex() {
		ArrayList<Thing> list = new ArrayList<Thing>();

		Hex hex = getBoard().getHexes().get(KNTAppFactory.getBoardPresenter().getLastHexSelected());
		List<Creature> army = hex.getArmies(getCurrentPlayer());
		
		if(army == null){
			Util.log("nullified "+ hex.getId());
		} else {
			for (Creature c: hex.getArmies(getCurrentPlayer())){
				if(c.isSelected())
					list.add(c);
			}
		}
		
		return list;
	}

	public List<Hex> getHexPool() {
		return hexPool;
	}

	public void setHexPool(List<Hex> hexPool) {
		this.hexPool = hexPool;
	}

	public List<Player> getPlayerOrder() {
		return playerOrder;
	}
	
	public Player getPlayerByName(String name) {
		for (Player p : playerOrder) {
			if (p.getName().equals(name)) return p;
		}
		
		return null;
	}
	
	// get special characters from hexes and players block
	// For the current player.
	public List<SpecialCharacter> getAllOwnedSpecialChar() {
		List<Hex> hexes = board.getHexes();
		List<SpecialCharacter> specChars = new ArrayList<>();
		
		for (Hex h : hexes) {
			if (h.getArmies(currentPlayer) == null) continue;
			
			for (Creature c : h.getArmies(currentPlayer)) {
				if (c instanceof SpecialCharacter) {
					specChars.add((SpecialCharacter) c);
				}
			}
		}

		
		return specChars;
	}
}
