/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.view.popups;

import com.model.Thing;
import com.view.customcontrols.ThingView;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author kurtis
 */
public class CupPopup extends FlowPane {
    
  //  private EventHandler<ThingEvent> thingHandler;
	    // Might not be the best way but works.
	public CupPopup(List<Thing> things){//, EventHandler<ThingEvent> event) {
		//this.thingHandler = event;
		buildPopup(things);
    }
    
    private void buildPopup(List<Thing> things) {
		setVgap(3);
		setHgap(3);
		setPadding(new Insets(10, 0, 0, 0));
		//TODO Hardcoded
		setPrefWrapLength(1160);
		
		for (Thing t : things) {
			ThingView tv =new ThingView(55, t);
			getChildren().add(tv);
			tv.setCupHandler();
		}
    }
}
