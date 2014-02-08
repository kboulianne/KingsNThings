package com.view.popups;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import com.main.KNTAppFactory;
import com.model.Battle;
import com.model.Creature;
import com.model.GamePiece;
import com.view.DiceView;

public class BattlePopup extends VBox{
	
	Label titleLbl;
	Label roundNumLbl;
	Label battleRoundLbl;
	Label hexLbl;
	
	Label offenderLbl;
	DiceView offDice;
	GridPane offGrid;
	
	Label defenderLbl;
	DiceView defDice;
	GridPane defGrid;
	
	public BattlePopup(){
		buildPopup();
	}
	
	private void buildPopup(){
		setAlignment(Pos.CENTER);
		titleLbl = new Label("Battle:");
		titleLbl.getStyleClass().add("title");
		HBox subTitleLbls = new HBox();
		subTitleLbls.getStyleClass().add("largeSpacing");
		subTitleLbls.setAlignment(Pos.CENTER);
		roundNumLbl = new Label();
		battleRoundLbl = new Label();
		hexLbl = new Label();
		subTitleLbls.getChildren().addAll(battleRoundLbl, hexLbl, roundNumLbl);
		
		// offender
		VBox offenderBox = new VBox();
		offenderBox.getStyleClass().add("block");
		offenderLbl = new Label();
		offenderLbl.getStyleClass().add("title");
		offenderBox.getChildren().add(offenderLbl);
		offGrid = new GridPane();
	    offGrid.setHgap(5);
	    offGrid.setVgap(5);
	    offenderBox.getChildren().add(offGrid);
		offDice = new DiceView();
		offDice.setEndTurnButtonLbl("Retreat");
		offenderBox.getChildren().add(offDice);
		
		// defender
		VBox defenderBox = new VBox();
		defenderBox.getStyleClass().add("block");
		defenderLbl = new Label();
		defenderLbl.getStyleClass().add("title");
		defenderBox.getChildren().add(defenderLbl);
		defGrid = new GridPane();
	    defGrid.setHgap(5);
	    defGrid.setVgap(5);
	    defenderBox.getChildren().add(defGrid);
		defDice = new DiceView();
		defDice.setEndTurnButtonLbl("Retreat");
		defenderBox.getChildren().add(defDice);
		
		HBox offenderDefenderBox = new HBox();
		offenderDefenderBox.getChildren().addAll(offenderBox, defenderBox);
		
		getChildren().addAll(titleLbl, subTitleLbls, offenderDefenderBox);
		
		
		
		
	}
	
	public void setPopup(Battle battle){
		titleLbl.setText("Battle: <Instructions>");
		roundNumLbl.setText("Round Number: "+battle.getRoundNumber());
		battleRoundLbl.setText("Phase: "+battle.getBattleRound().battleRoundName);
		hexLbl.setText("Terrain: "+battle.getAssociatedHex().getTypeAsString());
		
		offenderLbl.setText("Offender: "+battle.getOffender().getName());
		ArrayList<GamePiece> offGamePieces = new ArrayList<GamePiece>();
		offGamePieces.addAll(battle.getOffenderCreatures());
		setBattlePieces(offGamePieces, offGrid);
		offDice.setDice(battle.getOffenderDie1(), battle.getOffenderDie2());
		
		defenderLbl.setText("Defender: "+battle.getDefenderName());
		setBattlePieces(battle.getDefenderItems(), defGrid);
		defDice.setDice(battle.getDefenderDie1(), battle.getDefenderDie2());
		
	}
	
	public void setBattlePieces(ArrayList<GamePiece> offCreatures, GridPane grid){
		for (int i = 0; i<offCreatures.size();i++){
			HBox thingBox = new HBox();
			StackPane stack = new StackPane();

			int size = 70;

			Rectangle borderRect = new Rectangle();
			borderRect.setX(0);
			borderRect.setY(0);
			borderRect.setWidth(size);
			borderRect.setHeight(size);
			borderRect.setArcWidth(20);
			borderRect.setArcHeight(20);
			borderRect.setFill(Color.WHITE);

			Rectangle coloredRect = new Rectangle();
			coloredRect.setX(0);
			coloredRect.setY(0);
			coloredRect.setWidth(size - 1);
			coloredRect.setHeight(size - 1);
			coloredRect.setArcWidth(20);
			coloredRect.setArcHeight(20);
			

			ImageView img = new ImageView();
			img.setFitWidth(size - 7);
			img.setFitHeight(size - 7);
			img.setPreserveRatio(true);
			img.setSmooth(true);
			img.setCache(true);
			stack.getChildren().addAll(borderRect, coloredRect, img);
			
			VBox thingLblBox = new VBox();
			
			thingBox.getChildren().addAll(stack, thingLblBox);
			thingBox.getStyleClass().add("army");
			
			grid.add(thingBox,i%2, i/2);
			
			if (offCreatures.get(i) instanceof Creature){
				Creature c = (Creature) offCreatures.get(i);
				img.setImage(c.getImage());
				Label nameLbl = new Label(c.getName().toUpperCase());
				coloredRect.setFill(c.getColor());
				Label domainLbl = new Label("Domain: "+c.getDomain());
				Label comValLbl = new Label("Combat Value: "+c.getCombatVal());
				thingLblBox.getChildren().addAll(nameLbl, domainLbl, comValLbl);
			}
			//TODO instance of fort and others
			else{
				
				GamePiece gp = offCreatures.get(i);
				img.setImage(gp.getImage());
				Label nameLbl = new Label(gp.getName().toUpperCase());
				coloredRect.setFill(Color.BLACK);
				thingLblBox.getChildren().addAll(nameLbl);
			}
		}
	}

}
