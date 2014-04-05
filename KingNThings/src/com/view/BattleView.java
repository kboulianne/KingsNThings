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
import com.view.customcontrols.ThingView;

import java.util.List;

public class BattleView extends VBox{
	
//	private Battle battle;
	
	private Label titleLbl;
	private Label turnLbl;
	private Label roundNumLbl;
	private Label battleRoundLbl;
	private Label hexLbl;
	private Label infoLbl;
	
	private Label offenderLbl;
	private DiceView offDice;
	private GridPane offGrid;
	private HBox offButtonBox;
	private Label offHitsLbl;
	private Button offRetreatBtn;
	private Button offContinueBtn;
	
	private Label defenderLbl;
//	private DiceView defDice;
	private GridPane defGrid;
//	private HBox defButtonBox;
	private Label defHitsLbl;
//	private Button defContinueBtn;
//	private Button defRetreatBtn;
	
	public BattleView(DiceView dv1, DiceView dv2){
		offDice = dv1;
//		defDice = dv2;
		buildPopup();
	}
	
	private void buildPopup(){
		getChildren().clear();
		
		setAlignment(Pos.CENTER);
		setMinWidth(1240);
		setMinHeight(720);
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
		infoLbl = new Label();
		
		// setup offender
		offenderLbl = new Label();
		offGrid = new GridPane();
		offButtonBox = new HBox();
		offHitsLbl = new Label();
		offRetreatBtn = new Button("Retreat");
		offRetreatBtn.setDisable(true);
		offContinueBtn = new Button("Continue");
		offContinueBtn.setDisable(true);
		VBox offenderBox = playerBox(offenderLbl, offGrid, offDice, offButtonBox, offHitsLbl, offRetreatBtn, offContinueBtn);
		
		// setup defender
		defenderLbl = new Label();
		defGrid = new GridPane();
//		defButtonBox = new HBox();
		defHitsLbl = new Label();
//		defRetreatBtn = new Button("Retreat");
//		defRetreatBtn.setDisable(true);
//		defContinueBtn = new Button("Continue");
//		defContinueBtn.setDisable(true);
//		VBox defenderBox = playerBox(defenderLbl, defGrid, defDice, defButtonBox, defHitsLbl, defRetreatBtn, defContinueBtn);
		VBox defenderBox = defenderBox(defenderLbl, defGrid, defHitsLbl);
		
		HBox offenderDefenderBox = new HBox();
		offenderDefenderBox.getStyleClass().add("block");
		offenderDefenderBox.getChildren().addAll(offenderBox, defenderBox);
		
		getChildren().addAll(titleLbl, subTitleLbls, infoLbl, offenderDefenderBox);	
	}
	
	private VBox defenderBox(Label playerLbl, GridPane playerGrid, Label hitsLbl) {
		VBox playerBox = new VBox();
		playerBox.setMinWidth(600);
		playerBox.getStyleClass().add("block");
		playerLbl.getStyleClass().add("title");
		playerBox.getChildren().add(playerLbl);
	    playerGrid.setHgap(5);
	    playerGrid.setVgap(5);
	    playerGrid.setMinHeight(520);
	    playerBox.getChildren().add(playerGrid);
	    
	    HBox hits = new HBox();
	    hits.getChildren().addAll(hitsLbl);
	    AnchorPane defAp = new AnchorPane();
		defAp.getChildren().addAll(hits);
		playerBox.getChildren().addAll(defAp);
		return playerBox;
	}
	
	private VBox playerBox(Label playerLbl, GridPane playerGrid, DiceView playerDice, HBox playerButtonBox, Label hitsLbl,
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
		playerDice.getEndTurnBtn().setVisible(false); // end turn button not used here
		playerDice.getEndTurnBtn().setManaged(false);
		playerButtonBox.getStyleClass().add("block");
		playerButtonBox.getChildren().addAll(playerRetreatBtn, playerContinueBtn);
		HBox diceAndHits = new HBox();
		diceAndHits.getChildren().addAll(playerDice, hitsLbl);
		diceAndHits.getStyleClass().add("block");
		AnchorPane defAp = new AnchorPane();
		defAp.getChildren().addAll(diceAndHits, playerButtonBox);
		AnchorPane.setRightAnchor(playerButtonBox, 55.0);
		playerBox.getChildren().addAll(defAp);
		return playerBox;
	}
	
