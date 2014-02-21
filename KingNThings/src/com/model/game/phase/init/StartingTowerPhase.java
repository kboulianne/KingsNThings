package com.model.game.phase.init;

import javafx.scene.image.Image;

import com.main.KNTAppFactory;
import com.model.Player;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;

public class StartingTowerPhase extends AbstractPhaseStrategy {

	public StartingTowerPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Init Phase: Start of Starting Tower Phase");
		gv.getCurrentActionLbl().setText("Place Tower");	
		
		
	}
	
	@Override
	public void turnStart() {
		super.turnStart();
		
		KNTAppFactory.getBoardPresenter().getView().addStartTowerHandler();
		
		KNTAppFactory.getSidePanePresenter().getView().showArbitraryView("Place a tower on an owned hex", 
				new Image("view/com/assets/pics/gamepieces/forts/tower.jpeg"));
		
		if(Util.AUTOMATE){
			Util.log("Automated");
			if(game.getCurrentPlayer().getId().equals(Player.PlayerId.ONE))
				KNTAppFactory.getBoardPresenter().handleStartingTowerHexClick(23);
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.TWO))
				KNTAppFactory.getBoardPresenter().handleStartingTowerHexClick(28);
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.THREE))
				KNTAppFactory.getBoardPresenter().handleStartingTowerHexClick(32);
			else if(game.getCurrentPlayer().getId().equals(Player.PlayerId.FOUR))
				KNTAppFactory.getBoardPresenter().handleStartingTowerHexClick(19);
		}
	}

	@Override
	public void turnEnd() {
		
	}

	@Override
	public void phaseEnd() {
		Util.log("Init Phase: End of Starting Tower Phase");
		KNTAppFactory.getBoardPresenter().getView().addDefaultHandler();
		
		KNTAppFactory.getSidePanePresenter().getView().clearDetailsView();
	}

}
