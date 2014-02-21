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
import com.model.SpecialCharacter;
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
	//private ImageView img;
	private boolean moving;

	//private EventHandler<ThingEvent> thingHandler;

	public ArmyOrMisc(){//EventHandler<ThingEvent> click) {
		//this.thingHandler = click;
		buildComponent();
	}

	protected void buildComponent() {
		getStyleClass().add("block");
		setAlignment(Pos.CENTER);
		moving = false;

		circleStackPane = new StackPane();
		circle = new Circle();
		circle.setRadius(22);
		sizeLbl = new Label(/*Integer.toString(army.size())*/);
		circleStackPane.getChildren().addAll(circle, sizeLbl);
		
		circleStackPane.setVisible(false);

		thingHolder = new HBox();
		thingHolder.getStyleClass().add("army");

		getChildren().addAll(circleStackPane, thingHolder);

	}

/*	private StackPane createArmyImageView(final Thing t) {

		int size = 50;

		Rectangle borderRect = new Rectangle();
		borderRect.setX(0);
		borderRect.setY(0);
		borderRect.setWidth(size);
		borderRect.setHeight(size);
		borderRect.setArcWidth(20);
		borderRect.setArcHeight(20);

		borderRect.setFill(Color.WHITE);

		final Rectangle coloredRect = new Rectangle();
		coloredRect.setX(0);
		coloredRect.setY(0);
		coloredRect.setWidth(size - 1);
		coloredRect.setHeight(size - 1);
		coloredRect.setArcWidth(20);
		coloredRect.setArcHeight(20);
		coloredRect.setFill(t.getColor());

		img = new ImageView(t.getImage());
		img.getStyleClass().add("hand");
		img.setFitWidth(size - 7);
		img.setFitHeight(size - 7);
		img.setPreserveRatio(false);
		img.setSmooth(true);
		img.setCache(true);
		
		//!!!!!!!!!!!!!!
		if(moving)	{
			handleThingClickForMovement(t);
		} else {
			handleThingClicked(t);
		}

		// Add custom handler
		img.addEventFilter(ThingEvent.THING_CLICKED, thingHandler);

		StackPane pane = new StackPane();
		pane.getChildren().addAll(borderRect, coloredRect, img);
		return pane;
	}*/

	public void handleArmyClick(Hex hex, Player armyOwner, List<Creature> army){
		for(Creature c: army){
			c.setSelected(true);
		}
		KNTAppFactory.getArmyDetailsPresenter().showArmy(hex, armyOwner, army);
		if(moving)	KNTAppFactory.getBoardPresenter().handleMoveSetupForArmy();
	}
	
	
	/*private void handleThingClicked(final Thing t){
		img.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent me) {
				// Fire custom event on mouse clicked
				img.fireEvent(new ThingEvent(t));
			}
		});
	}*/
	
	/*private void handleThingClickForMovement(final Thing t){
		img.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent me) {
				if(t.getOwner().equals(GameService.getInstance().getGame().getCurrentPlayer().getName()))	{
					KNTAppFactory.getBoardPresenter().handleMoveSetupForThing(t);
				}
				img.fireEvent(new ThingEvent(t));
			}
		});
	}*/
	
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
				else	t.setSelected(false);
				ThingView tv = new ThingView(50, t);
				thingHolder.getChildren().add(tv);
				if(moving)
					tv.setMovementHandler();
				else
					tv.setDefaultHandler();
			}
		} 
	}
	
	public void setSpecialCharacters(final Hex hex, final List<SpecialCharacter> specChars){
		thingHolder.getChildren().clear();
		circleStackPane.setVisible(false);
		if(specChars == null)
			return;
		if (!specChars.isEmpty()) {
			circleStackPane.setVisible(true);

			sizeLbl.setText("SC");
			circle.setFill(hex.getColor());

			for (SpecialCharacter gp : specChars) {
				ThingView tv = new ThingView(50, (Thing) gp);
				thingHolder.getChildren().add(tv);
				//tv.setDefaultHandler();
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

	/*public void setThingHandler(EventHandler<ThingEvent> event){
		thingHandler = event;
	}*/
}
