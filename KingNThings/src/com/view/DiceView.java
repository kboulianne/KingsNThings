/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.main.KNTAppFactory;
import com.model.Die;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Displays both dice and the roll button.
 *
 * @author kurtis
 */
public class DiceView extends HBox {
	private ImageView die1Iv;
	private ImageView die2Iv;
	private Button rollBtn;
	private Button endTurnBtn;

	public DiceView() {
		buildView();
	}

	private void buildView() {
		// View initializations
		setId("topRBox");

		// First die
		die1Iv = new ImageView();
		die1Iv.setFitWidth(48);
		die1Iv.setPreserveRatio(true);
		die1Iv.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				KNTAppFactory.getDicePresenter().handleMouseClick(me, true);
			}
		});
		
		
		// Second die
		die2Iv = new ImageView();
		die2Iv.setFitWidth(48);
		die2Iv.setPreserveRatio(true);
		die2Iv.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				KNTAppFactory.getDicePresenter().handleMouseClick(me, false);
			}
		});
		
		// Roll button
		rollBtn = new Button("Roll");
			
		endTurnBtn = new Button("End Turn");
		
		// Add all controls
		getChildren().addAll(die1Iv, die2Iv, rollBtn, endTurnBtn);
	}

	public void setDice(Die d1, Die d2) {
		die1Iv.imageProperty().set(d1.getImage());
		die2Iv.imageProperty().set(d2.getImage());
	}
	
	public void setEndTurnButtonLbl(String labelTxt){ endTurnBtn.setText(labelTxt);}
	public ImageView getDie1()	{	return die1Iv;	}
	public ImageView getDie2()	{	return die2Iv;	}
	public Button getEndTurnBtn()	{	return endTurnBtn;	}
	public Button getRollBtn() {		return rollBtn;		}
	public void setDie1(ImageView die)	{	die1Iv = die;	}
	public void setDie2(ImageView die)	{	die2Iv = die;	}
	public void setEndTurnBtn(Button but)	{	endTurnBtn = but;	}
	public void setRollBtn(Button rollBtn) {	this.rollBtn = rollBtn;		}

	public void hideDie2(boolean hide) {
		//if (hide) {
		
		die2Iv.setVisible(!hide);
		///die2Iv.setManaged(false);
			
		/*	getChildren().remove(die2Iv);
		}
		else {
			getChildren().add(1, die2Iv);
		}*/
	}
}
