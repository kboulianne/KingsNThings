/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.presenter;


import com.main.KNTAppFactory;
import com.model.Thing;
import com.view.ThingDetailsView;

/**
 *
 * @author kurtis
 */
public class ThingDetailsPresenter {

	/** The General Thing View that this Presenter manages. */
	private ThingDetailsView view;
	/** The Creature Thing View that displays information specific to creatures. */
	//private CreatureDetailsView cView;
	
	public ThingDetailsPresenter(ThingDetailsView view){
		this.view = view;
	}

	/**
	 * Gets the appropriate view for the specified Thing instance.
	 * @param t The thing for which the view should be returned.
	 * @return The ThingDetailsView to to be used for the specified Thing.
	 */
	ThingDetailsView getViewFor(Thing t) {
		return view;
	}
	
	public ThingDetailsView getThingView()	{	return view;	}

	/** 
	 * Shows the specified Thing in the Details View of the SidePane.
	 * @param t  The Thing instance to display.
	 */
	void showThing(Thing t) {
		// Need to swap views according to Thing Type
		getViewFor(t).setThing(t, KNTAppFactory.getGamePresenter().getLocalInstance().getLastSelectedThingsOfCurrentPlayerBlock());
	}
}
