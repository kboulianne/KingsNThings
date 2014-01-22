package com.presenter;

import com.game.services.GameService;
import javafx.application.Platform;
//import jfxtras.labs.scene.control.BeanPathAdapter;
import view.com.GameScreen;

public class GameScreenCntrl {
    
    private final GameScreen view;
    private GameService service = GameService.getInstance();

    GameScreenCntrl(GameScreen view) {
    	this.view = view;
    }

    void initialize() {
	// Show view
	view.show();
	
	// Bind to the game instance managed by the GameService. Needs to be run from UI thread.
	Platform.runLater(new Runnable() {
	    
	    @Override
	    public void run() {
		view.setBindingsFor(service.getGame());
	    }
	});
	
    }
    
    public void rollDice() {
	service.getGame().rollDice();
    }
    
    public void test_Phases() {
	service.getGame().endTurn();
    }
}


