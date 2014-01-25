/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.model.Creature;
import com.model.Thing;
import com.view.CreatureDetailsView;
import com.view.ThingDetailsView;

/**
 *
 * @author kurtis
 */
public class ThingDetailsPresenter {
    private ThingDetailsView view;
    private CreatureDetailsView cView;
    
    
    public ThingDetailsPresenter(ThingDetailsView view, CreatureDetailsView cView) {
        this.view = view;
        this.view.setPresenter(this);
        this.cView = cView;
        this.cView.setPresenter(this);
    }
    
    public ThingDetailsView getViewFor(Thing t) {
	if (t instanceof Creature) 
	    return cView;
	else
	    return view;
    }
    
    public void showThing(Thing t) {
	// Need to swap views according to Thing Type
	getViewFor(t).setThing(t);
    }

	public void handleMoveButtonClick(Button moveButton) {
		// TODO Auto-generated method stub
		
		
		//final int selectedIndex = this.gSceen.getLastHexSelected();
		cView.getMoveButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Util.log("SeletectIndex");
				//highlighAvailableMoves();
			}
		});
		//
		
		//Util.log("SeletectIndex"+ selectedIndex);
		    	//moveButton
		    
	}
}
