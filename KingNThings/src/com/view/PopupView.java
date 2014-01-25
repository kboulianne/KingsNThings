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

    private AnchorPane rootAnchorPane;
    // Content to display
    //private Pane content;
    
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
		//this.parent = parent;
		
		// Add to parent once set
		parent.getChildren().add(this);
		StackPane.setAlignment(this, Pos.CENTER);
    }
    
    public void show(Pane content) {
    	this.getChildren().clear();
		
		if (!isVisible()) {
		    // Set the view to display
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
    	this.getChildren().clear();
    	
    	// Title
		titleLbl = new Label(title);
		titleLbl.getStyleClass().add("title");
		AnchorPane.setLeftAnchor(titleLbl, 0.0);
		
		// Close button
		closeBtn = new Button("Close");
		closeBtn.setOnAction(new EventHandler<ActionEvent>() {
	
		    @Override
		    public void handle(ActionEvent t) {
		    	dismiss();
		    }
		});
		rootAnchorPane.getChildren().addAll(titleLbl, closeBtn);
		AnchorPane.setRightAnchor(closeBtn, 0.0);
    	
		//titleLbl.setVisible(true);
		//closeBtn.setVisible(true);
		// Only execute if not visible
		if (!isVisible()) {
		    // Set the view to display.
		    getChildren().addAll(rootAnchorPane,content);	
		    setVisible(true);
		}
    }
    
    public void dismiss() {
		if (isVisible()) {
		    // Remove and null the content
		    getChildren().clear();   
		    setVisible(false);
		}
    }
    
    protected void buildPopup() {
		rootAnchorPane = new AnchorPane();
		getStyleClass().add("popup");
		getChildren().addAll(rootAnchorPane);
		setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
		setVisible(false);
    }
}
