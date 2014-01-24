/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.com;

import com.model.Player;
import com.presenter.PlayerInfoPresenter;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
	
	currentPlayerlbl = new PlayerLabel();
	goldLbl = new Label();
	currentPlayerNameAndGold.getChildren().addAll(currentPlayerlbl, goldLbl);
	
	currentPlayerInfoBox.getChildren().add(currentPlayerNameAndGold);
	
	getChildren().addAll(currentPlayerInfoBox);
    }
    
    public void setPresenter(final PlayerInfoPresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    public void setPlayer(Player current) {
	if (current != null) {
	    //TODO refactor to take Name and Color
	    currentPlayerlbl.setPlayer(current);
	    goldLbl.setText("Gold: " + String.valueOf(current.getGold()));
	}
    }
}
