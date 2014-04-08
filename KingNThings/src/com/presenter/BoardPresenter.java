package com.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.game.phase.init.ExchangePhase;
import com.main.KNTAppFactory;
import com.model.Board;
import com.model.Creature;
import com.model.Fort;
import com.model.Game;
import com.model.Hex;
import com.model.IncomeCounter;
import com.model.MagicItem;
import com.model.Player;
import com.model.Thing;
import com.model.Treasure;
import com.util.Util;
import com.view.BoardView;

/**
 *
 * @author kurtis
 */
public class BoardPresenter {

	private final BoardView view;
	// Ui Models
	private boolean movingArmy;
	private int lastHexSelected = -1;

	public BoardPresenter(BoardView view) {
		this.view = view;
		movingArmy = false;
	}

	public BoardView getView() {
		return view;
	}

	/**
	 * Helper method.
	 * @return The Game instance stored locally for this client.
	 */
	private Game getGame() {
		// Use local instance.
		return KNTAppFactory.getGamePresenter().getLocalInstance();
	}
	
	/**
	 * Default logic to execute when clicking a Hex tile.
	 * @param selected  The index of the hex in the list.
	 */
	public void handleHexClick(int selected) {
		Util.playHexClickSound();
		// Only need service to fetch board.
		// TODO create GameService#selectHex(...)?
		Board b = getGame().getBoard();

		// undo last selection.
		if (lastHexSelected > -1) {
			b.getHexes().get(lastHexSelected).setSelected(false);
		}
		lastHexSelected = selected;
		b.getHexes().get(selected).setSelected(true);

		// Update BoardView
		view.setBoard(b);

		// Show the HexDetails in sidepane
		KNTAppFactory.getSidePanePresenter().showHexDetailsFor(b.getHexes().get(selected));
	}
	
	/**
	 * Logic to execute during the Starting Forces phase.  This method tells the player to place a tower on one of their three initial kingdoms.
	 * @param selected The index of the selected hex in the list.
	 */
	public void handleStartingTowerHexClick(int selected) {
		Game game = getGame();
		Board b = game.getBoard();
		Hex hex = b.getHexes().get(selected);
		Player current = game.getCurrentPlayer();
		
		// Make sure the selected hex's own is the current player
		if (hex.getHexOwner() != null && hex.getHexOwner().equals(current)) {
			Util.playHexClickSound();
			hex.setFort(Fort.create());
			// Update view
			view.setBoard(b);
			
			KNTAppFactory.getGamePresenter().endTurn();
		}
	}
	
	/**
	 * Allows the player to select one of three starting kingdoms. The selected hex must be unowned, adjacent to a tile owned by the current player, and
	 * not adjacent to a tile owned by an opponent.
	 * @param selected The index of the selected hex in the list.
	 */
	public void handleStartingKingdomsHexClick(int selected) {
		Game game = getGame();
		Board b = game.getBoard();
		Hex hex = b.getHexes().get(selected);
		Player current = game.getCurrentPlayer();
		
		// Make sure the selected hex is unowned, adjacent to the current player's starting position
		// and not adjacent to an opponent's tile.
		// Do nothing if selected hex is owned
		
		if (hex.getHexOwner() == null) {
			Util.playHexClickSound();
			// Get the adjacent hexes.
			// Make sure current player owns one of the hexes. That is, there is a path from
			// current's start position to the selected hex.
			int[] adjacent = hex.getJoiningHexes();
			
			boolean hasPath = false, adjOpponent = false;
			Hex adjHex = null;
			for (int i : adjacent) {
				// -1 is invalid move
				if (i < 0) continue;
				
				adjHex = b.getHexes().get(i);
				if (adjHex.getHexOwner() != null) {
					if (adjHex.getHexOwner().equals(current)) {
						hasPath = true;
					}
					else {
						// Hex is owned (getOwner() != null above) but by an opponent.
						adjOpponent = true;
						break;
					}
				}
			}
			
			// Selected hex is next to a tile owned by the player and selected hex is not 
			// adjacent to an opponent's hex
			if (hasPath && !adjOpponent) {
				// Take ownership of the hex
				hex.setOwner(current);
				
				view.setBoard(b);
				KNTAppFactory.getGamePresenter().endTurn();
			}
		}
	}
	
