/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.view.customcontrols.ArmyOrMisc;
import com.game.services.GameService;
import com.model.Hex;
import com.model.game.Game;
import com.presenter.HexDetailsPresenter;
import javafx.event.EventHandler;
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

	// FIXME: Duplicate
	static final double HEX_WIDTH = 100.0;
	static final double HEX_HEIGHT = HEX_WIDTH * 0.8;

	// Presenter managing this view.
	private HexDetailsPresenter presenter;

	private ImageView hexImage;
	private Label nameLbl;
	private Label testLbl;
	private Label ownerLbl;
	private ArmyOrMisc opp1Army;
	private ArmyOrMisc opp2Army;
	private ArmyOrMisc opp3Army;
	private ArmyOrMisc currentPlayerArmy;
	
    //private ArmyOrMisc misc;

	public HexDetailsView() {
		buildView();
	}

	protected void buildView() {

		VBox content = new VBox();
		// TODO Put in create Methods
		// Define content. (FROM paintHexInDetails
		hexImage = new ImageView();
		// FIXME Hardcoded
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
		// Test
		testLbl = new Label();
		// owner
		ownerLbl = new Label();

		contentBox.getChildren().addAll(nameLbl, ownerLbl, testLbl);

		// Create the mouse handler for the things and hook it up to the presenter.
		EventHandler<ThingEvent> handler = new EventHandler<ThingEvent>() {
			@Override
			public void handle(ThingEvent t) {
				presenter.handleThingClick(t.getThing());
			}

		};

        // ArmyOrMisc components
		//misc = new ArmyOrMisc(handler);
		//TODO shouldn't add these if empty
		opp1Army = new ArmyOrMisc(handler);
		opp2Army = new ArmyOrMisc(handler);
		opp3Army = new ArmyOrMisc(handler);
		currentPlayerArmy = new ArmyOrMisc(handler);

		content.getChildren().addAll(contentBox, opp1Army, opp2Army, opp3Army, currentPlayerArmy);
		getChildren().addAll(hexImage, content);
		content.setAlignment(Pos.CENTER);
		content.getStyleClass().add("block");
	}

	public void setPresenter(final HexDetailsPresenter presenter) {
		if (presenter == null) {
			throw new NullPointerException("Presenter cannot be null");
		}

		if (this.presenter != null) {
			throw new IllegalStateException("The presenter was already set.");
		}

		this.presenter = presenter;
	}

	public void setHex(Hex hex) {
	// update ui here.
		setArmies(hex.getId());
		if (hex != null) {
			// Set the Hex Name
			nameLbl.setText(hex.getTypeAsString());
			// Set the new image.
			hexImage.setImage(hex.getImage());
			// Set the owner if it exists
			if (hex.getOwner() != null) {
				ownerLbl.setText("Owner: " + hex.getOwner().getName());
			} else {
				ownerLbl.setText("Owner: Not Owned");
			}

			// Test label
			String test = "";
			for (int i : hex.getJoiningHexes()) {
				test += i + ", ";
			}
			testLbl.setText("removeLater: id=" + hex.getId() + " joins with:" + test);
		}
	}
	
	protected void setArmies(int hexId){
		Game game = GameService.getInstance().getGame();
		Hex hex = game.getBoard().getHexes().get(hexId);
		opp1Army.setArmy(game.getOpponent1(), hex.getArmies(game.getOpponent1()));
		opp2Army.setArmy(game.getOpponent2(), hex.getArmies(game.getOpponent2()));
		opp3Army.setArmy(game.getOpponent3(), hex.getArmies(game.getOpponent3()));
		currentPlayerArmy.setArmy(game.getCurrentPlayer(), hex.getArmies(game.getCurrentPlayer()));
		
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
}
