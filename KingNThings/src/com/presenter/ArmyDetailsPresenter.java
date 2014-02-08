package com.presenter;

import java.util.List;

import com.main.KNTAppFactory;
import com.model.Creature;
import com.model.Hex;
import com.model.Player;
import com.view.ArmyDetailsView;

public class ArmyDetailsPresenter {
	private ArmyDetailsView view;
	
	public ArmyDetailsPresenter(ArmyDetailsView view) {
		this.view = view;
		this.view.setPresenter(this);
	}
	
	public void showArmy(Hex hex, Player armyOwner, List<Creature> army){
		view.setArmy(hex, armyOwner, army);
		KNTAppFactory.getSidePanePresenter().getView().showArmyDetailsView(view);
	}

	public ArmyDetailsView getView() {
		return view;
	}
}
