package controller.com;

import com.services.game.GameService;
import javafx.application.Platform;
import jfxtras.labs.scene.control.BeanPathAdapter;
import model.com.game.Game;
import view.com.GameScreen;

public class GameScreenCntrl {
    
    private final GameScreen view;
    private Game game;
    

    GameScreenCntrl(GameScreen view) {
	this.view = view;
	
	game = GameService.getInstance().getGame();
    }

    void initialize() {
	// Show view
	view.show();
	
	// Bind to the game instance which is managed by the GameController
	Platform.runLater(new Runnable() {

	    @Override
	    public void run() {
		view.setBindings(new BeanPathAdapter<>(game));
	    }
	});
	
    }

}
