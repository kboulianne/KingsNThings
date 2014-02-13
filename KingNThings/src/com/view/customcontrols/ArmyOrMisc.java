/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.customcontrols;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Creature;
import com.model.GamePiece;
import com.model.Hex;
import com.model.Player;
import com.model.Thing;
import com.view.ThingEvent;

import java.util.List;

import javafx.event.Event;
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
	private boolean moving;
	
	private Creature lastSelectedCreature;

	private EventHandler<ThingEvent> thingHandler;

	public ArmyOrMisc(EventHandler<ThingEvent> click) {
		this.thingHandler = click;
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

	private StackPane createArmyImageView(final Thing t) {

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
	}

	public void handleArmyClick(Hex hex, Player armyOwner, List<Creature> army){
		KNTAppFactory.getArmyDetailsPresenter().showArmy(hex, armyOwner, army);
		if(moving)
			KNTAppFactory.getBoardPresenter().handleMoveSetupForArmy();
	}
	
	
	private void handleThingClicked(final Thing t){
		img.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent me) {
				// Fire custom event on mouse clicked
				if(t instanceof Creature)	lastSelectedCreature = (Creature)t;
				img.fireEvent(new ThingEvent(t));
			}
		});
	}
	
	private void handleThingClickForMovement(final Thing t){
		img.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent me) {
				if(t instanceof Creature)	lastSelectedCreature = (Creature) t;
				if(t.getOwner().equals(GameService.getInstance().getGame().getCurrentPlayer().getName()))	{
					KNTAppFactory.getBoardPresenter().handleMoveSetupForThing(t);
				}
				img.fireEvent(new ThingEvent(t));
			}
		});
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
				
            	// TODO create object pool to avoid recreating image views.
				// create and add image views
				// FIXME, only drawing 1
				StackPane pane = createArmyImageView(t);
				thingHolder.getChildren().add(pane);
			}
		} 
	}
	
	public void setMisc(final Hex hex, final List<GamePiece> gamePieces){
		thingHolder.getChildren().clear();
		circleStackPane.setVisible(false);
		if(gamePieces == null)
			return;
		if (!gamePieces.isEmpty()) {
			circleStackPane.setVisible(true);
			//circleStackPane.getStyleClass().add("hand");
			/*circleStackPane.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event arg0) {
					handleArmyClick(hex, armyOwner, gamePieces);
				}
			});*/

			sizeLbl.setText("M");
			circle.setFill(Color.DARKGRAY);

			for (GamePiece gp : gamePieces) {
            	// TODO create object pool to avoid recreating image views.
				// create and add image views
				// FIXME, only drawing 1
					StackPane pane = createArmyImageView((Thing) gp);
					thingHolder.getChildren().add(pane);
			}
		} 
	}

	public void setMoving(boolean b) {
		this.moving = moving;
	}

	public Creature getLastSelectedCreature() {
		return lastSelectedCreature;
	}
	
	public void setThingHandler(EventHandler<ThingEvent> event){
		thingHandler = event;
	}

	public void setLastSelectedCreature(Creature lastSelectedCreature) {
		this.lastSelectedCreature = lastSelectedCreature;
	}


}
