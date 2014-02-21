package com.presenter;

import java.util.ArrayList;
import java.util.List;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Battle;
import com.model.Board;
import com.model.Creature;
import com.model.Fort;
import com.model.Hex;
import com.model.IncomeCounter;
import com.model.MagicItem;
import com.model.Player;
import com.model.Thing;
import com.model.Treasure;
import com.model.game.Game;
import com.model.game.phase.GamePlay;
import com.model.game.phase.init.ExchangePhase;
import com.view.BoardView;

/**
 *
 * @author kurtis
 */
public class BoardPresenter {

	private final BoardView view;

	// Usually BoardService, but ok for our purposes. We will see in IT2
	private final GameService svc;
	// Ui Models
	private boolean movingArmy;
	private int lastHexSelected = -1;

	public BoardPresenter(BoardView view) {
		this.view = view;
		this.view.setPresenter(this);
		movingArmy = false;

		// Set initial model (usually uses a service.
		svc = GameService.getInstance();
		Game game = svc.getGame();

		view.setBoard(game.getBoard());
	}

	public BoardView getView() {
		return view;
	}

	
	/**
	 * Default logic to execute when clicking a Hex tile.
	 * @param selected  The index of the hex in the list.
	 */
	public void handleHexClick(int selected) {
		// Only need service to fetch board.
		// TODO create GameService#selectHex(...)?
		Board b = svc.getGame().getBoard();

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
		Board b = svc.getGame().getBoard();
		Hex hex = b.getHexes().get(selected);
		Player current = svc.getGame().getCurrentPlayer();
		
		// Make sure the selected hex's own is the current player
		if (hex.getHexOwner() != null && hex.getHexOwner().equals(current)) {

			hex.setFort(Fort.create());
			// Update view
			view.setBoard(b);
			
			GamePlay.getInstance().endTurn();
		}
	}
	
