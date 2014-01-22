/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.com;

import com.model.game.Game;
import com.presenter.DicePresenter;
import com.presenter.GamePresenter;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/** Main Entry point of MVP. This is the main game screen.
 *
 * @author kurtis
 */
public class GameView extends AnchorPane {

    private GamePresenter presenter;
    private DicePresenter dicePresenter;
    private Game model;
    
    private Label currentPlayerLbl;
    
    // Class-level controls needing exposure outside buildView()
    // private Button roll;
    public GameView(final Game game) {
        this.model = game;
        buildView();
    }
    
    public void setPresenter(final GamePresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    protected void buildView() {
	setId("gameStatus");
	
	currentPlayerLbl = new Label("Sir "+model.getCurrentPlayer().getName()+"'s Turn: Roll Dice");
	currentPlayerLbl.getStyleClass().add("title");
	getChildren().add(currentPlayerLbl);
	AnchorPane.setLeftAnchor(currentPlayerLbl, 0.0);
	AnchorPane.setTopAnchor(currentPlayerLbl, 10.0);
	
	// Create the dice view and presenterPane
	createAndAddDiceView();
    }
    
    private void createAndAddDiceView() {
	DiceView diceView = new DiceView(model.getDie1(), model.getDie2());
	dicePresenter = new DicePresenter(diceView, presenter);
	// TODO pass to constructor?
	diceView.setPresenter(dicePresenter);
	
	getChildren().add(diceView);
    }
    
    public void setGame(final Game game) {
	// As a precaution.
	if (game != null) {
	    // Set all properties here
	}
    }
}
