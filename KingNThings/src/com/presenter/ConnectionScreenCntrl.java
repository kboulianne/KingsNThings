package com.presenter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class ConnectionScreenCntrl {

	private int numOfConnectedPlayers;
	Label player1;
	Circle circle1;
	Label player2;
	Circle circle2;
	Label player3;
	Circle circle3;
	Label player4;
	Circle circle4;
	Button beginButton;

	public ConnectionScreenCntrl(Label p1, Circle c1, Label p2, Circle c2, Label p3,
			Circle c3, Label p4, Circle c4, Button bButton) {
		// TODO Auto-generated constructor stub
		setNumOfConnectedPlayers(0);
		player1 = p1;
		circle1 = c1;
		player2 = p2;
		circle2 = c2;
		player3 = p3;
		circle3 = c3;
		player4 = p4;
		circle4 = c4;
		beginButton = bButton;
	}

	public void addPlayer(int playerId, String name) { // player id {1 2 3 4}
		setNumOfConnectedPlayers(getNumOfConnectedPlayers() + 1);
		if (playerId == 1) {
			player1.setText("Sir " + name);
			player1.getStyleClass().add("playername");
			circle1.setFill(Paint.valueOf("FF0000"));
		} else if (playerId == 2) {
			player2.setText("Sir " + name);
			player2.getStyleClass().add("playername");
			circle2.setFill(Paint.valueOf("00FF00"));
			addBeginButtonListener();
		} else if (playerId == 3) {
			player3.setText("Sir " + name);
			player3.getStyleClass().add("playername");
			circle3.setFill(Paint.valueOf("0000FF"));
		} else if (playerId == 4) {
			player4.setText("Sir " + name);
			player4.getStyleClass().add("playername");
			circle4.setFill(Paint.valueOf("FFFF00"));
		}
	}

	public void removePlayer(int playerId) { // player id {1 2 3 4}
		setNumOfConnectedPlayers(getNumOfConnectedPlayers() - 1);
		final String W_STR = "Waiting for Connection...";
		if (playerId == 1) {
			player1.setText(W_STR);
			player1.getStyleClass().removeAll("playername");
			player1.getStyleClass().add("disabled");
			circle1.setFill(Paint.valueOf("999999"));
		} else if (playerId == 2) {
			player2.setText(W_STR);
			player2.getStyleClass().removeAll("playername");
			player2.getStyleClass().add("disabled");
			circle2.setFill(Paint.valueOf("999999"));
			addBeginButtonListener();
		} else if (playerId == 3) {
			player3.setText(W_STR);
			player3.getStyleClass().removeAll("playername");
			player3.getStyleClass().add("disabled");
			circle3.setFill(Paint.valueOf("999999"));
		} else if (playerId == 4) {
			player4.setText(W_STR);
			player4.getStyleClass().removeAll("playername");
			player4.getStyleClass().add("disabled");
			circle4.setFill(Paint.valueOf("999999"));
		}

	}

	public void addBeginButtonListener() {
		beginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
			}
		});
	}

	public int getNumOfConnectedPlayers() {
		return numOfConnectedPlayers;
	}

	public void setNumOfConnectedPlayers(int numOfConnectedPlayers) {
		this.numOfConnectedPlayers = numOfConnectedPlayers;
	}

}
