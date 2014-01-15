package controller.com;

import view.com.ConnectionScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartScreenCntrl {
	
	public StartScreenCntrl(final TextField tV, Button startButton){
			
		startButton.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				String nobleName = tV.getText();
				if(nobleName.isEmpty()){
					new ConnectionScreen("Nameless Noble").show(); //temporary
				}else{
					new ConnectionScreen(nobleName).show();
				}
			}
		});
		
	}
}
