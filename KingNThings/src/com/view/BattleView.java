package com.view;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import com.model.Battle;
import com.model.Creature;
import com.model.Fort;
import com.model.GamePiece;
import com.presenter.BattlePresenter;
import com.presenter.DicePresenter;

public class BattleView extends VBox{
	
	BattlePresenter battlePresenter;
	private Battle battle;
	
	private Label titleLbl;
	private Label turnLbl;
	private Label roundNumLbl;
	private Label battleRoundLbl;
	private Label hexLbl;
	
	private Label offenderLbl;
	private DiceView offDice;
	private GridPane offGrid;
	private HBox offButtonBox;
	private Button offRetreatBtn;
	private Button offContinueBtn;
	
	private Label defenderLbl;
	private DiceView defDice;
	private GridPane defGrid;
	private HBox defButtonBox;
	private Button defContinueBtn;
	private Button defRetreatBtn;
	
	public BattleView(){
		buildPopup();
	}
	
	private void buildPopup(){
		getChildren().clear();
		
		setAlignment(Pos.CENTER);
		titleLbl = new Label("Battle:");
		titleLbl.getStyleClass().add("title");
		HBox subTitleLbls = new HBox();
		subTitleLbls.getStyleClass().add("largeSpacing");
		subTitleLbls.setAlignment(Pos.CENTER);
		subTitleLbls.setMinHeight(40);
		turnLbl = new Label();
		roundNumLbl = new Label();
		battleRoundLbl = new Label();
		hexLbl = new Label();
		subTitleLbls.getChildren().addAll(turnLbl, battleRoundLbl, hexLbl, roundNumLbl);
		
		// setup offender
		offenderLbl = new Label();
		offGrid = new GridPane();
		offDice = new DiceView();
		new DicePresenter(offDice);
		offButtonBox = new HBox();
		offRetreatBtn = new Button("Retreat");
		offContinueBtn = new Button("Continue");
		VBox offenderBox = playerBox(offenderLbl, offGrid, offDice, offButtonBox, offRetreatBtn, offContinueBtn);
		
		// setup defender
		defenderLbl = new Label();
		defGrid = new GridPane();
		defDice = new DiceView();
		new DicePresenter(defDice);
		defButtonBox = new HBox();
		defRetreatBtn = new Button("Retreat");
		defContinueBtn = new Button("Continue");
		VBox defenderBox = playerBox(defenderLbl, defGrid, defDice, defButtonBox, defRetreatBtn, defContinueBtn);
		
		HBox offenderDefenderBox = new HBox();
		offenderDefenderBox.getStyleClass().add("block");
		offenderDefenderBox.getChildren().addAll(offenderBox, defenderBox);
		
		getChildren().addAll(titleLbl, subTitleLbls, offenderDefenderBox);	
	}
	
	private VBox playerBox(Label playerLbl, GridPane playerGrid, DiceView playerDice, HBox playerButtonBox,
											Button playerRetreatBtn, Button playerContinueBtn){
		VBox playerBox = new VBox();
		playerBox.setMinWidth(600);
		playerBox.getStyleClass().add("block");
		playerLbl.getStyleClass().add("title");
		playerBox.getChildren().add(playerLbl);
	    playerGrid.setHgap(5);
	    playerGrid.setVgap(5);
	    playerGrid.setMinHeight(520);
	    playerBox.getChildren().add(playerGrid);
		playerDice.getEndTurnBtn().setVisible(false);
		playerButtonBox.getStyleClass().add("block");
		playerButtonBox.getChildren().addAll(playerRetreatBtn, playerContinueBtn);
		AnchorPane defAp = new AnchorPane();
		defAp.getChildren().addAll(playerDice, playerButtonBox);
		AnchorPane.setRightAnchor(playerButtonBox, 55.0);
		playerBox.getChildren().addAll(defAp);
		return playerBox;
	}
	
	public void setBattle(Battle battle){
		
		this.battle = battle;
		setTitleLblText("<Instructions>");
		setRoundNumLbl();
		setBattleRoundLbl();
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
		grid.getChildren().clear();
		for (int i = 0; i<offCreatures.size();i++){
			HBox thingBox = new HBox();
			StackPane stack = new StackPane();

			int size = 90;

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
				Label abilityLbl = new Label("Abilites: "+c.getAbilitiesString());
				thingLblBox.getChildren().addAll(nameLbl, domainLbl, comValLbl, abilityLbl);
			}
			//TODO instance of fort and others
			else if (offCreatures.get(i) instanceof Fort){
				
				Fort gp = (Fort)offCreatures.get(i);
				img.setImage(gp.getImage());
				Label nameLbl = new Label(gp.getName().toUpperCase());
				coloredRect.setFill(gp.getColor());
				thingLblBox.getChildren().addAll(nameLbl);
			}
		}
	}
	
	public void refreshView(String instructions){
		setTitleLblText(instructions);
		setTurnLblText();
		setRoundNumLbl();
		setBattleRoundLbl();
	}
	
	// getter dones and setter dones
	public void setPresenter(BattlePresenter bp) {
		battlePresenter = bp;
	}

	public Label getTitleLbl() {
		return titleLbl;
	}

	public void setTurnLblText() {
		if(battle.getCurrentPlayer() == null)
			turnLbl.setText("Turn: Creature and Things");
		else
			turnLbl.setText("Turn: "+battle.getCurrentPlayer().getName());
	}
	
	public void setTitleLblText(String instructions) {
		titleLbl.setText("Battle: "+instructions);
	}

	public void setRoundNumLbl() {
		roundNumLbl.setText("Round Number: "+battle.getRoundNumber());
	}

	public void setBattleRoundLbl() {
		battleRoundLbl.setText("Phase: "+battle.getBattlePhase().phaseAsString);
	}

	public GridPane getOffGrid() {
		return offGrid;
	}

	public void setOffGrid(GridPane offGrid) {
		this.offGrid = offGrid;
	}

	public GridPane getDefGrid() {
		return defGrid;
	}

	public void setDefGrid(GridPane defGrid) {
		this.defGrid = defGrid;
	}

	public Battle getBattle() {
		return battle;
	}

	public DiceView getOffDice() {
		return offDice;
	}

	public HBox getOffButtonBox() {
		return offButtonBox;
	}

	public Button getOffRetreatBtn() {
		return offRetreatBtn;
	}

	public Button getOffContinueBtn() {
		return offContinueBtn;
	}

	public HBox getDefButtonBox() {
		return defButtonBox;
	}

	public Button getDefContinueBtn() {
		return defContinueBtn;
	}

	public Button getDefRetreatBtn() {
		return defRetreatBtn;
	}

	public DiceView getDefDice() {
		return defDice;
	}

}
