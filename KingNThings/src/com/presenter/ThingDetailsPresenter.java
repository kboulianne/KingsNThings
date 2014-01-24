/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.model.Thing;
import view.com.ThingDetailsView;

/**
 *
 * @author kurtis
 */
public class ThingDetailsPresenter {
    private ThingDetailsView view;
    
    public ThingDetailsPresenter(ThingDetailsView view) {
        this.view = view;
        this.view.setPresenter(this);
    }
    
    public ThingDetailsView getView() {
        return view;
    }
    
    public void showThing(Thing t) {
	view.setThing(t);
    }
}
