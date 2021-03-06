/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.view.customcontrols.PlayerLabel;
import com.main.KNTAppFactory;
import com.model.Player;
import com.view.customcontrols.Rack;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author kurtis
 */
public class PlayerInfoView extends AnchorPane {

	private PlayerLabel currentPlayerlbl;
	private Label goldLbl;
	private Rack rack;

	private ImageView viewCupImg;

	public PlayerInfoView() {
		buildView();
	}

	private void buildView() {
		setId("bottomSection");

		// Current PlayerInfo Box. Wraps PlayerLabel and Gold VBox, and Rack.
		HBox currentPlayerInfoBox = new HBox();
		currentPlayerInfoBox.setId("playerInfo");

		// Holds PlayerLabel and Gold
		VBox currentPlayerNameAndGold = new VBox();
		currentPlayerNameAndGold.setAlignment(Pos.CENTER);

		// Current player and gold
		currentPlayerlbl = new PlayerLabel();
		currentPlayerlbl.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				KNTAppFactory.getPlayerInfoPresenter().showCurrentPlayerInfo();
			}
		});
		currentPlayerlbl.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				KNTAppFactory.getPlayerInfoPresenter().dismissPopup();
			}
		});

		goldLbl = new Label();
		currentPlayerNameAndGold.getChildren().addAll(currentPlayerlbl, goldLbl);

		// Rack
		rack = new Rack();
		currentPlayerInfoBox.getChildren().addAll(currentPlayerNameAndGold, rack);


		viewCupImg = new ImageView(new Image("view/com/assets/pics/cup.png"));
		viewCupImg.getStyleClass().add("hand");
		viewCupImg.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				KNTAppFactory.getPlayerInfoPresenter().handleCupClick();
			}
		});
		viewCupImg.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				KNTAppFactory.getPlayerInfoPresenter().dismissPopup();
			}
		});
		

		AnchorPane.setLeftAnchor(currentPlayerInfoBox, 10.0);
		AnchorPane.setTopAnchor(currentPlayerInfoBox, 5.0);
		AnchorPane.setRightAnchor(viewCupImg, 10.0);
		AnchorPane.setTopAnchor(viewCupImg, 8.0);

		getChildren().addAll(viewCupImg, currentPlayerInfoBox);
	}

	public void setPlayer(Player current) {
		if (current != null) {
			currentPlayerlbl.setPlayer(current);
			goldLbl.setText("Gold: " + String.valueOf(current.getGold()));
			
			rack.setThings(current.getBlock().getListOfThings());
		}
	}
	
	public void setRackExchangeThingsHandler(Player current){
		if (current != null) {
			rack.setExchangeThingsHandler(current.getBlock().getListOfThings());
		}
	}
	
	public void setRackRecruitingThingsHandler(Player current)	{
		if(current != null)	{
			rack.setRecruitingThingsHandler(current.getBlock().getListOfThings());
		}
	}
	
	public void setRackDefaultHandler(Player current){
		if (current != null) {
			rack.setThings(current.getBlock().getListOfThings());
		}
	}
		
	public void setRackDoNothingHandler(Player current){
		if (current != null) {
			rack.setDoNothingHandler(current.getBlock().getListOfThings());
		}
	}
	
	public Rack getRack()	{	return rack;	}
}
