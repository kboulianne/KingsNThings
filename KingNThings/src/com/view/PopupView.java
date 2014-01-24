/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.view;

import com.presenter.PopupPresenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    
    private GameView parent;

    private PopupPresenter presenter;

    // Content to display
    private Pane content;
    
    private Label titleLbl;
    private Button closeBtn; 
    
//    private boolean showing = false;
    
    // Parent will always be GameView
    public PopupView() {
	buildPopup();
    }
    
    public void setPresenter(final PopupPresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    public void setParent(GameView parent) {
	this.parent = parent;
	
	// Add to parent once set
	parent.getChildren().add(this);
	StackPane.setAlignment(this, Pos.CENTER);
    }
    
    /**
     * Shows the Popup with specified content and title.
     * 
     * @param content
     * @param title 
     */
    public void show(Pane content, String title) {
	// Only execute if not visible
	if (!isVisible()) {
	    // Set the view to display.
	    getChildren().add(content);

	    titleLbl.setText(title);

	    setVisible(true);
	}
    }
    
    protected void dismiss() {
	if (isVisible()) {
	    // Remove and null the content
	    getChildren().remove(1);
	    content = null;
	    
	    setVisible(false);
	}
    }
    
    protected void buildPopup() {
	
	
	AnchorPane ap = new AnchorPane();
	
	// Title
	titleLbl = new Label();
	titleLbl.getStyleClass().add("title");
	ap.getChildren().add(titleLbl);
	AnchorPane.setLeftAnchor(titleLbl, 0.0);
	
	// Close button
	closeBtn = new Button("Close");
	closeBtn.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent t) {
		dismiss();
		presenter.dismissPopup();
	    }
	});
	ap.getChildren().add(closeBtn);
	AnchorPane.setRightAnchor(closeBtn, 0.0);
	
	// Self
	getStyleClass().add("popup");
	getChildren().addAll(ap);
	setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
	setAlignment(Pos.CENTER);
	
	// Add to parent in invisible state
	setVisible(false);
    }
}
