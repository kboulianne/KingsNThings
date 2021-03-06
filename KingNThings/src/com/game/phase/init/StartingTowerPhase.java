package com.game.phase.init;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import com.game.phase.AbstractPhaseStrategy;
import com.game.phase.GamePlay;
import com.model.Game;
import com.model.Player;
import com.util.Util;

import static com.main.KNTAppFactory.*;

public class StartingTowerPhase extends AbstractPhaseStrategy {

	public StartingTowerPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		Util.log("Init Phase: Start of Starting Tower Phase");	
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(false);
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				getGamePresenter().endTurn();
			}
		});
		
	}
	
	@Override
	public void turnStart(Game game) {
		super.turnStart(game);
		
		getBoardPresenter().getView().addStartTowerHandler();
		
		getSidePanePresenter().getView().showArbitraryView("Place a tower on an owned hex", 
				new Image("view/com/assets/pics/gamepieces/forts/tower.jpeg"));
		
		if(Util.AUTOMATE){
			Util.log("Automated");
			if(game.getCurrentPlayer().getId().equals(Player.PlayerId.ONE))
				getBoardPresenter().handleStartingTowerHexClick(23);
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.TWO))
				getBoardPresenter().handleStartingTowerHexClick(28);
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.THREE))
				getBoardPresenter().handleStartingTowerHexClick(32);
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.FOUR))
				getBoardPresenter().handleStartingTowerHexClick(19);
		}
	}

	@Override
	public void turnEnd(Game game) {
		
	}

	@Override
	public void phaseEnd(Game game) {
		Util.log("Init Phase: End of Starting Tower Phase");
		getBoardPresenter().getView().addDefaultHandler();
		
		getSidePanePresenter().getView().clearDetailsView();
	}

	@Override
	public String getActionText() {
		return "Place Tower";
	}

}
