/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

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
}
