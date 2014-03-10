package com.model.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Board;
import com.model.Creature;
import com.model.Cup;
import com.model.Die;
import com.model.Hex;
import com.model.HexDesert;
import com.model.HexForest;
import com.model.HexFrozenWaste;
import com.model.HexJungle;
import com.model.HexMountain;
import com.model.HexPlains;
import com.model.HexSea;
import com.model.HexSwamp;
import com.model.Player;
import com.model.Thing;
import com.presenter.HexFactory;
import com.presenter.Util;

/**
 * This is the game model. Represents the current "State" of the game
 *
 * @author kurtis
 */
public final class Game {

	private Player opponent1;
	private Player opponent2;
	private Player opponent3;
	/**
	 * The player owning "this" Game instance.
	 */
//    private Player player;
	/**
	 * The player who's is currently playing his/her turn.
	 */
	private Player currentPlayer;
	/**
	 * The game board representing hex tiles and all their contents.
	 */
	private Board board;
//    private Die die1;
    private Die die1;
    private Die die2;
    private int mode;
    public static final int 
    	MODE_FOUR_PLAYER = 1,
	    MODE_TWO_THREE_PLAYER = 2;
    //private GamePlay gamePlay;

    private List<Hex> hexPool;
    private Iterator<Player> nextPlayerIt;
    private List<Player> playerOrder;
    private Cup cup;
    
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
		//	nextPlayerIt = playerOrder.iterator();

//		gamePlay = new GamePlay();
		cup = new Cup();

		// TODO: Factory for 2 or 4 player.
		board = new Board(Board.NumberOfHexes.THIRTY_SEVEN);
		
		// added for iteration 1 hard-coded
		
		board.addHex(new HexSea(0));
		board.addHex(new HexSea(1));
		board.addHex(new HexSea(2));
		board.addHex(new HexSea(3));
		board.addHex(new HexSea(4));
		board.addHex(new HexSea(5));
		board.addHex(new HexSea(6));
		board.addHex(new HexSea(7));
		board.addHex(new HexSea(8));
		board.addHex(new HexSea(9));
		board.addHex(new HexSea(10));
		board.addHex(new HexSea(11));
		board.addHex(new HexSea(12));
		board.addHex(new HexSea(13));
		board.addHex(new HexSea(14));
		board.addHex(new HexSea(15));
		board.addHex(new HexSea(16));
		board.addHex(new HexSea(17));
		board.addHex(new HexSea(18));
		board.addHex(new HexSea(19));
		board.addHex(new HexSea(20));
		board.addHex(new HexSea(21));
		board.addHex(new HexSea(22));
		board.addHex(new HexSea(23));
		board.addHex(new HexSea(24));
		board.addHex(new HexSea(25));
		board.addHex(new HexSea(26));
		board.addHex(new HexSea(27));
		board.addHex(new HexSea(28));
		board.addHex(new HexSea(29));
		board.addHex(new HexSea(30));
		board.addHex(new HexSea(31));
		board.addHex(new HexSea(32));
		board.addHex(new HexSea(33));
		board.addHex(new HexSea(34));
		board.addHex(new HexSea(35));
		board.addHex(new HexSea(36));
		
		for (int i = 0 ; i < board.getHexNum() ; i ++) {
		    board.getHexes().get(i).setId(i, Board.NumberOfHexes.THIRTY_SEVEN);
		}
		

		
		//Removed for iteration 1
		HexFactory hexFactory = new HexFactory();

		hexPool = hexFactory.createHexPool(Board.NumberOfHexes.THIRTY_SEVEN);
/*
		// Choose Hexes at random from the pool and add to the board.
		int rand = 0;
		Random rnd = new Random();
	
		for (int i = 0 ; i < board.getHexNum() ; i ++) {
		    rand = rnd.nextInt(hexPool.size());
		    // Add hex to position i in board hex array.
		    board.addHex(hexPool.get(rand));
		    hexPool.get(rand).setId(i, Board.NumberOfHexes.THIRTY_SEVEN);
		    hexPool.remove(rand);
		}*/

