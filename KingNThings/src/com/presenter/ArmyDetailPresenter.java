package com.presenter;

import java.util.List;

import com.main.KNTAppFactory;
import com.model.Creature;
import com.model.Player;
import com.view.ArmyDetailsView;

public class ArmyDetailPresenter {
	private ArmyDetailsView view;
	
	public ArmyDetailPresenter(ArmyDetailsView view) {
		this.view = view;
		this.view.setPresenter(this);
	}
	
	public void showArmy(Player armyOwner, List<Creature> army){
		view.setArmy(armyOwner, army);
		KNTAppFactory.getSidePanePresenter().getView().showArmyDetailsView(view);
	}
}
