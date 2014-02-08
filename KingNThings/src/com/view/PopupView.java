/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.presenter.PopupPresenter;
import com.view.popups.BattlePopup;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author kurtis
 */
public class PopupView extends VBox {

	//private GameView parent;
	private PopupPresenter presenter;

	//private AnchorPane rootAnchorPane;
    // Content to display
	//private Pane content;

	private Label titleLbl;
	private Button closeBtn;
	private AnchorPane rootAnchorPane;

	// private boolean showing = false;
	// Parent will always be GameView
	public PopupView() {
		buildPopup();
	}

	public void setPresenter(final PopupPresenter presenter) {
		if (presenter == null) {
			throw new NullPointerException("Presenter cannot be null");
		}
		if (this.presenter != null) {
			throw new IllegalStateException("The presenter was already set.");
		}
		this.presenter = presenter;
	}

	public void setParent(GameView parent) {
		//this.parent = parent;

		// Add to parent once set
		parent.getChildren().add(this);
		StackPane.setAlignment(this, Pos.CENTER);
	}

	public void show(Pane content) {
		//this.getChildren().clear();

		if (!isVisible()) {
			// Set the view to display
			//getChildren().add(content);
			rootAnchorPane.setVisible(false);
			rootAnchorPane.setManaged(false);
			
			getChildren().remove(1);
			getChildren().add(content);
			setVisible(true);
		}
	}

	/**
	 * Shows the Popup with specified content and title.
	 *
	 * @param content
	 * @param title
	 */
	public void show(Pane content, String title) {
		if (!isVisible()) {
			// Title
			titleLbl.setText(title);

			// Close button
			closeBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					dismiss();
				}
			});
			
			rootAnchorPane.setVisible(true);
			rootAnchorPane.setManaged(true);
			
			getChildren().remove(1);
			getChildren().add(content);
			setVisible(true);
		}
	}

	public void dismiss() {
		if (isVisible()) {
			setVisible(false);
		}
	}

	protected void buildPopup() {
		getStyleClass().add("popup");
		setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
		setVisible(false);
		
		titleLbl = new Label();
		titleLbl.getStyleClass().add("title");
		closeBtn = new Button("  X  ");
		
		rootAnchorPane = new AnchorPane();
		AnchorPane.setLeftAnchor(titleLbl, 0.0);
		AnchorPane.setRightAnchor(closeBtn, 0.0);
		
		Pane content = new Pane();
		rootAnchorPane.getChildren().addAll(titleLbl, closeBtn);
		
		
		getChildren().addAll(rootAnchorPane, content);
	}

	public Button getCloseBtn() {
		return closeBtn;
	}

}
