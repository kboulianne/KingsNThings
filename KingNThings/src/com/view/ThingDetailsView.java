/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.view;

import com.model.Creature;
import com.model.Thing;
import com.presenter.ThingDetailsPresenter;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/** When a thing is clicked, display this in SidePaneView using
 * SidePaneView#showThingDetailsView
 *
 * @author kurtis
 */
public class ThingDetailsView extends VBox {
    
    protected ThingDetailsPresenter presenter;
    
    private ImageView thingIv;
    private Label thingNameLbl;
    private Label typeLbl;
    private Label ownerLbl;
    
    
    public ThingDetailsView() {
        buildView();
    }
    
    public void setPresenter(final ThingDetailsPresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    protected void buildView() {
	setAlignment(Pos.CENTER);
	
	thingIv = new ImageView();
	// Hardcoded value
	thingIv.setFitWidth(260);
	thingIv.setPreserveRatio(true);
	thingIv.setSmooth(true);
	thingIv.setCache(true);
	
	thingNameLbl = new Label();
	typeLbl = new Label();
	ownerLbl = new Label();
	
	getChildren().addAll(thingIv, thingNameLbl, typeLbl, ownerLbl);
//        getChildren().add(new Label("This is ThingDetailsView"));
    }
    
    public void setThing(final Thing thing) {
        if (thing != null) {
	    thingIv.setImage(thing.getImage());
	    thingNameLbl.setText(thing.getName());
	    
	    String type = "error";
	    if(thing instanceof Creature) {
		type = ((Creature) thing).getDomain();
	    }
	    
	    typeLbl.setText("Type: " + type);
	    ownerLbl.setText("Owner: " + thing.getOwner());
	}
    }
}
