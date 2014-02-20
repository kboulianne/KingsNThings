/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.view.customcontrols.PlayerLabel;
import com.model.Player;
import com.presenter.PlayerInfoPresenter;
import com.view.customcontrols.Rack;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author kurtis
 */
public class PlayerInfoView extends AnchorPane {

	private PlayerInfoPresenter presenter;

	private PlayerLabel currentPlayerlbl;
	private Label goldLbl;
	private Rack rack;

	private Button viewCupBtn;

	public PlayerInfoView() {
		buildView();
	}

	protected void buildView() {
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
				presenter.showCurrentPlayerInfo();
			}
		});
		currentPlayerlbl.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				presenter.dismissCurrentPlayerInfo();
			}
		});

		goldLbl = new Label();
		currentPlayerNameAndGold.getChildren().addAll(currentPlayerlbl, goldLbl);

		// Rack
		/*EventHandler<ThingEvent> handler = new EventHandler<ThingEvent>() {

			@Override
			public void handle(ThingEvent t) {
				presenter.handleRackClick(t.getThing());
			}
		};*/

		rack = new Rack();
		currentPlayerInfoBox.getChildren().addAll(currentPlayerNameAndGold, rack);

		// For now Should be in GameView
		viewCupBtn = new Button("View Cup");
		viewCupBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				presenter.handleCupClick();
			}
		});

		AnchorPane.setLeftAnchor(currentPlayerInfoBox, 10.0);
		AnchorPane.setTopAnchor(currentPlayerInfoBox, 5.0);
		AnchorPane.setRightAnchor(viewCupBtn, 10.0);
		AnchorPane.setTopAnchor(viewCupBtn, 8.0);

		getChildren().addAll(viewCupBtn, currentPlayerInfoBox);
	}

	public void setPresenter(final PlayerInfoPresenter presenter) {
		if (presenter == null) {
			throw new NullPointerException("Presenter cannot be null");
		}

		if (this.presenter != null) {
			throw new IllegalStateException("The presenter was already set.");
		}

		this.presenter = presenter;
	}

	public void setPlayer(Player current) {
		if (current != null) {
			currentPlayerlbl.setPlayer(current);
			goldLbl.setText("Gold: " + String.valueOf(current.getGold()));
			
			rack.setThings(current.getBlock().getListOfThings());
		}
	}
	
	public void updateGold(Player current)	{
		if(current != null)	{
			goldLbl.setText("Gold: " + String.valueOf(current.getGold()));
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
}
