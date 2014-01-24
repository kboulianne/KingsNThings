/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.model.Player;
import com.view.PlayerPopup;
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
    private PlayerPopup playerPopup;
    
    public PopupPresenter(PopupView view) {
	this.view = view;
	this.view.setPresenter(this);
	
//	this.playerPopup = playerPopup;
    }
  

    public PopupView getView() {
	return view;
    }

    public void dismissPopup() {
	view.dismiss();
    }

    public void showPlayerPopup(Player p) {
	view.show(new PlayerPopup(p));
    }
}
