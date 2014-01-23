/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.com;

import com.model.game.Game;
import com.presenter.DicePresenter;
import com.presenter.GamePresenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

// TODO: Can't really make an abstract view since multiple inheritance is forbidden. Use Template Pattern?

/** Main Entry point of MVP. This is the main game screen.
 *
 * @author kurtis
 */
public class GameView extends StackPane {

    private GamePresenter presenter;
//    // Sub-presenters
//    private DicePresenter dicePresenter;
   
    private VBox rootVBox;
    private AnchorPane gameStatus;
    private HBox centerBox;
    private Label currentPlayerLbl;
    
    // Class-level controls needing exposure outside buildView()
    // private Button roll;
    public GameView() {
//        this.model = game;
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
	// Root Pane stuff
	rootVBox = new VBox();
	rootVBox.getStyleClass().add("border");
	rootVBox.setAlignment(Pos.TOP_CENTER);
	
	getStyleClass().add("border");
	setAlignment(Pos.TOP_CENTER);
	
	
	// Game status stuff
	gameStatus = new AnchorPane();
	gameStatus.setId("gameStatus");
	
	// TODO add a "Current action to execute" label.
	currentPlayerLbl = new Label();
	currentPlayerLbl.getStyleClass().add("title");
	gameStatus.getChildren().add(currentPlayerLbl);
	AnchorPane.setLeftAnchor(currentPlayerLbl, 0.0);
	AnchorPane.setTopAnchor(currentPlayerLbl, 10.0);
	
	// Contains sidepaneview and PlayArea
	centerBox = new HBox();
	// TESTING FOR current player
	Button btn = new Button("Test End Turn");
	AnchorPane.setLeftAnchor(btn, 0d);
	AnchorPane.setTopAnchor(btn, 100d);
	btn.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent t) {
		presenter.endPlayerTurn();
	    }
	    
	});
	
	rootVBox.getChildren().addAll(gameStatus, centerBox);
	getChildren().add(rootVBox);
	getChildren().add(btn);
    }
    
    /**
     * Adds the DiceView as a sub-view.
     * @param view The DiceView to add.
     */
    public void addDiceView(DiceView view) {
	gameStatus.getChildren().add(view);
	
	AnchorPane.setRightAnchor(view, 0.0);
	AnchorPane.setTopAnchor(view, 0.0);
	
//	rootVBox.getChildren().add(0, gameStatus);
    }
    
    public void addSidePaneView(SidePaneView view) {
	// Add to the centerBox
	centerBox.getChildren().add(view);
	
	// TODO needs restructuring
//	rootVBox.getChildren().add(1, centerBox);
    }
    
    public void addPlayAreaView() {
	//.add(1, view) as precaution
	//rootVBox.getChildren().add(2, playingArea);
    }
    
    /**
     * Sets the new UI State according to the data contained in Game instance.
     * @param game The game object the UI should display.
     */
    public void setGame(final Game game) {
	// As a precaution.
	if (game != null) {
	    // Set all GameView properties here
	    currentPlayerLbl.setText("Sir " + game.getCurrentPlayer().getName() + "'s Turn: <ACTION>");
	    
	    
	    
	    
	    
	}
    }
}
