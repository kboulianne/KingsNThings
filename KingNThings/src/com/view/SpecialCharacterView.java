package com.view;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.model.Cup;
import com.model.Die;
import com.model.Player;
import com.model.SpecialCharacter;
import com.presenter.DicePresenter;
import com.view.customcontrols.ThingView;

import static com.main.KNTAppFactory.*;

public class SpecialCharacterView{
	
	private VBox screen1;
	private Label title1;
	private Label ownedSCharLbl;
	private Label ownedSCharIntructionLbl;
	private HBox playersSCharBox;
	private Label cupSCharLbl;
	private Label cupSCharInstructionLbl;
	private HBox availCupSCharBox;
	
	private VBox screen2;
	private Label title2;
	private VBox tvHolder;
	private Label rollValueLbl;
	private Label valueNeededLbl;
	private DiceView dV;
	private Die die1;
	private Die die2;
	private Button recruitButton;
	private VBox rollBox;
	private Label costLbl;
	private Button addRollButton;
	private VBox addRollBox;
	
	public SpecialCharacterView(){
		buildScreen1();
		buildScreen2();
	}
	
	private void buildScreen1(){
		screen1 = new VBox();
		screen1.getStyleClass().add("largeSpacing");
		screen1.setAlignment(Pos.CENTER);
		
		title1 = new Label("Special Characters:");
		title1.getStyleClass().add("title");
		
		ownedSCharLbl = new Label();
		ownedSCharIntructionLbl = new Label();
		playersSCharBox = new HBox();
		playersSCharBox.setAlignment(Pos.CENTER);
		playersSCharBox.getStyleClass().add("army");
		
		availCupSCharBox = new HBox();
		availCupSCharBox.setAlignment(Pos.CENTER);
		availCupSCharBox.getStyleClass().add("army");
		cupSCharLbl = new Label();
		cupSCharInstructionLbl = new Label();
		
		screen1.getChildren().addAll(title1, ownedSCharLbl, ownedSCharIntructionLbl, playersSCharBox,
									cupSCharLbl, cupSCharInstructionLbl, availCupSCharBox);
	}
	
	private void buildScreen2(){
		screen2 = new VBox();
		screen2.getStyleClass().add("largeSpacing");
		screen2.setAlignment(Pos.CENTER);
		
		title2 = new Label("Recruit Special Character:");
		title2.getStyleClass().add("title");
		
		tvHolder = new VBox();
		tvHolder.setAlignment(Pos.CENTER);
		rollValueLbl = new Label();
		valueNeededLbl = new Label();
		dV = new DiceView();
		die1 = new Die();
		die2 = new Die();
		dV.setAlignment(Pos.CENTER);
		// TODO: May need to modify me.
		new DicePresenter(dV, die1, die2);
		recruitButton = dV.getEndTurnBtn();
		recruitButton.setText("Recruit");
		rollBox = new VBox();
		rollBox.setAlignment(Pos.CENTER);
		rollBox.getStyleClass().add("block");
		rollBox.getChildren().addAll(rollValueLbl,valueNeededLbl,  dV);
		costLbl = new Label();
		addRollButton = new Button();
		addRollBox = new VBox();
		addRollBox.setAlignment(Pos.CENTER);
		addRollBox.getStyleClass().add("block");
		addRollBox.getChildren().addAll(costLbl, addRollButton);
		
		screen2.getChildren().addAll(title2, tvHolder, addRollBox, rollBox);
	}
	
	
	VBox setScreen1(Player current, List<SpecialCharacter> playersSChars, Cup cup){
		List<SpecialCharacter> specCfromCup = cup.getListOfSpecialCharacters();
		boolean playerHasLord = false;
		
		
		ownedSCharLbl.setText(current.getName()+"'s special characters: "+playersSChars.size()+" items");
		if(playersSChars.isEmpty()){
			ownedSCharIntructionLbl.setText("");
		}
		else{
			ownedSCharIntructionLbl.setText("Click to place special character back in cup");
		}

		playersSCharBox.getChildren().clear();
		for(SpecialCharacter sC: playersSChars){
			ThingView tv = new ThingView(50, sC);
			tv.setSendSpecialCharacterBackToCupHandler();
			playersSCharBox.getChildren().add(tv);
			
			if(sC.isLord())
				playerHasLord = true;
		}
		cupSCharInstructionLbl.setText("Choose a special characters to recruit");
		
		
		availCupSCharBox.getChildren().clear();
		for(SpecialCharacter sC: specCfromCup){
			if(!(sC.isLord() && playerHasLord)){
				sC.setSelected(false);
				ThingView tv = new ThingView(50, sC);
				tv.setChooseSpecialCharToRecruitHandler(current);
				availCupSCharBox.getChildren().add(tv);
			}
		}
		
		cupSCharLbl.setText("Available special characters from cup: "+availCupSCharBox.getChildren().size()+" items");
		return screen1;
	}
	
	VBox setScreen2(final SpecialCharacter sC, final Player current){

		final int valueNeeded = sC.getCombatVal()*2;
		getSpecialCharacterPresenter().setSelectedSpecialCharacter(sC);
		getSpecialCharacterPresenter().setRolledValue(0);
		getSpecialCharacterPresenter().setCost(5);
		
		tvHolder.getChildren().clear();
		ThingView tv = new ThingView(200,sC);
		tvHolder.getChildren().add(tv);
		
		// TODO: Will this cause problems.
		rollValueLbl.setText("Current rolled value: "+ getSpecialCharacterPresenter().getRolledValue());
		valueNeededLbl.setText("Value needed to recuit character: "+valueNeeded);

		
		costLbl.setText("Cost to add 1 to roll: "+ getSpecialCharacterPresenter().getCost() + " Gold");
		addRollButton.setText("Add to Roll");
		if(current.getGold()<5)
			addRollButton.setDisable(true);
		else
			addRollButton.setDisable(false);
		
		dV.getRollBtn().setVisible(true);
		dV.getRollBtn().setManaged(true);
		recruitButton.setDisable(true);
		// handlers
		getSpecialCharacterPresenter().setOnAddButtonPressHandler(valueNeeded);		
		getSpecialCharacterPresenter().setOnRollButtonPressedHandler(valueNeeded);
		getSpecialCharacterPresenter().setOnRecuitButtonPressHandler();
		
		return screen2;
	}

	public DiceView getdV() {
		return dV;
	}

	public Button getRecruitButton() {
		return recruitButton;
	}

	public Button getAddRollButton() {
		return addRollButton;
	}

	public Label getRollValueLbl() {
		return rollValueLbl;
	}

	public Label getCostLbl() {
		return costLbl;
	}
}
