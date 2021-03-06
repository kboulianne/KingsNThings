/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.view.customcontrols.ArmyOrMisc;
import com.model.Hex;
import com.model.Player;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author kurtis
 */
public class HexDetailsView extends StackPane {

	private ImageView hexImage;
	private Label nameLbl;
	private Label ownerLbl;
	private ArmyOrMisc counter;
	private ArmyOrMisc opp1Army;
	private ArmyOrMisc opp2Army;
	private ArmyOrMisc opp3Army;
	private ArmyOrMisc currentPlayerArmy;
	

	public HexDetailsView() {
		buildView();
	}

	private void buildView() {

		VBox content = new VBox();
		// TODO Put in create Methods
		// Define content. (FROM paintHexInDetails
		hexImage = new ImageView();
		hexImage.setFitWidth(300);
		hexImage.setPreserveRatio(true);
		hexImage.setSmooth(true);
		hexImage.setCache(true);
		getStyleClass().add("block");
		VBox contentBox = new VBox();
		contentBox.getStyleClass().add("block");
		contentBox.setAlignment(Pos.CENTER);

		// Name Label
		nameLbl = new Label();
		nameLbl.getStyleClass().add("title");
		// owner
		ownerLbl = new Label();

		contentBox.getChildren().addAll(nameLbl, ownerLbl);

        // ArmyOrMisc components
		//misc = new ArmyOrMisc(handler);
		counter = new ArmyOrMisc();
		opp1Army = new ArmyOrMisc();
		opp2Army = new ArmyOrMisc();
		opp3Army = new ArmyOrMisc();
		currentPlayerArmy = new ArmyOrMisc();

		content.getChildren().addAll(contentBox, counter, currentPlayerArmy, opp1Army, opp2Army, opp3Army);
		getChildren().addAll(hexImage, content);
		content.setAlignment(Pos.CENTER);
		content.getStyleClass().add("block");
	}

	public void setHex(Hex hex, Player current, List<Player> opponents) {
	// update ui here.
		if (hex != null) {
			setArmies(hex, current, opponents);
		
			// Set the Hex Name
			nameLbl.setText(hex.getTypeAsString());
			// Set the new image.
			hexImage.setImage(hex.getImage());
			// Set the owner if it exists
			if (hex.getHexOwner() != null) {
				ownerLbl.setText("Owner: " + hex.getHexOwner().getName());
			} else {
				ownerLbl.setText("Un-Explored");
			}
		}
	}
	
	private void setArmies(Hex hex, Player current, List<Player> opponents){
//		// TODO will crash for 2-3 players
		opp1Army.setArmy(hex, opponents.get(0), hex.getArmies(opponents.get(0)));
		opp2Army.setArmy(hex, opponents.get(1), hex.getArmies(opponents.get(1)));
		opp3Army.setArmy(hex, opponents.get(2), hex.getArmies(opponents.get(2)));
		currentPlayerArmy.setArmy(hex, current, hex.getArmies(current));

		counter.setIncomeCounter(hex, hex.getCounter());
	}

	public ArmyOrMisc getCurrentPlayerArmy() {
		return currentPlayerArmy;
	}


	public ArmyOrMisc getOpp1Army() {
		return opp1Army;
	}

	public ArmyOrMisc getOpp2Army() {
		return opp2Army;
	}

	public ArmyOrMisc getOpp3Army() {
		return opp3Army;
	}
	
	public ArmyOrMisc getCounter()	{
		return counter;
	}
}
