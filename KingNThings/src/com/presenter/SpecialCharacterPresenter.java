package com.presenter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.game.phase.GamePlay;
import com.main.KNTAppFactory;
import com.model.Game;
import com.model.Player;
import com.model.SpecialCharacter;
import com.util.Util;
import com.view.SpecialCharacterView;

public class SpecialCharacterPresenter {

	private SpecialCharacterView view;
	
	private int rolledValue; // accumulated value
	private int cost; // cost is 5 before roll and 10 after roll
	private SpecialCharacter selectedSpecialCharacter;
	
	public SpecialCharacterPresenter(SpecialCharacterView v){
		view = v;
		view.setPresenter(this);
	}
	
	//handlers
	public void setOnAddButtonPressHandler(final int valueNeeded){
		view.getAddRollButton().setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent arg0) {
				Game game = KNTAppFactory.getGamePresenter().getLocalInstance();
				Player currPlayer = game.getCurrentPlayer();
				if(currPlayer.getGold() >= cost){
					Util.playClickSound();
					currPlayer.setGold(currPlayer.getGold()-cost);
					KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(currPlayer);
					
					rolledValue++;
					view.getRollValueLbl().setText("Current rolled value: "+rolledValue);
					
					if(rolledValue >= valueNeeded)
						view.getRecruitButton().setDisable(false);
					
					if(currPlayer.getGold()<cost)
						view.getAddRollButton().setDisable(true);
				}
			}
		});
	}
	
	public void setOnRecuitButtonPressHandler(){
		view.getRecruitButton().setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				Game game = KNTAppFactory.getGamePresenter().getLocalInstance();
				Util.playClickSound();
				game.moveThingFromCupToPlayer(selectedSpecialCharacter, game.getCurrentPlayer());
				KNTAppFactory.getGamePresenter().endTurn();
			}
		});
	}
	
	public void setOnRollButtonPressedHandler(final int valueNeeded){
		view.getdV().getRollBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				int rollVal = KNTAppFactory.getDicePresenter().roll();
				rolledValue += rollVal;
				
				view.getRollValueLbl().setText("Current rolled value: "+rolledValue);
				cost = 10;
				view.getCostLbl().setText("Cost to add 1 to roll: "+cost+" Gold");
				
				
				if(rolledValue >= valueNeeded)
					view.getRecruitButton().setDisable(false);
				
				view.getdV().getRollBtn().setVisible(false);
				view.getdV().getRollBtn().setManaged(false);
			}
		});
	}
	
	
	// setters and getters
	public int getCost() {
		return cost;
	}

	public SpecialCharacter getSelectedSpecialCharacter() {
		return selectedSpecialCharacter;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getRolledValue() {
		return rolledValue;
	}

	public void setRolledValue(int rolledValue) {
		this.rolledValue = rolledValue;
	}

	public SpecialCharacterView getView() {
		return view;
	}

	public void setSelectedSpecialCharacter(
			SpecialCharacter selectedSpecialCharacter) {
		this.selectedSpecialCharacter = selectedSpecialCharacter;
	}
}
