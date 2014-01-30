/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.model.game.Game;
import com.presenter.GamePresenter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

// TODO: Can't really make an abstract view since multiple inheritance is forbidden. Use Template Pattern?
/**
 * Main Entry point of MVP. This is the main game screen.
 *
 * @author kurtis
 */
public class GameView extends StackPane {

	private GamePresenter presenter;

	public VBox rootVBox;
	private AnchorPane gameStatus;
	private HBox centerBox;
	private Label currentPlayerLbl;
	private Label currentActionLbl;

    // Class-level controls needing exposure outside buildView()
	// private Button roll;
	public GameView() {
		buildView();
	}

	public void setPresenter(final GamePresenter presenter) {
		if (presenter == null) {
			throw new NullPointerException("Presenter cannot be null");
		}

		if (this.presenter != null) {
			throw new IllegalStateException("The presenter was already set.");
		}

		this.presenter = presenter;
	}

	protected void buildView() {
		// Root Pane stuff
		rootVBox = new VBox();
		rootVBox.getStyleClass().add("border");
		rootVBox.setAlignment(Pos.TOP_CENTER);

		setAlignment(Pos.TOP_CENTER);

		// Game status stuff
		gameStatus = new AnchorPane();
		gameStatus.setId("gameStatus");

		// Holds Current Player Label and Current Action Label.
		HBox box = new HBox();
		currentPlayerLbl = new Label();
		currentPlayerLbl.getStyleClass().add("title");
		
		currentActionLbl = new Label();
		currentActionLbl.getStyleClass().add("title");
		box.getChildren().addAll(currentPlayerLbl, currentActionLbl);
		
		gameStatus.getChildren().add(box);
		AnchorPane.setLeftAnchor(box, 0.0);
		AnchorPane.setTopAnchor(box, 10.0);
		
		// Contains sidepaneview and PlayArea
		centerBox = new HBox();

//		Button finishTurnBtn = new Button("Finished Turn");
		//Button test = new Button("Test");
		//AnchorPane.setLeftAnchor(test, 500.0);
		//AnchorPane.setTopAnchor(test, 0.0);
		//test.setOnAction(new EventHandler<ActionEvent>() {

			//@Override
			//public void handle(ActionEvent t) {
				//presenter.test();
			//}
		//});
		//gameStatus.getChildren().add(test);
		rootVBox.getChildren().addAll(gameStatus, centerBox);
		getChildren().add(rootVBox);
	}

	/**
	 * Adds the DiceView as a sub-view.
	 *
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

	public void addBoardView(BoardView view) {
	//.add(1, view) as precaution
		//rootVBox.getChildren().add(2, playingArea);
		centerBox.getChildren().add(view);
	}

	public void addPlayerInfoView(PlayerInfoView view) {
		rootVBox.getChildren().add(view);
	}

	/**
	 * Sets the new UI State according to the data contained in Game instance.
	 *
	 * @param game The game object the UI should display.
	 */
	public void setGame(final Game game) {
		//TODO Could actually do most updating here since this presenter has all
		// other presenters as a dependency.
		// As a precaution.
		if (game != null) {
			currentPlayerLbl.setText("Sir " + game.getCurrentPlayer().getName() + "'s Turn: ");
	
		}
	}

	public Label getCurrentPlayerLbl() {
		return currentPlayerLbl;
	}

	public Label getCurrentActionLbl() {
		return currentActionLbl;
	}
}
