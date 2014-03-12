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

	public Rack(){
		buildComponent();
	}

	private void buildComponent() {
		setAlignment(Pos.CENTER);
		getStyleClass().add("block");
	}

	// sets things with default handler
	public void setThings(List<Thing> things) {
		getChildren().clear();
		for (Thing t : things) {
			t.setSelected(false);
			ThingView tv = new ThingView(50, t);
			getChildren().add(tv);
			tv.setDefaultHandler();
		}
	}
	
	public void setExchangeThingsHandler(List<Thing> things){
		getChildren().clear();
		for (Thing t : things) {
			t.setSelected(false);
			ThingView tv = new ThingView(50, t);
			getChildren().add(tv);
			tv.setExchangeThingHandler();
		}
	}
	
	public void setRecruitingThingsHandler(List<Thing> things)	{
		getChildren().clear();
		for (Thing t : things) {
			t.setSelected(false);
			ThingView tv = new ThingView(50, t);
			getChildren().add(tv);
			tv.setRecruitingThingsHandler();
		}
	}

	public void setDoNothingHandler(List<Thing> things){
		getChildren().clear();
		for (Thing t : things) {
			t.setSelected(false);
			ThingView tv =new ThingView(50, t);
			getChildren().add(tv);
			tv.setDoNothingHandler();
		}
	}
}
