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
    private Label testLbl;
    private Button moveButton;
    

	@Override
	protected void buildView() {
		// Build Controls in super
		super.buildView();

		combatLbl = new Label();
		specialAbilitiesLbl = new Label();

		testLbl = new Label();
		
		moveButton = new Button("Move");
		
		getChildren().addAll(combatLbl, specialAbilitiesLbl, testLbl, moveButton);
    
	}

	@Override
	public void setThing(Thing thing) {

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
		    testLbl.setText("Avail Moves: "+ c.getNumberOfMovesAvailable());
		    combatLbl.setText("Combat Value: " + c.getCombatVal());
		    specialAbilitiesLbl.setText("Abilities: " + abilities);
		    
		}
		// TODO Don't know how to do this, presenter should be boardPresenter for moveButton
		// but boardPresenter is null

		//boardPresenter.handleMoveButtonClick(moveButton);
		super.setThing(thing);

	}

}
