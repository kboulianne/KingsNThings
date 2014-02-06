package com.view;

import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.main.KNTAppFactory;
import com.model.Creature;
import com.model.Player;
import com.presenter.ArmyDetailPresenter;
import com.view.customcontrols.ArmyOrMisc;

public class ArmyDetailsView extends VBox{

	protected ArmyDetailPresenter presenter;
	private Label lbl;
	private Label sublbl;
	private ArmyOrMisc army;
	
	public ArmyDetailsView(){
		buildView();
	}
	
	public void setPresenter(ArmyDetailPresenter presenter){
		if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
	}
	
	protected void buildView(){
		setAlignment(Pos.CENTER);
		getStyleClass().add("largeSpacing");
		lbl = new Label();
		lbl.getStyleClass().add("title");
		sublbl = new Label();
		EventHandler<ThingEvent> handler = new EventHandler<ThingEvent>() {
			@Override
			public void handle(ThingEvent t) {
				KNTAppFactory.getHexDetailsPresenter().handleThingClick(t.getThing());
			}

		};
		
		army = new ArmyOrMisc(handler);
		
		getChildren().addAll(lbl, sublbl, army);
	}

	public void setArmy(Player armyOwner, List<Creature> armyList) {
		lbl.setText(armyOwner.getName()+"'s Army");
		sublbl.setText("Army Size: "+armyList.size());
		army.setArmy(armyOwner, armyList);
	}
}
