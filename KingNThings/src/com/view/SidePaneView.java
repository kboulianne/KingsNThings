/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedMap;

import com.view.customcontrols.PlayerLabel;
import com.main.KNTAppFactory;
import com.model.Player;
import com.model.SpecialCharacter;
import com.model.game.Game;
import com.presenter.SidePanePresenter;
import com.presenter.Util;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author kurtis
 */
public class SidePaneView extends VBox {

    // FIXME: Duplicate, put in global stuff? Put in GamePresenter?
	// View specific stuff
	static final double HEX_WIDTH = 100.0;
	static final double HEX_HEIGHT = HEX_WIDTH * 0.8;

	private SidePanePresenter presenter;

    // Current detailsview being displayed in the view
	// TODO Create abstract DetailsView?
	private StackPane content;

	private PlayerLabel opp1Lbl;
	private PlayerLabel opp2Lbl;
	private PlayerLabel opp3Lbl;

	public SidePaneView() {
		buildView();

		addHandlers();
	}

	protected void buildView() {
		// Initial setup
		setId("sidePane");
		//FIXME: Hardcoded
		setMinWidth(1280 * 0.5 - 20);

		// Opponents
		HBox otherPlayerInfo = new HBox();
		otherPlayerInfo.setId("otherPlayerInfo");
		otherPlayerInfo.setAlignment(Pos.TOP_CENTER);
		opp1Lbl = new PlayerLabel();
		opp2Lbl = new PlayerLabel();
		opp3Lbl = new PlayerLabel();
		otherPlayerInfo.getChildren().addAll(opp1Lbl, opp2Lbl, opp3Lbl);

		// TODO make Content take the background image.
		content = new StackPane();
		content.setId("detailsBox");
		content.setMinHeight(HEX_HEIGHT * 7);

		// Adds opponents and details container
		getChildren().addAll(otherPlayerInfo, content);

	}

	public void addHandlers() {
		EventHandler<MouseEvent> playerLabelHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				PlayerLabel lbl = (PlayerLabel) t.getSource();
				// TODO create a custom event.
				presenter.showOpponentInfo(lbl.getPlayer());
			}
		};
		opp1Lbl.setOnMouseEntered(playerLabelHandler);
		opp2Lbl.setOnMouseEntered(playerLabelHandler);
		opp3Lbl.setOnMouseEntered(playerLabelHandler);

		EventHandler<MouseEvent> exitHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				presenter.dismissOpponentInfo();
			}
		};
		opp1Lbl.setOnMouseExited(exitHandler);
		opp2Lbl.setOnMouseExited(exitHandler);
		opp3Lbl.setOnMouseExited(exitHandler);
	}

	public void setPresenter(final SidePanePresenter presenter) {
		if (presenter == null) {
			throw new NullPointerException("Presenter cannot be null");
		}

		if (this.presenter != null) {
			throw new IllegalStateException("The presenter was already set.");
		}

		this.presenter = presenter;
	}

	// TODO pass list/set?
	public void setOpponents(Player o1, Player o2, Player o3) {
		opp1Lbl.setPlayer(o1);
		opp2Lbl.setPlayer(o2);
		opp3Lbl.setPlayer(o3);
	}
	
	public void showGoldCollection(int hexGold, int fortGold, int counterGold, int specCharGold){
		int totalGold = (hexGold + fortGold + counterGold + specCharGold);
		Label title = new Label("Gold Income:");
		title.getStyleClass().add("title");
		
		ImageView im = new ImageView(Game.GOLD_IMAGE);
		im.setFitWidth(300);
		im.setPreserveRatio(true);
		
		Label hexes = new Label("Hex Income:  " + hexGold);
		Label forts = new Label("Fort Income:  " + fortGold);
		Label counters = new Label("Counter Income:  " + counterGold);
		Label specChars = new Label("Character Income:  " + specCharGold);
		Label divider = new Label(" ");
		Label total = new Label("Total Income:  " + totalGold);
		VBox labels = new VBox();
		
		labels.setAlignment(Pos.CENTER);
		labels.getChildren().addAll(hexes, forts, counters, specChars, divider, total);

		VBox vbox = new VBox();
		//popupVbox.setPadding(new Insets(15));
		vbox.setSpacing(20);//getStyleClass().add("block");
		vbox.getChildren().addAll(title, im, labels);
		vbox.setAlignment(Pos.CENTER);
		vbox.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		
		content.getChildren().clear();
		content.getChildren().add(vbox);
	}
	
	public void showSpecialCharRecruitment(SpecialCharacter thing) {
		content.getChildren().clear();
		KNTAppFactory.getSpecialCharacterPresenter().getView().showScreen2(thing);
		content.getChildren().add(KNTAppFactory.getSpecialCharacterPresenter().getView().getScreen2());
	}
	
