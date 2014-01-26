/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.presenter;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.game.services.GameService;
import com.model.Board;
import com.model.Hex;
import com.model.game.Game;
import com.view.BoardView;

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

	public BoardPresenter(BoardView view, SidePanePresenter sidePanePresenter) {
		this.view = view;
		this.view.setPresenter(this);
		//this.detailsPresenter = details;
		this.sidePanePresenter = sidePanePresenter;

		// Set initial model (usually uses a service.
		svc = GameService.getInstance();
		Game game = svc.getGame();

		view.setBoard(game.getBoard());
	}

	public BoardView getView() {
		return view;
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

	public void handleMoveButtonClick(Button moveButton) {
		// TODO Auto-generated method stub

		//final int selectedIndex = this.gSceen.getLastHexSelected();
		moveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Util.log("SeletectIndex-->" + lastHexSelected);
				ArrayList<Integer> moveableHexIdList = new ArrayList<Integer>();
				calculateMovementWeight(lastHexSelected, moveableHexIdList);

				//repaint moveableHexIdList
				for (int i : moveableHexIdList) {
					Util.log("moveable ids:" + i);
					view.paintHex(svc.getGame().getBoard().getHexes().get(i));
				}
			}
		});
		//

		//Util.log("SeletectIndex"+ selectedIndex);
		//moveButton
	}

	public void calculateMovementWeight(int hexId, ArrayList<Integer> calculated) {
		Hex hex = svc.getGame().getBoard().getHexes().get(hexId);

		ArrayList<Integer> checkedList = new ArrayList<Integer>();
		ArrayList<Integer> uncheckedList = new ArrayList<Integer>();

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
<<<<<<< HEAD
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
=======
		int weight = minValue + 1;//(hex.getType().equals(Hex.HexType.SWAMP)?2:1);

		int availableMovesForSelectedThing; //=
		//for now
		availableMovesForSelectedThing = 4;
		if (weight > availableMovesForSelectedThing + 1) {//|| (hex.getType().equals(Hex.HexType.SEA  )&& weight ==availableMovesForSelectedThing)){ 
			Util.log("    id:" + hexId + "-->not selectable");
>>>>>>> 35ce1c56cdfad54c35f765cd7a597e27db5fc074
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
}
