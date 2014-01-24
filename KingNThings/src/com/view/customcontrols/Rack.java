/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.view.customcontrols;

import com.model.Thing;
import com.view.ThingEvent;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author kurtis
 */
public class Rack extends HBox {
    
    public static final int CAPACITY = 8;
    
    // Maximum of 8 Tiles on Rack
    // TODO Make Tile since ArmyOrMisc uses same logic.
    // Change to List<TileView/Component>
    //TODO ObjectPool
//    private List<StackPane> things;
    private EventHandler<ThingEvent> thingHandler;
    
    public Rack(EventHandler<ThingEvent> thingHandler) {
//	things = new ArrayList<>();
	this.thingHandler = thingHandler;
	
//	for (int i = 0 ; i < CAPACITY ; i ++) {
//	    thingsIv.add(new ImageView());
//	}
	
	buildComponent();
    }
    
    protected void buildComponent() {
	setAlignment(Pos.CENTER);
	getStyleClass().add("block");
	
	// Initialize Image Views.
	
    }
    
    // FIXME: Copied from ArmyOrMisc. Make this class, and ArmyOrMisc use Tile component
    private void createRackImageView(final Thing t) {
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
    
    public void setThings(List<Thing> things) {
	getChildren().clear();
	
	
	if (!things.isEmpty()) {
	    for (Thing t : things) {
		createRackImageView(t);
	    }
	}
	else {
	    
	}
    }
}
