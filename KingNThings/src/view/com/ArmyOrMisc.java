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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/** A Control.  Using DetailsView as a presenter.
 *
 * @author kurtis
 */
public class ArmyOrMisc extends HBox {
    
    private Circle circle;
    private Label sizeLbl;
    private StackPane thingHolder;
    
    public ArmyOrMisc(EventHandler<MouseEvent> click) {
	buildComponent();
    }
    StackPane circleStackPane;
    protected void buildComponent() {
	getStyleClass().add("army");
	setAlignment(Pos.CENTER);
	
	circleStackPane = new StackPane();
	circle = new Circle();
	circle.setRadius(22);
	sizeLbl = new Label(/*Integer.toString(army.size())*/);
	circleStackPane.getChildren().addAll(circle, sizeLbl);
        
        thingHolder = new StackPane();
        
	getChildren().addAll(circleStackPane, thingHolder);
	
        
    }
    
    private ImageView createArmyImageView(Thing t) {
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
	
	ImageView img = new ImageView(t.getImage());
	img.setFitWidth(size-7); 
	img.setFitHeight(size-7);
        img.setPreserveRatio(false);
        img.setSmooth(true);
        img.setCache(true);
        img.getStyleClass().add("thing");
       
		
	thingHolder.getChildren().addAll(borderRect, coloredRect, img);
//		pane.getChildren().add(stack);
		
		return img;
    }
    
    public void setArmy(Player armyOwner, List<Thing> army) {
        thingHolder.getChildren().clear();
        
        sizeLbl.setText(String.valueOf(army.size()));
        circle.setFill(armyOwner.getColor());
        
	if (!army.isEmpty()) {
	    for (Thing t : army) {
		// TODO create object pool to avoid recreating image views.
                // create and add image views
                // FIXME, only drawing 1
                createArmyImageView(t);
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
