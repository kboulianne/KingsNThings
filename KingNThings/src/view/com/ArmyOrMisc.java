/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.com;

import com.model.Player;
import com.model.Thing;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

/** A Control.  Using DetailsView as a presenter.
 *
 * @author kurtis
 */
public class ArmyOrMisc extends HBox {
    
    private Circle circle;
    private Label sizeLbl;
    
    
    public ArmyOrMisc(EventHandler<MouseEvent> click) {
	buildComponent();
    }
    
    protected void buildComponent() {
	getStyleClass().add("army");
	setAlignment(Pos.CENTER);
	
	StackPane circleStackPane = new StackPane();
	circle = new Circle();
	circle.setRadius(22);
	sizeLbl = new Label(/*Integer.toString(army.size())*/);
	circleStackPane.getChildren().addAll(circle, sizeLbl);
	getChildren().add(circleStackPane);
	

    }
    
    public void setArmy(Player armyOwner, List<Thing> army) {
	if (!army.isEmpty()) {
	    for (Thing t : army) {
		
	    }
	}
	//	for(final Thing t:army){
	    //ImageView thingImg = t.paintThingRectangle(50, armyBox);
				//t.paintThingInDetails(detailsBox);
//				thingImg.setOnMouseClicked(new EventHandler<Event>() {
//					@Override
//					public void handle(Event arg0) {
//						detailsBox.getChildren().clear();
//						t.paintThingInDetails(detailsBox);	
//					}
//				});
//			}	
    }
}
