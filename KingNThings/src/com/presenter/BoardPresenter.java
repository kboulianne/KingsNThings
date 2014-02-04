/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.presenter;

import java.util.ArrayList;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Board;
import com.model.Creature;
import com.model.Hex;
import com.model.Player;
import com.model.game.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.view.BoardView;
import java.util.Set;

/**
 *
 * @author kurtis
 */
public class BoardPresenter {

	private final BoardView view;
    // Required Presenters
	//private HexDetailsPresenter detailsPresenter;
	private SidePanePresenter sidePanePresenter;

	// Usually BoardService, but ok for our purposes. We will see in IT2
	private GameService svc;

	private int lastHexSelected = -1; //FIXME this variable also exists in GameScreen

	public BoardPresenter(BoardView view) {
		this.view = view;
		this.view.setPresenter(this);

		// Set initial model (usually uses a service.
		svc = GameService.getInstance();
		Game game = svc.getGame();

		view.setBoard(game.getBoard());
	}

	public BoardView getView() {
		return view;
	}

	public final void setDependencies(SidePanePresenter sidePanePresenter) {
		this.sidePanePresenter = sidePanePresenter;
	}
	
    // UI Logic here.
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
		sidePanePresenter.showHexDetailsFor(b.getHexes().get(selected));
		//	detailsPresenter.showHex(b.getHexes().get(selected));
	}
	
	public void handleStartingKingdomsHexClick(int selected) {
		Board b = svc.getGame().getBoard();
		Hex hex = b.getHexes().get(selected);
		Player current = svc.getGame().getCurrentPlayer();
		
		// Make sure the selected hex is unowned, adjacent to the current player's starting position
		// and not adjacent to an opponent's tile.
		
		// Do nothing if selected hex is owned
		if (hex.getOwner() == null) {
//			// Get the start positions set so we can make sure there is a path from current
//			// player's starting hex and the selected tile.
//			Set<Integer> start = b.getStartPositions();
			
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
				if (adjHex.getOwner() != null) {
					if (adjHex.getOwner().equals(current)) {
						System.out.println("owned by current");
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
	
	public void handleStartPositionSelectedHexClick(int selected) {
		
		Hex hex = svc.getGame().getBoard().getHexes().get(selected);
		if(hex.getOwner() == null){
			hex.setOwner(GameService.getInstance().getGame().getCurrentPlayer());
//			hex.setColor(GameService.getInstance().getGame().getCurrentPlayer().getId().getColor());
		}else{
			//TODO display msg
		}
		
		// Update the view
		view.setBoard(svc.getGame().getBoard());
		
		GamePlay.getInstance().endTurn();
	}
	
	public void handleMovementSelectedHexClick(int selected) {
		
		Hex hex = svc.getGame().getBoard().getHexes().get(selected);
		
		Util.log("selected hex id "+selected+" isHighlighted="+hex.isHighlighted());
		if(hex.isHighlighted()){	

			Player currentPlayer = GameService.getInstance().getGame().getCurrentPlayer();
			Creature creature = KNTAppFactory.getHexDetailsPresenter().getView().getCurrentPlayerArmy().getLastSelectedCreature();
			svc.getGame().getBoard().getHexes().get(lastHexSelected).removeCreatureFromArmy(creature, currentPlayer);
			hex.addCreatToArmy(creature, currentPlayer);
			
		}
		for(Hex h: svc.getGame().getBoard().getHexes()){
			h.setHighlighted(false);
		}
		handleHexClick(selected);
	}

	public void handleMoveButtonClick() {
		// TODO Auto-generated method stub


		Util.log("SeletectIndex-->" + lastHexSelected);
		ArrayList<Integer> moveableHexIdList = new ArrayList<>();
		calculateMovementWeight(lastHexSelected, moveableHexIdList);

		//repaint moveableHexIdList
		for (int i : moveableHexIdList) {
			Util.log("moveable ids:" + i);
//			view.paintHex(svc.getGame().getBoard().getHexes().get(i));
		}

		// Update view
		view.setBoard(svc.getGame().getBoard());
		
		//

		//Util.log("SeletectIndex"+ selectedIndex);
		//moveButton
	}

	public void calculateMovementWeight(int hexId, ArrayList<Integer> calculated) {
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
		
		int availableMovesForSelectedThing; //=
		//for now
		availableMovesForSelectedThing = 4;
		if(weight>availableMovesForSelectedThing+1){
		//|| (hex.getType().equals(Hex.HexType.SEA  )&& weight ==availableMovesForSelectedThing)
	    // || friendly army ==10 at hex(<-- exception citadel)){
			
			Util.log("    id:"+hexId+"-->not selectable");
			return;
		}

		Util.log("    id:" + hexId + "-->weight:" + weight);

		///otherwise
		hex.setHighlighted(true);
		hex.setMovementWeight(weight);
		hex.setSelectable(true);
		calculated.add(hexId);
		for (int k : uncheckedList) { // recurse
			calculateMovementWeight(k, calculated);
		}
	}

	public int getLastHexSelected() {
		return lastHexSelected;
	}
}
