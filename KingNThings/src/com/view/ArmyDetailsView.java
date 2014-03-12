package com.view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.model.Creature;
import com.model.Hex;
import com.model.Player;
import com.presenter.ArmyDetailsPresenter;
import com.view.customcontrols.ArmyOrMisc;

public class ArmyDetailsView extends VBox{

	private ArmyDetailsPresenter presenter;
	private Label lbl;
	private Label sublbl;
	private ArmyOrMisc army;
	
	private Hex fromHex;
	private List<Creature> lastSelectedArmy;
	
	//EventHandler<ThingEvent> handler;
	
	public ArmyDetailsView(){
		buildView();
	}
	
	public void setPresenter(ArmyDetailsPresenter presenter){
		if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
	}
	
	private void buildView(){
		lastSelectedArmy = new ArrayList<Creature>();
		setAlignment(Pos.CENTER);
		getStyleClass().add("largeSpacing");
		lbl = new Label();
		lbl.getStyleClass().add("title");
		sublbl = new Label();
		/*handler = new EventHandler<ThingEvent>() {
			@Override
			public void handle(ThingEvent t) {
				KNTAppFactory.getHexDetailsPresenter().handleThingClick(t.getThing());
			}

		};*/
		
		army = new ArmyOrMisc();
		
		getChildren().addAll(lbl, sublbl, army);
	}

	public void setArmy(Hex hex, Player armyOwner, List<Creature> armyList) {
		lastSelectedArmy = armyList;
		setFromHex(hex);
		lbl.setText(armyOwner.getName()+"'s Army");
		sublbl.setText("Army Size: "+armyList.size());
		army.setArmy(hex, armyOwner, armyList);
	}
	
	public void setMovementHandler(){
		army.setMoving(true);
	}
	public void setDefaultHandler(){
		army.setMoving(false);
	}
	public ArmyOrMisc getArmy() {
		return army;
	}

	public List<Creature> getLastSelectedArmy() {
		return lastSelectedArmy;
	}

	public Hex getFromHex() {
		return fromHex;
	}

	public void setFromHex(Hex fromHex) {
		this.fromHex = fromHex;
	}
}
