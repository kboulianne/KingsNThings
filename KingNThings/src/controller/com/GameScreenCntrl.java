package controller.com;

import controller.com.game.GameController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class GameScreenCntrl {

    /** 
     *	Delegate game actions/events to the GameController. 
     *	This controller is responsible for handling UI actions
     *  only.
     */
    private GameController gameCtrl;
    
    public GameScreenCntrl() {
	gameCtrl = new GameController();
    }
    
	public GameScreenCntrl(Button button) {
		// TODO Auto-generated constructor stub
		
		button.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
			    // i.e.
			    // gameCtrl.doStuff(...);
			}
		});
	}

	
	
}