	/** 
	 * Allows the player to select one of four starting positions during the starting position phase.
	 * @param selected The index of the selected hex in the list.
	 */
	public void handleStartPositionSelectedHexClick(int selected) {
		Game game = getGame();
		Hex hex = game.getBoard().getHexes().get(selected);
		
		if(hex.getHexOwner() == null)	{
			Util.playHexClickSound();
			if(hex.getType() == Hex.HexType.SEA)	{
				List<Hex> hexes = game.getHexPool();
				HexFactory fact = new HexFactory();
				Random rnd = new Random();
				int rand = 0;
			
				while (hex.getType() == Hex.HexType.SEA) {
				    rand = rnd.nextInt(hexes.size());
				    
				    hexes.add(fact.createHex(0, hex.getType()));
				    hex = hexes.remove(rand);
				    hex.setId(selected, Board.NumberOfHexes.THIRTY_SEVEN);
				    game.getBoard().replaceHex(hex);
				    hex.setStartPosition(true);
				    hex.setFaceDown(true);
				}
			}
			
			hex = game.getBoard().getHexes().get(selected);
			hex.setOwner(game.getCurrentPlayer());
			game.getCurrentPlayer().setStartPos(hex);
			
			view.setBoard(game.getBoard());
			
			int count = 0;
			for(int i=0; i<hex.getJoiningHexes().length; i++)	{
				if(hex.getJoiningHexes()[i] == -1)	continue;
				
				Hex curr = game.getBoard().getHexes().get(hex.getJoiningHexes()[i]);
				if(curr.getType() != Hex.HexType.SEA)	count++;
				if(count >= 2)	{
					KNTAppFactory.getGamePresenter().endTurn();
					break;
				}
			}
			
			if(count < 2)	{
				hex.setFaceDown(false);
				for(int i=0; i<hex.getJoiningHexes().length; i++)	{
					if(hex.getJoiningHexes()[i] == -1)	continue;
					
					game.getBoard().getHexes().get(hex.getJoiningHexes()[i]).setFaceDown(false);
				}
				
				view.setBoard(game.getBoard());
				KNTAppFactory.getBoardPresenter().getView().addHexExchangeHandler();
				KNTAppFactory.getSidePanePresenter().getView().showArbitraryView("Exchange neighbouring sea tiles", Game.START_HEX_IMAGE);
			}
		}		
	}
	
	public void handleHexExchangeClick(int selected)	{
		Util.playHexClickSound();
		Game game = getGame();
		Player currPlayer = game.getCurrentPlayer();
		Hex hex = game.getBoard().getHexes().get(selected);
		boolean neighbour = false;
		
		for(int i=0; i<currPlayer.getStartPos().getJoiningHexes().length; i++)	{
			if(currPlayer.getStartPos().getJoiningHexes()[i] == -1)	continue;
			
			Hex curr = game.getBoard().getHexes().get(currPlayer.getStartPos().getJoiningHexes()[i]);
			if(curr == hex)	{
				neighbour = true;
				break;
			}
		}
		
		if(!neighbour)	return;
		
		if(hex.getType() == Hex.HexType.SEA)	{
			List<Hex> hexes = game.getHexPool();
			HexFactory fact = new HexFactory();
			Random rnd = new Random();
			int rand = 0;			
		
			while (hex.getType() == Hex.HexType.SEA) {
			    rand = rnd.nextInt(hexes.size());
			   
			    hexes.add(fact.createHex(0, hex.getType()));
			    hex = hexes.remove(rand);
			    hex.setId(selected, Board.NumberOfHexes.THIRTY_SEVEN);
			    game.getBoard().replaceHex(hex);
			}
		}
		
		view.setBoard(game.getBoard());
		
		int count = 0;
		Hex startPos = currPlayer.getStartPos();
		
		for(int i=0; i<startPos.getJoiningHexes().length; i++)	{
			if(startPos.getJoiningHexes()[i] == -1)	continue;
			
			Hex curr = game.getBoard().getHexes().get(startPos.getJoiningHexes()[i]);
			if(curr.getType() != Hex.HexType.SEA)	count++;
			if(count >= 2)	{
//				KNTAppFactory.getGamePresenter().endTurn();
				return;
			}
		}
	}
	
