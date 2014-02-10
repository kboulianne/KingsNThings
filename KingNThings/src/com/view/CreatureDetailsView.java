/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.model.Creature;
import com.model.Thing;
import javafx.scene.control.Label;

/**
 *
 * @author kurtis
 */
// ThingDetailsView is a VBox
public class CreatureDetailsView extends ThingDetailsView {

    private Label combatLbl;
    private Label specialAbilitiesLbl;
    private Label testLbl;
    
	@Override
	protected void buildView() {
		// Build Controls in super
		super.buildView();

		combatLbl = new Label();
		specialAbilitiesLbl = new Label();

		testLbl = new Label();
		
		getChildren().addAll(combatLbl, specialAbilitiesLbl, testLbl);
	}

	@Override
	public void setThing(Thing thing) {
		super.setThing(thing);
		
		if (thing != null && thing instanceof Creature) {
		    Creature c = (Creature)thing;
		    testLbl.setText("Avail Moves: "+ c.getNumberOfMovesAvailable());
		    combatLbl.setText("Combat Value: " + c.getCombatVal());
		    specialAbilitiesLbl.setText("Abilities: " + c.getAbilitiesString());
			presenter.setLastSelectedCreature(c);
		}
		
	}
}
