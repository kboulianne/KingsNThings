/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.com;

import com.presenter.DetailsPresenter;
import com.model.Player;
import com.presenter.SidePanePresenter;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author kurtis
 */
public class SidePaneView extends VBox {
    
    // FIXME: Duplicate, put in global stuff? Put in GamePresenter?
    // View specific stuff
    static final double HEX_WIDTH = 100.0; 
    static final double HEX_HEIGHT = HEX_WIDTH *0.8;
    
    private SidePanePresenter presenter;
    private DetailsPresenter detailsPresenter;
    
    private PlayerLabel opp1Lbl;
    private PlayerLabel opp2Lbl;
    private PlayerLabel opp3Lbl;
    
    public SidePaneView() {
	buildView();
    }
    
    protected void buildView() {
	// Initial setup
	setId("sidePane");
	//FIXME: Hardcoded
	setMinWidth(1280 * 0.5 - 20);

	
	// TODO Sub-view?
//	VBox detailsBox = new VBox(); 
//	detailsBox.setAlignment(Pos.CENTER);
//	detailsBox.setId("detailsBox");
//	detailsBox.setMinHeight(HEX_HEIGHT*7);
	
	// Opponents
	HBox otherPlayerInfo = new HBox();
	otherPlayerInfo.setId("otherPlayerInfo");	
	otherPlayerInfo.setAlignment(Pos.TOP_CENTER);
	opp1Lbl = new PlayerLabel();
	opp2Lbl = new PlayerLabel();
	opp3Lbl = new PlayerLabel();
	otherPlayerInfo.getChildren().addAll(opp1Lbl, opp2Lbl, opp3Lbl);
	
	// Adds opponents and DetailsView
	getChildren().addAll(otherPlayerInfo, detailsPresenter.getView());

    }
    
    public void setPresenter(final SidePanePresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    // TODO pass list/set?
    public void setOpponents(Player o1, Player o2, Player o3) {
	opp1Lbl.setPlayer(o1);
	opp2Lbl.setPlayer(o2);
	opp3Lbl.setPlayer(o3);
    }
}
