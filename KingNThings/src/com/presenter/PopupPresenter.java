/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.view.PopupView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 *
 * @author kurtis
 */
public class PopupPresenter {
    private PopupView view;
    
    // All the views (Pane classes) this popup can display.
    private StackPane test;
    
    public PopupPresenter(PopupView view) {
	this.view = view;
	this.view.setPresenter(this);
    }

    public PopupView getView() {
	return view;
    }
    
    public void showPopup() {
	test = new StackPane();
	test.getChildren().add(new Label("TEST POPUP"));
	
	view.show(test);
    }

    // Do we really need this?
    public void dismissPopup() {
	
    }
}
