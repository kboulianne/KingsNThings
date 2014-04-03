package com.view;

import com.main.KNTAppFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ThingRecruitmentView extends VBox {
	
	private Label freeRec;
	private Label paidRec;
	private Button buyRecBut;
	private Button placeRecBut;
	
	public ThingRecruitmentView()	{
		super();
		
		getStyleClass().add("largeSpacing");
		setAlignment(Pos.CENTER);
		
		Label title = new Label("Recruited Things\n");
		title.getStyleClass().add("title");
		
		VBox FBox = new VBox();
		FBox.setAlignment(Pos.CENTER);
		Label freeRecLab = new Label("Free Recruits");
		freeRec = new Label();
		FBox.getChildren().addAll(freeRecLab, freeRec);
		
		VBox PBox = new VBox();
		PBox.setAlignment(Pos.CENTER);
		Label paidRecLab = new Label("Paid Recruits");
		paidRec = new Label();
		PBox.getChildren().addAll(paidRecLab, paidRec);
		
		HBox rec = new HBox(80);
		rec.setAlignment(Pos.CENTER);
		rec.getChildren().addAll(FBox, PBox);
		
		buyRecBut = new Button("Buy A Recruit (5 gold)");
		buyRecBut.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				KNTAppFactory.getSidePanePresenter().handleBuyRecruits(ThingRecruitmentView.this);
			}
		});
		
		Label exchLab = new Label("Select any recruits you would like to exchange\n"
				+ "    1 new recruit is given for every 2 returned");
		
		placeRecBut = new Button("Place Recruits");
		placeRecBut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				KNTAppFactory.getSidePanePresenter().handlePlaceRecruit();
			}
		});
		
		getChildren().addAll(title, rec, buyRecBut, exchLab, placeRecBut);
	}
	
	public void setFreeRecruit(int free) {
		freeRec.setText(String.valueOf(free));
	}
	public void setPaidRecruit(int rec) {
		paidRec.setText(String.valueOf(rec));
	}
//	public int getPaidRecruitsValue()	{	return paidRecValue;	}
	public Label getPaidRecruitsLabel()	{	return paidRec;			}
	public Button getBuyRecruitsButton()	{	return buyRecBut;	}
	public Button getPlaceRecruitsButton()	{	return placeRecBut;	}
//	void increasePaidRecruits()	{
//		paidRecValue++;
//		paidRec.setText("" + paidRecValue);
//	}
}
