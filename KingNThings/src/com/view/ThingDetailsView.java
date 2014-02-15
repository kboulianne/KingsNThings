/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.model.Creature;
import com.model.Fort;
import com.model.IncomeCounter;
import com.model.Thing;
import com.presenter.ThingDetailsPresenter;
import com.view.customcontrols.ThingView;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * When a thing is clicked, display this in SidePaneView using SidePaneView#showThingDetailsView
 *
 * @author kurtis
 */


public class ThingDetailsView extends VBox {

	protected ThingDetailsPresenter presenter;
	private Creature lastSelectedCreature;

    //TODO
    //needs to be set or shouldnt be here
   // private BoardPresenter boardPresenter;

    private Label thingNameLbl;
    private Label typeLbl;
    private Label ownerLbl;
    
    private Label combatLbl;
    private Label specialAbilitiesLbl;
    private Label testLbl;

   // private Thing thing;
    
    public ThingDetailsView() {
     //   thing = t;
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

		/*stack = new StackPane();

		int size = 260;

		borderRect = new Rectangle();
		borderRect.setX(0);
		borderRect.setY(0);
		borderRect.setWidth(size);
		borderRect.setHeight(size);
		borderRect.setArcWidth(20);
		borderRect.setArcHeight(20);
		borderRect.setFill(Color.WHITE);

		coloredRect = new Rectangle();
		coloredRect.setX(0);
		coloredRect.setY(0);
		coloredRect.setWidth(size - 1);
		coloredRect.setHeight(size - 1);
		coloredRect.setArcWidth(20);
		coloredRect.setArcHeight(20);

		img = new ImageView();
		img.setFitWidth(size - 7);
		img.setFitHeight(size - 7);
		img.setPreserveRatio(true);
		img.setSmooth(true);
		img.setCache(true);*/

		thingNameLbl = new Label();
		thingNameLbl.getStyleClass().add("title");
		typeLbl = new Label();
		ownerLbl = new Label();
		
		combatLbl = new Label();
		specialAbilitiesLbl = new Label();
		testLbl = new Label();

		//stack.getChildren().addAll(borderRect, coloredRect, img);*/
		
		//thingHolder.getChildren().add(tv);

		
	}

	public void setThing(final Thing thing) {
		if (thing != null) {

			//img.setImage(thing.getImage());
			//coloredRect.setFill(thing.getColor());

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
			
			tv.setImg(new ImageView(thing.getImage()));
			//tv.getImg().set;
			//Util.log("qass");

			thingNameLbl.setText(thing.getName().toUpperCase());
			typeLbl.setText("Type: " + type);
			ownerLbl.setText("Owner: " + thing.getOwner());
			if (thing instanceof Creature) {
				Creature c = (Creature)thing;
			    testLbl.setText("Avail Moves: "+ c.getNumberOfMovesAvailable());
			    combatLbl.setText("Combat Value: " + c.getCombatVal());
			    specialAbilitiesLbl.setText("Abilities: " + c.getAbilitiesString());
				setLastSelectedCreature(c);
				getChildren().clear();
				getChildren().addAll(thingNameLbl, tv, typeLbl, ownerLbl, combatLbl, specialAbilitiesLbl, testLbl);
			}else{
				getChildren().clear();
				getChildren().addAll(thingNameLbl, tv, typeLbl, ownerLbl);
			}
			
		}
	}
	
	public Creature getLastSelectedCreature() {
		return lastSelectedCreature;
	}

	public void setLastSelectedCreature(Creature lastSelectedCreature) {
		this.lastSelectedCreature = lastSelectedCreature;
	}
}
