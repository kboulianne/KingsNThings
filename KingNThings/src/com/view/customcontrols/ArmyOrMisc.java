/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.customcontrols;

import com.main.KNTAppFactory;
import com.model.Creature;
import com.model.Player;
import com.model.Thing;
import com.view.ThingEvent;

import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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
	private ImageView img;
	private String phase;
	
	private Creature lastSelectedCreature;

	private final EventHandler<ThingEvent> thingHandler;

	public ArmyOrMisc(EventHandler<ThingEvent> click) {
		this.thingHandler = click;
		buildComponent();
	}

	protected void buildComponent() {
		getStyleClass().add("block");
		setAlignment(Pos.CENTER);
		phase = "other";

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

	private void createArmyImageView(final Thing t) {

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
		img.setFitWidth(size - 7);
		img.setFitHeight(size - 7);
		img.setPreserveRatio(false);
		img.setSmooth(true);
		img.setCache(true);
		img.getStyleClass().add("thing");
		if(phase.equals("movement"))
			handleThingClickForMovement(t);
		else if(phase.equals("placement"))
			img.setOnMouseClicked(null);
		else
			handleThingClicked(t);			

		// Add custom handler
		img.addEventFilter(ThingEvent.THING_CLICKED, thingHandler);

		StackPane pane = new StackPane();
		pane.getChildren().addAll(borderRect, coloredRect, img);
		thingHolder.getChildren().add(pane);
	}

	
	public void handleThingClicked(final Thing t){
		img.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent me) {
				// Fire custom event on mouse clicked
				lastSelectedCreature = (Creature)t;
				img.fireEvent(new ThingEvent(t));
			}
		});
	}
	
	public void handleThingClickForMovement(final Thing t){
		img.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent me) {
				lastSelectedCreature = (Creature)t;
				KNTAppFactory.getBoardPresenter().handleMoveButtonClick();
				img.fireEvent(new ThingEvent(t));
			}
		});
	}
	
	public void setArmy(Player armyOwner, List<Creature> army) {
		
		thingHolder.getChildren().clear();
		circleStackPane.setVisible(false);
		if(army == null)
			return;
		if (!army.isEmpty()) {
			circleStackPane.setVisible(true);

			sizeLbl.setText(String.valueOf(army.size()));
			circle.setFill(armyOwner.getColor());

			for (Thing t : army) {
            	// TODO create object pool to avoid recreating image views.
				// create and add image views
				// FIXME, only drawing 1
				createArmyImageView(t);
			}
		} 
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Creature getLastSelectedCreature() {
		return lastSelectedCreature;
	}


}
