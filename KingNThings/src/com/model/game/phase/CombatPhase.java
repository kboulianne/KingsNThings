/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.main.KNTAppFactory;
import com.presenter.Util;

/**
 *
 * @author kurtis
 */
public class CombatPhase extends AbstractPhaseStrategy {

	public CombatPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Combat Phase");

		gv.getCurrentActionLbl().setText("Combat Phase");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				context.endTurn();
			}
		});
	}

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Combat Phase");
	}

	@Override
	public void turnStart() {
		super.turnStart();
		Util.log("Game Phase: Logic for " + game.getCurrentPlayer().getName());
		
		VBox vbox = new VBox();
		Label title = new Label("Battle:");
		
		vbox.getChildren().addAll(title);
		
		KNTAppFactory.getPopupPresenter().getView().show(vbox);
		//context.endTurn();
	}
	
	@Override
	public void turnEnd() {
		//GameService.getInstance().endTurn(this);
		
	}
}
