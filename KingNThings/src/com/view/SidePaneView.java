/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.view.customcontrols.PlayerLabel;
import com.main.KNTAppFactory;
import com.model.Hex;
import com.model.Player;
import com.presenter.SidePanePresenter;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
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
	
	public void showArbituaryView(String title, Image img){
		
		VBox display = new VBox();
		ImageView iv = new ImageView(img);

		iv.setFitHeight(200.0);
		iv.setPreserveRatio(true);

		Label lbl = new Label(title);
		display.getChildren().addAll(iv, lbl);
		
		display.getStyleClass().add("block");
		display.setAlignment(Pos.CENTER);
		content.getChildren().clear();
		content.getChildren().add(display);
	}

	public void showHexDetailsView(HexDetailsView view) {
		// Set the pane to the HexDetailsView
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
