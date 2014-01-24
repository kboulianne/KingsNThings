/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.view;

import com.model.Creature;
import com.model.Thing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author kurtis
 */
// ThingDetailsView is a VBox
public class CreatureDetailsView extends ThingDetailsView {

    private Label combatLbl;
    private Label specialAbilitiesLbl;
    private Button moveButton;
    
    @Override
    protected void buildView() {
	// Build Controls in super
	super.buildView(); 
	
	combatLbl = new Label();
	specialAbilitiesLbl = new Label();
	
	moveButton = new Button("Move");
	
	getChildren().addAll(combatLbl, specialAbilitiesLbl, moveButton);
    }

    @Override
    public void setThing(Thing thing) {
	super.setThing(thing);
	
	if (thing != null && thing instanceof Creature) {
	    Creature c = (Creature)thing;
	    String abilities = "";
	    
	    if (c.getFly()) {
		abilities += " Flying";
	    }
	    if (c.getRanged()) {
		abilities += " Ranged";
	    }
	    if (c.getCharge()) {
		abilities += " Charging";
	    }
	    if(c.getMagic()){
		abilities += " Magic";
	    }
	    
	    if(abilities.isEmpty()){
		abilities = " None";
	    }
	    
	    combatLbl.setText("Combat Value: " + c.getCombatVal());
	    specialAbilitiesLbl.setText("Abilities: " + abilities);
	}
    }
    
    
}
