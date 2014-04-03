/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import java.util.List;

import com.model.Creature;
import com.model.Fort;
import com.model.IncomeCounter;
import com.model.SpecialCharacter;
import com.model.Thing;
import com.presenter.ThingDetailsPresenter;
import com.view.customcontrols.ThingView;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * When a thing is clicked, display this in SidePaneView using SidePaneView#showThingDetailsView
 *
 * @author kurtis
 */


public class ThingDetailsView extends VBox {

    private Label thingNameLbl;
    private Label typeLbl;
    private Label ownerLbl;
    
    private Label combatLbl;
    private Label specialAbilitiesLbl;
    private Label testLbl;
    
    public ThingDetailsView() {
    		buildView();
    }
    
    private void buildView() {

		setAlignment(Pos.CENTER);
		getStyleClass().add("block");

		thingNameLbl = new Label();
		thingNameLbl.getStyleClass().add("title");
		typeLbl = new Label();
		ownerLbl = new Label();
		
		combatLbl = new Label();
		specialAbilitiesLbl = new Label();
		testLbl = new Label();
	}

	public void setThing(final Thing thing, List<Thing> lastSelected) {
		if (thing != null) {
			if(thing.isFacedDown()){
				thingNameLbl.setText("UNKNOWN");
				ThingView tv =new ThingView(260, thing);
				getChildren().clear();
				getChildren().addAll(thingNameLbl, tv);
				return;
			}
			
			String type = "error";
			if (thing instanceof SpecialCharacter){
				type = "Special Character";
			}else if (thing instanceof Creature) {
				type = ((Creature) thing).getDomain();
			}
			else if (thing instanceof IncomeCounter) {
				type = "Income Counter";
			}
			else if (thing instanceof Fort){
				type = "Fort";
			}

			ThingView tv = new ThingView(260, thing);

			thingNameLbl.setText(thing.getName().toUpperCase());
			typeLbl.setText("Type: " + type);
			ownerLbl.setText("Owner: " + thing.getOwner());
			getChildren().clear();
			if (thing instanceof Creature || thing instanceof SpecialCharacter) {
				Creature c = (Creature)thing;
			    testLbl.setText("Available Moves: "+ c.getNumberOfMovesAvailable());
			    combatLbl.setText("Combat Value: " + c.getCombatVal());
			    specialAbilitiesLbl.setText("Abilities: " + c.getAbilitiesString());
			    lastSelected.add(c);
				getChildren().addAll(thingNameLbl, tv, typeLbl, ownerLbl, combatLbl, specialAbilitiesLbl, testLbl);
			}else{
				getChildren().addAll(thingNameLbl, tv, typeLbl, ownerLbl);
			}	
		}
	}
}