<<<<<<< HEAD
	public void showSpecialCharRecruitment(String playerName, ArrayList<SpecialCharacter> playersSChars){
=======
	
	int rolledValue = 0;
	int cost = 5; // cost is 5 before roll and 10 after roll
	public void showSpecialCharRecruitment2(SpecialCharacter sC){

		final int valueNeeded = sC.getCombatVal()*2;
		
		VBox vbox = new VBox();
		vbox.getStyleClass().add("largeSpacing");
		vbox.setAlignment(Pos.CENTER);
		Label title = new Label("Recruit Special Character:");
		title.getStyleClass().add("title");
		ThingView tv = new ThingView(200,sC);
		
		final Label rollValueLbl = new Label("Current rolled value: "+rolledValue);
		Label valueNeededLbl = new Label("Value needed to recuit character: "+valueNeeded);
		final DiceView dV = new DiceView();
		final Die die1 = new Die();
		final Die die2 = new Die();
		dV.setDice(die1, die2);
		dV.setAlignment(Pos.CENTER);
		final Button recruitButton = dV.getEndTurnBtn();
		recruitButton.setText("Recruit");
		recruitButton.setDisable(true);
		VBox rollBox = new VBox();
		rollBox.setAlignment(Pos.CENTER);
		rollBox.getStyleClass().add("block");
		rollBox.getChildren().addAll(rollValueLbl,valueNeededLbl,  dV);
		
		final Label costLbl = new Label("Cost to add 1 to roll: "+cost+" Gold");
		final Button addRollButton = new Button("Add to Roll");
		if(GameService.getInstance().getGame().getCurrentPlayer().getGold()<5)
			addRollButton.setDisable(true);
		
		VBox addRollBox = new VBox();
		addRollBox.setAlignment(Pos.CENTER);
		addRollBox.getStyleClass().add("block");
		addRollBox.getChildren().addAll(costLbl, addRollButton);
		
		// handlers
		addRollButton.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent arg0) {
				//get gold check
				Player currPlayer = GameService.getInstance().getGame().getCurrentPlayer();
				if(currPlayer.getGold()>=cost){
					
					currPlayer.setGold(currPlayer.getGold()-cost);
					KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(currPlayer);
					
					rolledValue += 1;
					rollValueLbl.setText("Current rolled value: "+rolledValue);
					
					if(rolledValue >= valueNeeded)
						recruitButton.setDisable(false);
					
					if(currPlayer.getGold()<cost)
						addRollButton.setDisable(true);
				}
			}
		});		
		
		dV.getRollBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				rolledValue += die1.roll() + die2.roll();
				
				rollValueLbl.setText("Current rolled value: "+rolledValue);
				cost = 10;
				costLbl.setText("Cost to add 1 to roll: "+cost+" Gold");
				
				
				if(rolledValue >= valueNeeded)
					recruitButton.setDisable(false);
				
				dV.getRollBtn().setVisible(false);
				dV.getRollBtn().setManaged(false);
			}
		});
		
		recruitButton.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// endturn
			}
		});
		
		vbox.getChildren().addAll(title,tv,  addRollBox, rollBox);
>>>>>>> 423a603034c5bd7c1bfd975cc3f4756de4f571ff
		content.getChildren().clear();
		KNTAppFactory.getSpecialCharacterPresenter().getView().showScreen1(playerName, playersSChars);
		content.getChildren().add(KNTAppFactory.getSpecialCharacterPresenter().getView().getScreen1());
	}
	

	public void showThingRecruitment(int freeRecruits) {
		ThingRecruitmentView view = new ThingRecruitmentView(freeRecruits);	
		
		content.getChildren().clear();
		content.getChildren().add(view);
	}
	
	public void showRolls(SortedMap<Integer, Player> rolls){
		Iterator<Integer> it = rolls.keySet().iterator();
		if(it.hasNext()){
			VBox display = new VBox();
			display.setMinHeight(500);
			ImageView iv = new ImageView(Game.DICE_IMAGE);
			iv.setFitHeight(200.0);
			iv.setPreserveRatio(true);
			display.getChildren().add(iv);
			while(it.hasNext()){
				int roll = it.next();
				Player p = rolls.get(roll);
				if(p==null)
					Util.log("why is player null?");
				Label lbl = new Label("Player has rolled a " + roll);
				display.getChildren().add(lbl);
			}
			display.getStyleClass().add("block");
			display.setAlignment(Pos.CENTER);
			content.getChildren().clear();
			content.getChildren().add(display);
		}
	}
	
	public void showArbituaryView(String title, Image img){
		VBox display = new VBox();
		ImageView iv = new ImageView(img);
		iv.setFitHeight(200.0);
		iv.setPreserveRatio(true);
		Label lbl = new Label(title);
		display.getChildren().addAll(iv, lbl);
		display.getStyleClass().add("largeSpacing");
		display.setAlignment(Pos.CENTER);
		content.getChildren().clear();
		content.getChildren().add(display);
	}

	public void showHexDetailsView(HexDetailsView view) {
		content.getChildren().clear();
		content.getChildren().add(view);
	}

	public void showThingDetailsView(ThingDetailsView view) {
		content.getChildren().clear();
		content.getChildren().add(view);
	}
	
	public void showArmyDetailsView(ArmyDetailsView view) {
		content.getChildren().clear();
		content.getChildren().add(view);
	}
	
	public void clearDetailsView(){
		content.getChildren().clear();
	}
}
