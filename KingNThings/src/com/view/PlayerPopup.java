/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.view;

import com.model.Player;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author kurtis
 */
public class PlayerPopup extends VBox {
    
    public PlayerPopup(Player toShow) {
	createPopup(toShow);
    }
    
    private void createPopup(Player p) {
	Label nameLbl = new Label("Name: Sir " + p.getName());
	Label goldLbl = new Label("Gold: " + p.getGold());
	
	// Not supposed to see their rack instance.
	
	getChildren().addAll(nameLbl, goldLbl);
    }
}
