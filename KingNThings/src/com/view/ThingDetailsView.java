/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import java.util.ArrayList;

import com.game.services.GameService;
import com.model.Creature;
import com.model.Fort;
import com.model.IncomeCounter;
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

	protected ThingDetailsPresenter presenter;
	
	//TODO Move
	private Creature lastSelectedCreature5;
	private ArrayList<Creature> lastSelectedCreatures = new ArrayList<Creature>();

    //TODO
    //needs to be set or shouldnt be here
   // private BoardPresenter boardPresenter;

    private Label thingNameLbl;
    private Label typeLbl;
    private Label ownerLbl;
    
    private Label combatLbl;
    private Label specialAbilitiesLbl;
    private Label testLbl;
    
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
		getStyleClass().add("block");

		thingNameLbl = new Label();
		thingNameLbl.getStyleClass().add("title");
		typeLbl = new Label();
		ownerLbl = new Label();
		
		combatLbl = new Label();
		specialAbilitiesLbl = new Label();
		testLbl = new Label();
	}

	public void setThing(final Thing thing) {
		if (thing != null) {

			String type = "error";
			if (thing instanceof Creature) {
				type = ((Creature) thing).getDomain();
			}
			if (thing instanceof IncomeCounter) {
				type = "Income Counter";
			}
			if (thing instanceof Fort){
				type = "Fort";
			}
			ThingView tv =new ThingView(260, thing);

			thingNameLbl.setText(thing.getName().toUpperCase());
			typeLbl.setText("Type: " + type);
			ownerLbl.setText("Owner: " + thing.getOwner());
			getChildren().clear();
			if (thing instanceof Creature) {
				Creature c = (Creature)thing;
			    testLbl.setText("Avail Moves: "+ c.getNumberOfMovesAvailable());
			    combatLbl.setText("Combat Value: " + c.getCombatVal());
			    specialAbilitiesLbl.setText("Abilities: " + c.getAbilitiesString());
				GameService.getInstance().getGame().getLastSelectedCreatures().add(c);
				getChildren().addAll(thingNameLbl, tv, typeLbl, ownerLbl, combatLbl, specialAbilitiesLbl, testLbl);
			}else{
				getChildren().addAll(thingNameLbl, tv, typeLbl, ownerLbl);
			}	
		}
	}
	/*
	public Creature getLastSelectedCreature() {
		return lastSelectedCreature;
	}

	public void setLastSelectedCreature(Creature lastSelectedCreature) {
		this.lastSelectedCreature = lastSelectedCreature;
	}*/
}
