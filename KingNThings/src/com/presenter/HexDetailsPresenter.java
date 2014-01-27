/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.presenter;

import com.model.Hex;
import com.model.Thing;
import com.view.HexDetailsView;

/**
 *
 * @author kurtis
 */
public class HexDetailsPresenter {

	private HexDetailsView view;
	private SidePanePresenter sidePanePresenter;

	public HexDetailsPresenter(HexDetailsView view) {
		this.view = view;
		this.view.setPresenter(this);
	}

	public HexDetailsView getView() {
		return view;
	}

	public void setDependencies(SidePanePresenter sidePanePresenter) {
		this.sidePanePresenter = sidePanePresenter;
	}
	
	public void showHex(Hex h) {
		view.setHex(h);
	}

	public void handleThingClick(Thing t) {
		// Show ThingDetailView in SidePane.
		sidePanePresenter.showThingDetailsFor(t);
	}
}