	public void updateBattle(final Battle battle){
		setInfoLbl(battle.getInfo());
		setTitleLblText(battle.getInstructions());
		setRoundNumLbl(String.valueOf(battle.getRoundNumber()));
		setBattleRoundLbl(battle.getBattlePhase().phaseAsString);
		setTurnLblText(battle.getCurrentPlayer().getName());
		setOffHitsLbl(String.valueOf(battle.getOffHits()));
		setDefHitsLbl(String.valueOf(battle.getDefHits()));
		hexLbl.setText("Terrain: "+battle.getAssociatedHex().getTypeAsString());
		
		offenderLbl.setText("Offender: "+battle.getOffender().getName());
		List<Creature> offGamePieces = new ArrayList<>();
		offGamePieces.addAll(battle.getOffenderCreatures());
		setBattlePieces(offGamePieces, offGrid);

		defenderLbl.setText("Defender: "+battle.getDefenderName());
		setBattlePieces(battle.getDefenderCreatures(), battle.getDefenderFort(), defGrid);

		
	}
	
	private void setBattlePieces(List<Creature> offCreatures, GridPane grid) {
		setBattlePieces(offCreatures, null, grid);
	}
	
	
	private void setBattlePieces(List<Creature> offCreatures, Fort def, GridPane grid){
		grid.getChildren().clear();
		// Adds creature pieces
		for (int i = 0; i<offCreatures.size();i++){
			HBox thingBox = createCreaturePiece(offCreatures.get(i));

			grid.add(thingBox,i%2, i/2);
		}
		
		// Hack
		if (def != null) {
			int i = offCreatures.size();
			HBox box = createFort(def);
			
			grid.add(box, i % 2, i / 2);
		}
	}
	
	private HBox createCreaturePiece(Creature c) {
		HBox thingBox = new HBox();
		ThingView tv = new ThingView(90 , c);

		tv.setApplyHitsForBattleHandler();
		VBox thingLblBox = new VBox();

		thingBox.getChildren().addAll(tv, thingLblBox);
		thingBox.getStyleClass().add("army");
		
		Label nameLbl = new Label(c.getName().toUpperCase());
		Label domainLbl = new Label("Domain: "+c.getDomain());
		Label comValLbl = new Label("Combat Value: "+c.getCombatVal());
		Label abilityLbl = new Label("Abilites: "+c.getAbilitiesString());
		thingLblBox.getChildren().addAll(nameLbl, domainLbl, comValLbl, abilityLbl);
		
		
		return thingBox;
	}
	
	private HBox createFort(Fort f) {
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

		img.setImage(f.getImage());
		Label nameLbl = new Label(f.getName().toUpperCase());
		coloredRect.setFill(f.getColor());
		thingLblBox.getChildren().addAll(nameLbl);
		
		return thingBox;
	}

	
	// getter dones and setter dones
	public Label getTitleLbl() {
		return titleLbl;
	}
	private void setTurnLblText(String name) {
		turnLbl.setText("Turn: " + name);
	}
	private void setOffHitsLbl(String off) {
		offHitsLbl.setText("Hits: " + off);
	}
	private void setDefHitsLbl(String def) {
		defHitsLbl.setText("Hits: " + def);
	}
	public void setInfoLbl(String info) {
		infoLbl.setText(info);
	}
	public void setTitleLblText(String instructions) {
		titleLbl.setText("Battle: "+instructions);
	}
	private void setRoundNumLbl(String num) {
		roundNumLbl.setText("Round Number: " + num);
	}
	private void setBattleRoundLbl(String round) {
		battleRoundLbl.setText("Phase: " + round);
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
//	public Battle getBattle() {
//		return battle;
//	}
	public HBox getOffButtonBox() {
		return offButtonBox;
	}
	public Button getOffRetreatBtn() {
		return offRetreatBtn;
	}
	public Button getOffContinueBtn() {
		return offContinueBtn;
	}
	
	public void hideControls() {
		offButtonBox.setVisible(false);
		offDice.getRollBtn().setVisible(false);
	}
	
	public void showControls() {
		offButtonBox.setVisible(true);
		offDice.getRollBtn().setVisible(true);
	}
	
//	public HBox getDefButtonBox() {
//		return defButtonBox;
//	}
//	public Button getDefContinueBtn() {
//		return defContinueBtn;
//	}
//	public Button getDefRetreatBtn() {
//		return defRetreatBtn;
//	}
}
