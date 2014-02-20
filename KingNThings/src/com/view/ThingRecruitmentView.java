package com.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ThingRecruitmentView extends VBox {
	
	private int paidRecValue = 0;
	
	private Label paidRec;
	private Button buyRecBut;
	private Button placeRecBut;
	
	public ThingRecruitmentView(int freeR, int paidR)	{
		super();
		paidRecValue = paidR;
		
		getStyleClass().add("largeSpacing");
		setAlignment(Pos.CENTER);
		
		Label title = new Label("Recruited Things\n");
		title.getStyleClass().add("title");
		
		VBox FBox = new VBox();
		FBox.setAlignment(Pos.CENTER);
		Label freeRecLab = new Label("Free Recruits");
		Label freeRec = new Label("" + freeR);
		FBox.getChildren().addAll(freeRecLab, freeRec);
		
		VBox PBox = new VBox();
		PBox.setAlignment(Pos.CENTER);
		Label paidRecLab = new Label("Paid Recruits");
		paidRec = new Label("" + paidRecValue);
		PBox.getChildren().addAll(paidRecLab, paidRec);
		
		HBox rec = new HBox(80);
		rec.setAlignment(Pos.CENTER);
		rec.getChildren().addAll(FBox, PBox);
		
		buyRecBut = new Button("Buy A Recruit (5 gold)");
		
		Label exchLab = new Label("Select any recruits you would like to exchange\n"
				+ "    1 new recruit is given for every 2 returned");
		
		placeRecBut = new Button("Place Recruits");
		
		getChildren().addAll(title, rec, buyRecBut, exchLab, placeRecBut);
	}
	
	public int getPaidRecruitsValue()	{	return paidRecValue;	}
	public Label getPaidRecruitsLabel()	{	return paidRec;			}
	public Button getBuyRecruitsButton()	{	return buyRecBut;	}
	public Button getPlaceRecruitsButton()	{	return placeRecBut;	}
	public void increasePaidRecruits()	{
		paidRecValue++;
		paidRec.setText("" + paidRecValue);
	}
}
