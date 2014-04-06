/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;


import com.main.KNTAppFactory;
import com.model.Player;
import com.model.Game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

//	private GamePresenter presenter;

	private VBox rootVBox;
	private AnchorPane gameStatus;
	private HBox centerBox;
	private Label currentPlayerLbl;
	private Label currentActionLbl;
	private Button skipPhase;
	private Button loadBtn;

    // Class-level controls needing exposure outside buildView()
	// private Button roll;
	public GameView() {
		buildView();
	}

	private void buildView() {
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
		
		skipPhase = new Button("Skip");
		skipPhase.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				KNTAppFactory.getGamePresenter().skipPhase();
			}
		});
		
		loadBtn = new Button("Load");
		loadBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				KNTAppFactory.getGamePresenter().loadBoard();
			}
		});
		
		currentActionLbl = new Label();
		currentActionLbl.getStyleClass().add("title");
		box.getChildren().addAll(skipPhase, loadBtn, currentPlayerLbl, currentActionLbl );
		
		gameStatus.getChildren().add(box);
		AnchorPane.setLeftAnchor(box, 0.0);
		AnchorPane.setTopAnchor(box, 10.0);
		
		// Contains sidepaneview and PlayArea
		centerBox = new HBox();
		
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
	public void setGame(final Game game, final String actionText) {
		// As a precaution.
		if (game != null) {
			currentPlayerLbl.setText(game.getCurrentPlayer().getName() + "'s Turn: ");
			currentActionLbl.setText((actionText == null ? "" : actionText));
		}
	}

	public Label getCurrentPlayerLbl() {
		return currentPlayerLbl;
	}

	public Label getCurrentActionLbl() {
		return currentActionLbl;
	}
	
	public void showWinnerScreen(Player winner)	{
		rootVBox.getChildren().clear();
		
		Label title = new Label("Sir " + winner.getName() + " has become King of Kadab!");
		title.getStyleClass().add("title");
		Label info = new Label("Close the window and restart to begin a new game");
		
		ImageView im = new ImageView(Game.CROWN_IMAGE);
		im.setFitWidth(300);
		im.setPreserveRatio(true);
		
		rootVBox.setSpacing(20);
		rootVBox.setAlignment(Pos.CENTER);
		rootVBox.getChildren().addAll(title, info, im);
	}
}
