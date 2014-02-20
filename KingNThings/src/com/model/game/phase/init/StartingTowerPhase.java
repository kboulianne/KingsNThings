package com.model.game.phase.init;

import javafx.scene.image.Image;

import com.main.KNTAppFactory;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;

public class StartingTowerPhase extends AbstractPhaseStrategy {

	public StartingTowerPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Init Phase: End of Starting Tower Phase");
		gv.getCurrentActionLbl().setText("Place Tower");	
		KNTAppFactory.getBoardPresenter().getView().addStartTowerHandler();
		
		KNTAppFactory.getSidePanePresenter().getView().showArbitraryView("Place a tower on an owned hex", 
				new Image("view/com/assets/pics/gamepieces/forts/tower.jpeg"));
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
