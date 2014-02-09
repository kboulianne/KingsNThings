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

	/** The General Thing View that this Presenter manages. */
	private ThingDetailsView view;
	/** The Creature Thing View that displays information specific to creatures. */
	private CreatureDetailsView cView;
	/** The last creature selected from the rack. */
	private Creature lastSelectedCreature;
	
	public ThingDetailsPresenter(ThingDetailsView view, CreatureDetailsView cView) {
		this.view = view;
		this.view.setPresenter(this);
		this.cView = cView;
		this.cView.setPresenter(this);
	}

	/**
	 * Gets the appropriate view for the specified Thing instance.
	 * @param t The thing for which the view should be returned.
	 * @return The ThingDetailsView to to be used for the specified Thing.
	 */
	public ThingDetailsView getViewFor(Thing t) {
		if (t instanceof Creature) {
			return cView;
		} else {
			return view;
		}
	}

	/** 
	 * Shows the specified Thing in the Details View of the SidePane.
	 * @param t  The Thing instance to display.
	 */
	public void showThing(Thing t) {
		// Need to swap views according to Thing Type
		getViewFor(t).setThing(t);
	}
	
	/**
	 * UI Model. Gets the last creature that was selected from the rack.
	 * @return The selected creature.
	 */
	public Creature getLastSelectedCreature() {	
		return lastSelectedCreature;	
	}
	
	/**
	 * UI Model. Sets the last creature that was selected from the rack.
	 * @param c The creature to set.
	 */
	public void setLastSelectedCreature(Creature c) {	
		this.lastSelectedCreature = c;	
	}
}