		//Cup created with all things required for gameplay
		cup.setListOfThings(Util.getRandomList(Thing.createThings()));
	}

	// Getters and Setters =============================================================================================
	/**
	 * Gets the player owning this Game instance. (Player "playing" this game)
	 *
	 * @return The player
	 */
//    public final Player getPlayer() { return player; }
	/**
	 * Sets the player owning this instance of the Game.
	 *
	 * @param player The player to set.
	 */
//    public void setPlayer(final Player player) { this.player = player; }
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

	/**
	 * Gets the phase that the players must currently go through.
	 *
	 * @return The current phase.
	 */
//    public final GamePlay getPhase() { return gamePlay; }
//    
//    /**
//     * Sets the new phase that the players will go through.
//     * @param phase The phase
//     */
//    public final void setPhase(final GamePlay phase) { this.gamePlay = phase; }
	/**
	 * Gets the first opponent.
	 *
	 * @return The opponent.
	 */
	public final Player getOpponent1() {
		return opponent1;
	}

	/**
	 * Sets the first opponent instance.
	 *
	 * @param opponent1 The new opponent.
	 */
	public final void setOpponent1(final Player opponent1) {
		this.opponent1 = opponent1;
	}

	/**
	 * Gets the second opponent.
	 *
	 * @return The opponent.
	 */
	public final Player getOpponent2() {
		return opponent2;
	}

	/**
	 * Sets the second opponent instance.
	 *
	 * @param opponent2 The new opponent.
	 */
	public final void setOpponent2(final Player opponent2) {
		this.opponent2 = opponent2;
	}

	/**
	 * Gets the third opponent.
	 *
	 * @return The opponent.
	 */
	public final Player getOpponent3() {
		return opponent3;
	}

	/**
	 * Sets the third opponent instance.
	 *
	 * @param opponent3 The new opponent.
	 */
	public void setOpponent3(Player opponent3) {
		this.opponent3 = opponent3;
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

	/**
	 * Returns true if the current player is the last player in the turn order.
	 *
	 * @return The player is last in player order.
	 */
	public final boolean isLastPlayer() {
		return !nextPlayerIt.hasNext();
	}
	
	public boolean hasNextPlayer(){
		return nextPlayerIt.hasNext();
	}

	public final List<Player> getOpponentsForCurrent() {
		List<Player> opponents = new ArrayList<>(playerOrder);
		// Remove the current player
		opponents.remove(currentPlayer);
		
		return opponents;
	}
	
	/**
	 * Sets the turn order for the players of the Game.
	 *
	 * @param playersHighToLow The new collection containing the player order.
	 */
	public final void setPlayerOrder(final Collection<Player> playersHighToLow) {
		// Contains all four players.
		this.playerOrder = new ArrayList<>(playersHighToLow);
		// Reset Iterator to the new playerOrder
		nextPlayerIt = playerOrder.iterator();
		
		// Set new current player
//		if (init)
//			currentPlayer = nextPlayerIt.next();
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


	public void clearLastSelectedThingsOfCurrentPlayerBlock() {
		List<Thing> blockList = GameService.getInstance().getGame().getCurrentPlayer().getBlock().getListOfThings();
		for(Thing t: blockList){
			t.setSelected(false);
		}
	}

	public ArrayList<Thing> getLastSelectedThingsOfCurrentPlayerBlock() {
		ArrayList<Thing> list = new ArrayList<Thing>();
		List<Thing> blockList = getCurrentPlayer().getBlock().getListOfThings();
		for(Thing t: blockList){
			if(t.isSelected())
				list.add(t);
		}
		return list;
	}
	
	public ArrayList<Thing> getLastSelectedThingsOfCurrentPlayerHex() {
		ArrayList<Thing> list = new ArrayList<Thing>();

		Hex hex = getBoard().getHexes().get(KNTAppFactory.getBoardPresenter().getLastHexSelected());
		ArrayList<Creature> army = hex.getArmies(getCurrentPlayer());
		
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
}
