package com.model;

import java.util.List;

import view.com.GameScreen;
import com.presenter.Paintable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player implements Paintable	{
	PlayerId id; 		// {1, 2, 3, 4}
	Color color;	// {blue, green, red, yellow}
	Block block;
	int gold;
	private StringProperty name;
	
	public enum PlayerId { ONE(Color.BLUE),TWO(Color.GREEN),THREE(Color.RED),FOUR(Color.YELLOW); 
		private final Color color;
		PlayerId(Color c) {
			color = c;
		}
	}
	
	
	public Player(PlayerId i, String name) {
	    this.name = new SimpleStringProperty(name);
	    color = i.color;
	    id = i;
	    block = new Block();
	    gold = 0;
	}
	
	/**
	 * Convenience getter for the player's name.
	 * @return 
	 */
	public final String getName() {
	    return name.get();
	}
	
	public final StringProperty getNameProperty() {
	    return name;
	}
	
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
	
	public PlayerId getId() {
		return id;
	}
	public final void setNameProperty(final StringProperty name) {
	    this.name = name;
	}

	@Override
	public void paint(Pane pane) {
		// TODO Auto-generated method stub
		Label playerLbl = new Label("Sir "+name.getValue());
		playerLbl.getStyleClass().add("playerName"); 
		Circle circle = new Circle();
		circle.setRadius(6);
		circle.setFill(color);   
		HBox playerBox = new HBox();
		playerBox.getChildren().addAll(circle, playerLbl);
		playerBox.setAlignment(Pos.CENTER);
		
		playerBox.setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				VBox popupContentVbox = new VBox();	
				popupContentVbox.getStyleClass().add("block");
				popupContentVbox.setMinSize(700, 400);
				popupContentVbox.setAlignment(Pos.CENTER);
				//popupContentVbox.getStyleClass().add("border");
				
				Label playerNameLbl = new Label("Name: Sir "+name.getValue());
				//Label blockNameLbl = new Label(block.name);
				Label goldLbl = new Label("Gold: "+gold);
				HBox blockHBox = new HBox();
				blockHBox.setAlignment(Pos.CENTER);
				blockHBox.getStyleClass().add("block");
				List<Thing> listOfThings = block.listOfThings;
				for(Thing thing: listOfThings){
					thing.paint(blockHBox);
				}
				
				popupContentVbox.getChildren().addAll(playerNameLbl, blockHBox, goldLbl);
				GameScreen.popup(popupContentVbox);
			}
		});
		
		playerBox.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				GameScreen.dismissPopup();
			}	
		});
		
		pane.getChildren().addAll(playerBox);
		
	}
	
	public void paintGold(Pane pane){
		pane.getChildren().add(new Label("Gold: "+gold));
	}
}
