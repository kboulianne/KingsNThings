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
import javafx.scene.layout.VBox;

/**
 *
 * @author kurtis
 */
public class HexDetailsView extends VBox {
    
    // FIXME: Duplicate
    static final double HEX_WIDTH = 100.0; 
    static final double HEX_HEIGHT = HEX_WIDTH *0.8;
    
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
    //addmisc
    
    public HexDetailsView() {
	buildView();
    }
    
    protected void buildView() {        
	// TODO Put in create Methods
	// Define content. (FROM paintHexInDetails
	hexImage = new ImageView();
	// FIXME Hardcoded
	hexImage.setFitWidth(300);
	hexImage.setPreserveRatio(true);
	hexImage.setSmooth(true);
	hexImage.setCache(true);
	
	VBox contentBox = new VBox();
        contentBox.getStyleClass().add("block");
	contentBox.setAlignment(Pos.CENTER);
	
	// Name Label
	nameLbl = new Label();
	// Test
	testLbl = new Label();
	// owner
	ownerLbl = new Label();
	
	contentBox.getChildren().addAll(hexImage, nameLbl, ownerLbl, testLbl);
	
        // Create the mouse handler for the things and hook it up to the presenter.
        EventHandler<ThingEvent> handler = new EventHandler<ThingEvent>() {
            @Override
            public void handle(ThingEvent t) {
                presenter.handleThingClick(t.getThing());
            }
          
        };
        
	// ArmyOrMisc components
	opp1Army = new ArmyOrMisc(handler);
	opp2Army = new ArmyOrMisc(handler);
	opp3Army = new ArmyOrMisc(handler);
	currentPlayerArmy = new ArmyOrMisc(handler);
	
        getChildren().addAll(contentBox, opp1Army, opp2Army, opp3Army, currentPlayerArmy);
    }
    
    // TODO Put in ArmyOrMiscView?
//    private HBox createArmyOrMisc() {
//	// TODO should we put armies in player?
//	
////	return armyBox;
//    }
    
    public void setPresenter(final HexDetailsPresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    public void setHex(Hex hex) {
	// update ui here.
	
	if (hex != null) {
	    // Set the Hex Name
	    nameLbl.setText("Type: " + hex.getType().name());
	    // Set the new image.
	    hexImage.setImage(hex.getImage());
	    // Set the owner if it exists
	    if (hex.getOwner() != null) {
		ownerLbl.setText("Owner: " + hex.getOwner().getName());
	    }
	    else {
		ownerLbl.setText("Owner: Not Owned");
	    }
	    
	    // Test label
	    String test = "";
	    for (int i: hex.getJoiningHexes()){
		    test+=i+", "; 
	    }
	    testLbl.setText("removeLater: id=" + hex.getId() + " joins with:"+test);
	    // TODO Hardcoded stuff!
	    // Update the armies
            
            
//	    Map<Player, List<Thing>> armies = hex.getArmies();
	    // TESTING: SERVICE SHOULD NOT BE HERE
	    Game game = GameService.getInstance().getGame();
            opp1Army.setArmy(game.getOpponent1(), hex.getOpponent1Army());
//	    opp1Army.setArmy(game.getOpponent1(), armies.get(game.getOpponent1()));
	    opp2Army.setArmy(game.getOpponent2(), hex.getOpponent2Army());
	    opp3Army.setArmy(game.getOpponent3(), hex.getOpponent3Army());
	    currentPlayerArmy.setArmy(game.getCurrentPlayer(), hex.getCurrentPlayerArmy());
	    
	    //TODO Misc things
	    
	    
	}
    }
}
