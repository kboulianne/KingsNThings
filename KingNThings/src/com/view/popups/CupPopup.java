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
    
	public CupPopup(Cup cup){
		buildPopup(cup);
    }
    
    private void buildPopup(Cup cup) {
		
    	int wrapLength = 1160;
    	int thingSize =45;
    	Label thingLbl = new Label("Things: "+cup.getListOfThings().size()+" items");
    	
    	FlowPane thingFP = new FlowPane();
    	thingFP.setVgap(3);
    	thingFP.setHgap(3);
    	thingFP.setPrefWrapLength(wrapLength);
		
		for (Thing t : cup.getListOfThings()) {
			ThingView tv =new ThingView(thingSize, t);
			thingFP.getChildren().add(tv);
		}
		
		int total = 0;
		for (Thing t : cup.getListOfThings()) {
			if(t instanceof IncomeCounter)	{
				total++;
			}
		}
		
		Label incomeLbl = new Label("Income Counters: "+ total +" items");
		FlowPane icFP = new FlowPane();
    	icFP.setVgap(3);
    	icFP.setHgap(3);
    	icFP.setPrefWrapLength(wrapLength);
		for (Thing t : cup.getListOfThings()) {
			if(t instanceof IncomeCounter)	{
				ThingView tv = new ThingView(thingSize, t);
				icFP.getChildren().add(tv);
			}
		}
		
		Label scLbl = new Label("Special Characters: "+cup.getListOfSpecialCharacters().size()+" items");
		FlowPane scFP = new FlowPane();
    	scFP.setVgap(3);
    	scFP.setHgap(3);
    	scFP.setPrefWrapLength(wrapLength);
		for (SpecialCharacter sc : cup.getListOfSpecialCharacters()) {
			ThingView tv =new ThingView(thingSize, sc);
			scFP.getChildren().add(tv);
		}
		
		getStyleClass().add("block");
		getChildren().addAll(thingLbl, thingFP, incomeLbl, icFP, scLbl, scFP);	
    }
}
