/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.com;

import com.model.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 *
 * @author kurtis
 */
public class PlayerLabel extends HBox {
    
    private Circle color;
    private Label name;
    
    public PlayerLabel() {
	buildComponent();
    }
    
    protected void buildComponent() {
	// Positioning
	setAlignment(Pos.CENTER);
	
	name = new Label();
	name.getStyleClass().add("playerName"); 
	color = new Circle();
	color.setRadius(6);
//	circle.setFill(color);   
//	HBox playerBox = new HBox();
	getChildren().addAll(color, name);

		
//		playerBox.setOnMouseEntered(new EventHandler<Event>() {
//
//			@Override
//			public void handle(Event arg0) {
//				VBox popupContentVbox = new VBox();	
//				popupContentVbox.getStyleClass().add("block");
//				popupContentVbox.setMinSize(700, 400);
//				popupContentVbox.setAlignment(Pos.CENTER);
//				//popupContentVbox.getStyleClass().add("border");
//				
//				Label playerNameLbl = new Label("Name: Sir "+name.getValue());
//				//Label blockNameLbl = new Label(block.name);
//				Label goldLbl = new Label("Gold: "+gold);
//				HBox blockHBox = new HBox();
//				blockHBox.setAlignment(Pos.CENTER);
//				blockHBox.getStyleClass().add("block");
//				List<Thing> listOfThings = block.listOfThings;
//				for(Thing thing: listOfThings){
//					thing.paint(blockHBox);
//				}
//				
//				popupContentVbox.getChildren().addAll(playerNameLbl, blockHBox, goldLbl);
//				GameScreen.popup(popupContentVbox);
//			}
//		});
		
//		playerBox.setOnMouseExited(new EventHandler<Event>() {
//
//			@Override
//			public void handle(Event arg0) {
//				GameScreen.dismissPopup();
//			}	
//		});
//		
	
//	pane.getChildren().addAll(playerBox);
    }
    
    public void setPlayer(Player player) {
	color.setFill(player.getColor());
	name.setText("Sir " + player.getName());
    }
}
