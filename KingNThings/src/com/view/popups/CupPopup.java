/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.view.popups;

import com.model.Thing;
import com.view.ThingEvent;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author kurtis
 */
public class CupPopup extends FlowPane {
    
    private EventHandler<ThingEvent> thingHandler;
    // Might not be the best way but works.
    public CupPopup(List<Thing> things, EventHandler<ThingEvent> event) {
	this.thingHandler = event;
	buildPopup(things);
    }
    
    private void buildPopup(List<Thing> things) {
	setVgap(1);
	setHgap(1);
	setPadding(new Insets(10, 0, 0, 0));
	//TODO Hardcoded
	setPrefWrapLength(1180);
	
	for (Thing t : things) {
	    createTileImageView(t);
	}
    }
    
    // IS DUPLICATED NEED TO BE PUT IN Tile class
    private void createTileImageView(final Thing t) {
        int size = 55;
        
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
	coloredRect.setWidth(size-1);
	coloredRect.setHeight(size-1);
	coloredRect.setArcWidth(20);
	coloredRect.setArcHeight(20);
	coloredRect.setFill(t.getColor());
	
	final ImageView img = new ImageView(t.getImage());
	img.setFitWidth(size-7); 
	img.setFitHeight(size-7);
        img.setPreserveRatio(false);
        img.setSmooth(true);
        img.setCache(true);
        img.getStyleClass().add("thing");
        
        // TODO Clean me up
        img.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
		// Fire custom event on mouse clicked
                img.fireEvent(new ThingEvent(t));
            }
        });
        
        // Add custom handler
        img.addEventFilter(ThingEvent.THING_CLICKED, thingHandler);
        
	StackPane pane = new StackPane();
	pane.getChildren().addAll(borderRect, coloredRect, img);
	getChildren().add(pane);
    }
}