	/**
	 * Allows the current player to move a thing or army from the selected hex, to another hex.
	 * @param selected The index of the selected hex in the list.
	 */
	public void handleMovementSelectedHexClick(int selected) {
		Game game = getGame();
		Hex hex = game.getBoard().getHexes().get(selected);
//		KNTAppFactory.getSidePanePresenter().showHexDetailsFor(hex);
		
		if(hex.isHighlighted())	{
//		if (lastHexSelected != -1) {
			Util.playHexClickSound();
			Hex lastSelected = game.getBoard().getHexes().get(lastHexSelected);
			Player currentPlayer = game.getCurrentPlayer();
			if(movingArmy)	{ // move whole army
				List<Creature> army = KNTAppFactory.getArmyDetailsPresenter().getView().getLastSelectedArmy();

				if(selected != lastHexSelected)	{
					for(Creature c: army) { // for same order
						hex.addCreatToArmy(c, currentPlayer);
						c.setHexLocation(selected);
					}
					for(int i = army.size()-1; i>=0; i--) {
						lastSelected.removeCreatureFromArmy(army.get(i), currentPlayer);
					}
				}
				
				movingArmy = false;
			} else { 
				List<Thing> things = game.getLastSelectedThingsOfCurrentPlayerHex();
				if(things.isEmpty())
					return;

				for(Thing t: things){
					if(selected != lastHexSelected)	{
						if(t instanceof IncomeCounter)	{
							continue;
						} else	{
							lastSelected.removeCreatureFromArmy((Creature) t, currentPlayer);
							hex.addCreatToArmy((Creature) t, currentPlayer);							
						}
						t.setHexLocation(selected);
					}
				}
			}
			

			// if Hex is unexplored
			if(hex.getHexOwner() == null && hex.getKedabCreatures().isEmpty()){
				int randNum = Util.randomNumber(1, 6);
				if(randNum == 6 || randNum ==1){
					hex.setOwner(game.getCurrentPlayer());
					System.out.println("Player claimed tile after exploring.");
				}
				else {
					for(int i =0; i<randNum;i++){
						Creature c = game.getCup().getRandomCreature();
						if(c==null){
							if(hex.getKedabCreatures().isEmpty()){
								hex.setOwner(game.getCurrentPlayer());
								System.out.println("Player claimed tile - no creatures in cup.");
							}
							break;
						}else{
							hex.addKebabCreature(c);
						}
					}
				}
			}
			view.setBoard(game.getBoard());
		}
		
		
		
		// TOOD add setAllHighlighted(boolean)
		for(Hex h: game.getBoard().getHexes()){
			h.setHighlighted(false);
		}
		handleHexClick(selected);
	}
	
	/**
	 * Allows the player to add things from the player rack to any tile they own.
	 * @param selected The index of the selected hex in the list.
	 */
	public void handlePlacementSelectedHexClick(int selected) {
		Game game = getGame();
		Hex h = game.getBoard().getHexes().get(selected);
		Player player = game.getCurrentPlayer();
		List<Thing> listOfThings = game.getLastSelectedThingsOfCurrentPlayerBlock();
		
		if((h != null) && h.getHexOwner().equals(player))	{
			Util.playHexClickSound();
			for(Thing t: listOfThings)	{
				if(t.getHexLocation() == -1)	{
					if(t instanceof IncomeCounter)	{
						if((h.getCounter() != null))	continue;
						if((!((IncomeCounter) t).getDomain().equals("none")) && (!h.getTypeAsString().equals(((IncomeCounter) t).getDomain())))	{
							continue;
						} else	{
							h.setCounter((IncomeCounter) t);
						}
					} else if(t instanceof Treasure || t instanceof MagicItem)	{
						continue;
					} else	{
						h.addCreatToArmy((Creature) t, player);							
					}
				}
				player.removeThing(t);
				t.setHexLocation(selected);
				view.setBoard(game.getBoard());
			}
			//TODO: Is there a better way to do this?
			if(KNTAppFactory.getGamePlay().getPhaseLogic() instanceof ExchangePhase)
				KNTAppFactory.getPlayerInfoPresenter().getView().setRackExchangeThingsHandler(player);
			else
				KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(player);
			
			KNTAppFactory.getSidePanePresenter().showHexDetailsFor(h);
		}
	}
	
	public void handleConstructionHexClick(int selected) {
		Game game = getGame();
		Hex h = game.getBoard().getHexes().get(selected);
		Player player = game.getCurrentPlayer();
		
		if(!h.getHexOwner().equals(player))	return;
		Util.playHexClickSound();
		KNTAppFactory.getSidePanePresenter().getView().showBuildMenu(h, player, game.getBoard());
	}

	public void handleMoveSetupForThing(Thing t) {
		Util.playHexClickSound();
		Game game = getGame();
		ArrayList<Integer> moveableHexIdList = new ArrayList<>();
		
		int availableMovesForSelectedThing = 4;
		if(t instanceof Creature)	availableMovesForSelectedThing = ((Creature)t).getNumberOfMovesAvailable();
		
		calculateMovementWeight(lastHexSelected, availableMovesForSelectedThing, moveableHexIdList);

		view.setBoard(game.getBoard());
	}
	
