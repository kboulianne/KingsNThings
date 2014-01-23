/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.model.Hex;
import view.com.DetailsView;

/**
 *
 * @author kurtis
 */
public class DetailsPresenter {
    private DetailsView view;
    
    public DetailsPresenter(DetailsView view /* Needed presenters go here. */) {
	this.view = view;
	this.view.setPresenter(this);
    }
    
    public DetailsView getView() {
	return view;
    }
    
    
    public void showHex(Hex h) {
	view.setHex(h);
    }
}
