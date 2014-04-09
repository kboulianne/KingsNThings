/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.customcontrols;

import com.main.KNTAppFactory;
import com.model.Creature;
import com.model.Hex;
import com.model.IncomeCounter;
import com.model.Player;
import com.model.Thing;

import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

/**
 * A Control. Using DetailsView as a presenter.
 *
 * @author kurtis
 */
public class ArmyOrMisc extends HBox {

	private Circle circle;
	private Label sizeLbl;
	private HBox thingHolder;
	private StackPane circleStackPane;
	private boolean moving;

	public ArmyOrMisc()	{
		buildComponent();
	}

	private void buildComponent() {
		getStyleClass().add("block");
		setAlignment(Pos.CENTER);
		moving = false;

		circleStackPane = new StackPane();
		circle = new Circle();
		circle.setRadius(22);
		sizeLbl = new Label();
		circleStackPane.getChildren().addAll(circle, sizeLbl);
		
		circleStackPane.setVisible(false);

		thingHolder = new HBox();
		thingHolder.getStyleClass().add("army");

		getChildren().addAll(circleStackPane, thingHolder);

	}

	private void handleArmyClick(Hex hex, Player armyOwner, List<Creature> army){
		
		if(moving){
			boolean allSelected = true;  
			for(Creature c: army){
				if(!c.isSelected())	allSelected = false;
			}
			
			for(Creature c: army)	{
				c.setSelected(!allSelected);
			}
		
			KNTAppFactory.getHexDetailsPresenter().getView().getCurrentPlayerArmy().setArmy(hex, armyOwner, army);
			
			KNTAppFactory.getBoardPresenter().handleMoveSetup();
		}
	}
	
	public void setArmy(final Hex hex, final Player armyOwner, final List<Creature> army) {
		thingHolder.getChildren().clear();
		circleStackPane.setVisible(false);
		if(army == null)
			return;
		if (!army.isEmpty()) {
			circleStackPane.setVisible(true);
			circleStackPane.getStyleClass().add("hand");
			circleStackPane.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event arg0) {
					handleArmyClick(hex, armyOwner, army);
				}
			});
			
			sizeLbl.setText(String.valueOf(army.size()));
			circle.setFill(armyOwner.getColor());
			
			for (Thing t : army) {
				if(!moving)	t.setSelected(true);
				ThingView tv = new ThingView(50, t);
				thingHolder.getChildren().add(tv);
				if(moving)
					tv.setMovementHandler();
				else
					tv.setDefaultHandler();
			}
		} 
	}
	
	public void setIncomeCounter(Hex hex, IncomeCounter counter) {
		thingHolder.getChildren().clear();
		circleStackPane.setVisible(false);
		if(counter == null)
			return;
		else {
			circleStackPane.setVisible(true);

			sizeLbl.setText("IC");
			circle.setFill(hex.getColor());

			counter.setSelected(true);
			
			ThingView tv = new ThingView(50, counter);
			thingHolder.getChildren().add(tv);
			tv.setDefaultHandler();
		}
	}

	public void setMoving(boolean b) {
		this.moving = b;
	}
}
