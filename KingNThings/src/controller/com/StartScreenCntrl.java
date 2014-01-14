package controller.com;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StartScreenCntrl {
	
	public StartScreenCntrl(final Label myLabel, Button startButton){
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				myLabel.setText("Button Clicked");				
			}
		});
		
	}

}
