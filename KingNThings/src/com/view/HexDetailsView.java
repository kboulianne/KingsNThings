/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.view.customcontrols.ArmyOrMisc;
import com.game.services.GameService;
import com.model.Hex;
import com.model.Player;
import com.model.game.Game;
import com.presenter.HexDetailsPresenter;
import java.util.List;
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
	private Label ownerLbl;
	private ArmyOrMisc opp1Army;
	private ArmyOrMisc opp2Army;
	private ArmyOrMisc opp3Army;
	private ArmyOrMisc currentPlayerArmy;
	
    private ArmyOrMisc misc;

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
		// owner
		ownerLbl = new Label();

		contentBox.getChildren().addAll(nameLbl, ownerLbl);

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
		misc = new ArmyOrMisc(handler);

		content.getChildren().addAll(contentBox, currentPlayerArmy, opp1Army, opp2Army, opp3Army, misc);
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
		if (hex != null) {
			setArmies(hex.getId());
		
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
	
	protected void setArmies(int hexId){
		Game game = GameService.getInstance().getGame();
		Hex hex = game.getBoard().getHexes().get(hexId);
		// Doing this is a glitch in our case since the game instance is shared amongst 4 players. This means that
		// At any point, one of the getOpponents will be equal to current player, therefore, displaying the same
		// armies for opponents and current player.
		
		// TODO will crash for 2-3 players
		// Use player order to determine opponents.
		List<Player> opponents = game.getOpponentsForCurrent();
		
		opp1Army.setArmy(hex, opponents.get(0), hex.getArmies(opponents.get(0)));
		opp2Army.setArmy(hex, opponents.get(1), hex.getArmies(opponents.get(1)));
		opp3Army.setArmy(hex, opponents.get(2), hex.getArmies(opponents.get(2)));
		currentPlayerArmy.setArmy(hex, game.getCurrentPlayer(), hex.getArmies(game.getCurrentPlayer()));
		
		
		misc.setMisc(hex, hex.getMiscItems());
		
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
