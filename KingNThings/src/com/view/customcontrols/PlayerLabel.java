/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.customcontrols;

import com.model.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 *
 * @author kurtis
 */
public class PlayerLabel extends HBox {

	private Circle color;
	private Label name;
	private Player model;

	public PlayerLabel() {
		buildComponent();
	}

	protected void buildComponent() {
		// Positioning
		setAlignment(Pos.CENTER);

		name = new Label();
		name.getStyleClass().add("playerName");
		color = new Circle();
		color.setRadius(6);
		getChildren().addAll(color, name);
	}

	public Player getPlayer() {
		return model;
	}

	public void setPlayer(Player player) {
		this.model = player;
		color.setFill(player.getColor());
		name.setText("Sir " + player.getName());
	}
}