	/**
	 * Allows the player to select one of three starting kingdoms. The selected hex must be unowned, adjacent to a tile owned by the current player, and
	 * not adjacent to a tile owned by an opponent.
	 * @param selected The index of the selected hex in the list.
	 */
	public void handleStartingKingdomsHexClick(int selected) {
		Board b = svc.getGame().getBoard();
		Hex hex = b.getHexes().get(selected);
		Player current = svc.getGame().getCurrentPlayer();
		
		// Make sure the selected hex is unowned, adjacent to the current player's starting position
		// and not adjacent to an opponent's tile.
		// Do nothing if selected hex is owned
		
		if (hex.getHexOwner() == null) {
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
				GamePlay.getInstance().endTurn();
			}
		}
	}
	
	/** 
	 * Allows the player to select one of four starting positions during the starting position phase.
	 * @param selected The index of the selected hex in the list.
	 */
	public void handleStartPositionSelectedHexClick(int selected) {
		
		Hex hex = svc.getGame().getBoard().getHexes().get(selected);
		if(hex.getHexOwner() == null){
			hex.setOwner(GameService.getInstance().getGame().getCurrentPlayer());
			// Update the view and end turn
			view.setBoard(svc.getGame().getBoard());
			GamePlay.getInstance().endTurn();
		} else {
			//TODO display msg
		}
		
		
	}
	
	/**
	 * Allows the current player to move a thing or army from the selected hex, to another hex.
	 * @param selected The index of the selected hex in the list.
	 */
	public void handleMovementSelectedHexClick(int selected) {
		Hex hex = svc.getGame().getBoard().getHexes().get(selected);
		
		if(hex.isHighlighted())	{
			Hex lastSelected = svc.getGame().getBoard().getHexes().get(lastHexSelected);
			Player currentPlayer = svc.getGame().getCurrentPlayer();
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
				ArrayList<Thing> things = svc.getGame().getLastSelectedThingsOfCurrentPlayerHex();
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
			
			// Hex is unexplored, skip explore logic and claim it for DEMO
			if (hex.getHexOwner() == null) {
				hex.setOwner(svc.getGame().getCurrentPlayer());
				System.out.println("Player claimed tile after exploring.");
			}
			
			view.setBoard(svc.getGame().getBoard());
		}
		
		
		
		// TOOD add setAllHighlighted(boolean)
		for(Hex h: svc.getGame().getBoard().getHexes()){
			h.setHighlighted(false);
		}
		handleHexClick(selected);
	}
	
	/**
	 * Allows the player to add things from the player rack to any tile they own.
	 * @param selected The index of the selected hex in the list.
	 */
	public void handlePlacementSelectedHexClick(int selected) {
		Hex h = svc.getGame().getBoard().getHexes().get(selected);
		Player player = svc.getGame().getCurrentPlayer();
		ArrayList<Thing> listOfThings = svc.getGame().getLastSelectedThingsOfCurrentPlayerBlock();
		
		if(h.getHexOwner() == player)	{
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
				view.setBoard(svc.getGame().getBoard());
			}
			if(GamePlay.getInstance().getPhaseLogic() instanceof ExchangePhase)
				KNTAppFactory.getPlayerInfoPresenter().getView().setRackExchangeThingsHandler(player);
			else
				KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(player);
			KNTAppFactory.getSidePanePresenter().showHexDetailsFor(h);
		}
	}

	public void handleMoveSetupForThing(Thing t) {
		
		ArrayList<Integer> moveableHexIdList = new ArrayList<>();
		
		int availableMovesForSelectedThing = 4;
		if(t instanceof Creature)	availableMovesForSelectedThing = ((Creature)t).getNumberOfMovesAvailable();
		
		calculateMovementWeight(lastHexSelected, availableMovesForSelectedThing, moveableHexIdList);

		view.setBoard(svc.getGame().getBoard());
	}
	
	public void handleMoveSetupForArmy() {
		ArrayList<Integer> moveableHexIdList = new ArrayList<>();
		//set to 4 for now
		calculateMovementWeight(lastHexSelected, 4, moveableHexIdList);
		view.setBoard(svc.getGame().getBoard());
		movingArmy = true;
	}

	protected void calculateMovementWeight(int hexId, int availableMovesForSelectedThing, ArrayList<Integer> calculated) {
		Hex hex = svc.getGame().getBoard().getHexes().get(hexId);

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
					minValue = svc.getGame().getBoard().getHexes().get(j).getMovementWeight();
				} else if (svc.getGame().getBoard().getHexes().get(j).getMovementWeight() < minValue) {
					minValue = svc.getGame().getBoard().getHexes().get(j).getMovementWeight();
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
		Board board = svc.getGame().getBoard();
		Player current = svc.getGame().getCurrentPlayer();
		
		List<Hex> conflicts = board.findConflictsFor(current);
		
		for (Hex h : conflicts) {
			h.setConflict(true);
		}		
		
		// Update view
		view.setBoard(board);
	}
	public void clearConflictHighlights() {
		Board board = svc.getGame().getBoard();
		for (Hex h : board.getHexes()) {
			h.setConflict(false);
		}		
		// Update view
		view.setBoard(board);
	}
	
	public void handleBattleSelectionHex(int selected) {
		Board b = svc.getGame().getBoard();
		Player current = svc.getGame().getCurrentPlayer();
		Player defender = null;
		Hex hex = b.getHexes().get(selected);
		
		if (hex.hasConflict()) {
			// Create battle. Assume first opposing player returned from valueSet.
			for (Player p : hex.getArmies().keySet()) {
				if (!p.equals(current)) {
					defender = p;
					break;
				}
			}
			
			//FOR DEMO -- Exploration is skipped
			// Starts battle between Players
			Battle battle = new Battle(current, defender, hex);
			// SHow the popup
			KNTAppFactory.getPopupPresenter().showBattlePopup();
			
			// Start the battle
			KNTAppFactory.getBattlePresenter().startBattle(battle);	
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
