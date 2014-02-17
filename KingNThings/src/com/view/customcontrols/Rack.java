/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.customcontrols;

import com.model.Thing;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

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
	//private EventHandler<ThingEvent> thingHandler;

	public Rack(){//EventHandler<ThingEvent> thingHandler) {
//	things = new ArrayList<>();
		//this.thingHandler = thingHandler;

//	for (int i = 0 ; i < CAPACITY ; i ++) {
//	    thingsIv.add(new ImageView());
//	}
		buildComponent();
	}

	protected void buildComponent() {
		setAlignment(Pos.CENTER);
		getStyleClass().add("block");
	}

	// sets things with default handler
	public void setThings(List<Thing> things) {
		getChildren().clear();
		for (Thing t : things) {
			t.setSelected(false);
			ThingView tv =new ThingView(50, t);
			getChildren().add(tv);
			//tv.setDefaultHandler();
		}
	}
	
	public void setExcahngeThingsHandler(List<Thing> things){
		getChildren().clear();
		for (Thing t : things) {
			t.setSelected(false);
			ThingView tv =new ThingView(50, t);
			getChildren().add(tv);
			tv.setExchangeThingHandler();
		}
	}
}