	public void handleMoveSetupForArmy() {
		Util.playHexClickSound();
		Game game = getGame();
		ArrayList<Integer> moveableHexIdList = new ArrayList<>();
		//set to 4 for now
		calculateMovementWeight(lastHexSelected, 4, moveableHexIdList);
		view.setBoard(game.getBoard());
		movingArmy = true;
	}

	private void calculateMovementWeight(int hexId, int availableMovesForSelectedThing, ArrayList<Integer> calculated) {
		Game game = getGame();
		Hex hex = game.getBoard().getHexes().get(hexId);

		ArrayList<Integer> checkedList = new ArrayList<>();
		ArrayList<Integer> uncheckedList = new ArrayList<>();

		int[] joiningHexes = hex.getJoiningHexes();
		for (int i = 0; i < joiningHexes.length; i++) {
			if (joiningHexes[i] == -1) {
				//do nothing
			} else if (calculated.contains(joiningHexes[i])) {
				checkedList.add(joiningHexes[i]);
			} else if (!calculated.contains(joiningHexes[i])) {
				uncheckedList.add(joiningHexes[i]);
			} else {
				Util.log("calcMoveW Error: remove this later");
			}
		}

		// get min value of joining hexes
		int minValue = -1;
		if (checkedList.isEmpty()) {
			minValue = 0;
		} else {
			for (int j : checkedList) {
				if (minValue == -1) {
					minValue = game.getBoard().getHexes().get(j).getMovementWeight();
				} else if (game.getBoard().getHexes().get(j).getMovementWeight() < minValue) {
					minValue = game.getBoard().getHexes().get(j).getMovementWeight();
				}
			}
		}

		//fornow
		int weight = minValue + 1;
		//(hex.getType().equals(Hex.HexType.SWAMP)||
		// hex.getType().equals(Hex.HexType.MOUNTAIN)||
		// hex.getType().equals(Hex.HexType.FOREST||
		// hex.getType().equals(Hex.HexType.JUNGLE)))?2:1);
		
		//int availableMovesForSelectedThing; //=
		//for now
		//availableMovesForSelectedThing = 1;
		if(weight>availableMovesForSelectedThing+1){
		//|| (hex.getType().equals(Hex.HexType.SEA  )&& weight ==availableMovesForSelectedThing)
	    // || friendly army ==10 at hex(<-- exception citadel)){
			
			//Util.log("    id:"+hexId+"-->not selectable");
			return;
		}

		//Util.log("    id:" + hexId + "-->weight:" + weight);

		///otherwise
		hex.setHighlighted(true);
		hex.setMovementWeight(weight);
		hex.setSelectable(true);
		calculated.add(hexId);
		for (int k : uncheckedList) { // recurse
			calculateMovementWeight(k, availableMovesForSelectedThing, calculated);
		}
	}
	
	/**
	 * Highlights and sets hexes in conflict. 
	 */
	public void findAndHighlightConflicts() {
		Game game = getGame();
		Board board = game.getBoard();
		Player current = game.getCurrentPlayer();
		
		List<Hex> conflicts = board.findConflictsFor(current);
		
		for (Hex h : conflicts) {
			h.setConflict(true);
		}		
		
		// Update view
		view.setBoard(board);
	}
	public void clearConflictHighlights() {
		Game game = getGame();
		Board board = game.getBoard();
		for (Hex h : board.getHexes()) {
			h.setConflict(false);
		}		
		// Update view
		view.setBoard(board);
	}
	
	public void handleBattleSelectionHex(int selected) {

		
		Board b = getGame().getBoard();
		Player current = getGame().getCurrentPlayer();
		Hex hex = b.getHexes().get(selected);
		
		if (hex.hasConflict()) {
			Util.playStartBattleSound();
			// Create battle. Assume first opposing player returned from valueSet.
			
			List<Player> defendingPlayers = new ArrayList<Player>();
			
			for (Player p : hex.getArmies().keySet()) {
				if (!p.equals(current)) {
					defendingPlayers.add(p);
				}
			}
			
			if(defendingPlayers.size()==1){
				KNTAppFactory.getBattlePresenter().triggerBattle(current, defendingPlayers.get(0), hex); // Start the battle
				
			}
			else{
				KNTAppFactory.getSidePanePresenter().getView().showBattleChooserView(current, defendingPlayers, hex);
			}
		}else{
			KNTAppFactory.getSidePanePresenter().getView().showArbitraryView("Available battles displayed as red hexes", Game.CROWN_IMAGE);
		}
		
		view.setBoard(b);
	}
	
	/**
	 * Gets the index of the last Hex tile that was selected.
	 * @return The last selected index.
	 */
	public int getLastHexSelected() {
		return lastHexSelected;
	}
}
