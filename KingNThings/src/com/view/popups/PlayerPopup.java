/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.view.popups;

import com.model.Player;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    	setAlignment(Pos.CENTER);
    	getStyleClass().add("block");
    	Label nameLbl = new Label("Name: Sir " + p.getName());
    	ImageView iv = new ImageView(new Image("view/com/assets/pics/warrior"+p.getId().ordinal()+".jpg"));
    	iv.setFitHeight(450.0);
		iv.setPreserveRatio(true);
    	
    	Label goldLbl = new Label("Gold: " + p.getGold());
	
    	// Not supposed to see their rack instance. 
	
    	getChildren().addAll(nameLbl,iv,goldLbl);
    }
}
