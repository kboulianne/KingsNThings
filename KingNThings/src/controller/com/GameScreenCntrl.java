package controller.com;

import com.services.game.GameService;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import jfxtras.labs.scene.control.BeanPathAdapter;
import model.com.game.Game;
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
	
	// Bind to the game instance managed by the GameController. Needs to be run from UI thread.
	Platform.runLater(new Runnable() {
	    
	    @Override
	    public void run() {
		view.setBindings(new BeanPathAdapter<>(service.getGame()));
	    }
	});
	
    }
    
    public void rollDice() {
	service.rollDice();
    }
}


