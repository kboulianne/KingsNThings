package com.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ThingRecruitmentView extends VBox {
	
	private int paidRecValue = 0;
	
	private Label paidRec;
	private Button buyRecBut;
	private Button placeRecBut;
	
	public ThingRecruitmentView(int freeR)	{
		super();
		
		getStyleClass().add("largeSpacing");
		setAlignment(Pos.CENTER);
		
		Label title = new Label("Recruited Things");
		title.getStyleClass().add("title");
		
		Label freeRecLab = new Label("Free Recruits");
		Label freeRec = new Label("" + freeR);
		
		Label paidRecLab = new Label("Paid Recruits");
		paidRec = new Label("" + paidRecValue);
		
		buyRecBut = new Button("Buy A Recruit (5 gold)");
		
		Label exchLab = new Label("Select any number of recruits (multiples of 2) ");
		Label exchLab2 = new Label("from your block that you would like to exchange");
		
		placeRecBut = new Button("Place Recruits");
		
		getChildren().addAll(title, freeRecLab, freeRec, paidRecLab, paidRec, buyRecBut, exchLab, exchLab2, placeRecBut);
	}
	
}
