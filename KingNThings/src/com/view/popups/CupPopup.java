/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.view.popups;

import com.model.Cup;
import com.model.IncomeCounter;
import com.model.SpecialCharacter;
import com.model.Thing;
import com.view.customcontrols.ThingView;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author kurtis
 */
public class CupPopup extends VBox {
    
  //  private EventHandler<ThingEvent> thingHandler;
	    // Might not be the best way but works.
	public CupPopup(Cup cup){//, EventHandler<ThingEvent> event) {
		//this.thingHandler = event;
		buildPopup(cup);
    }
    
    private void buildPopup(Cup cup) {
		
    	Label thingLbl = new Label("Things: "+cup.getListOfThings().size()+" items");
    	//thingLbl.setPadding(new Insets(10, 0, 0, 0));
    	
    	FlowPane thingFP = new FlowPane();
    	thingFP.setVgap(3);
    	thingFP.setHgap(3);
    	thingFP.setPrefWrapLength(1160);
		
		for (Thing t : cup.getListOfThings()) {
			ThingView tv =new ThingView(55, t);
			thingFP.getChildren().add(tv);
			tv.setCupHandler();
		}
		
		Label incomeLbl = new Label("Income Counters: "+cup.getListOfIncomeCounters().size()+" items");
		FlowPane icFP = new FlowPane();
    	icFP.setVgap(3);
    	icFP.setHgap(3);
    	icFP.setPrefWrapLength(1160);
		for (IncomeCounter ic : cup.getListOfIncomeCounters()) {
			ThingView tv =new ThingView(55, ic);
			icFP.getChildren().add(tv);
			tv.setCupHandler();
		}
		
		Label scLbl = new Label("Special Characters: "+cup.getListOfSpecialCharacters().size()+" items");
		FlowPane scFP = new FlowPane();
    	scFP.setVgap(3);
    	scFP.setHgap(3);
    	scFP.setPrefWrapLength(1160);
		for (SpecialCharacter sc : cup.getListOfSpecialCharacters()) {
			ThingView tv =new ThingView(55, sc);
			scFP.getChildren().add(tv);
			tv.setCupHandler();
		}
		
		getStyleClass().add("block");
		getChildren().addAll(thingLbl, thingFP, incomeLbl, icFP, scLbl, scFP);
		
    }
}
