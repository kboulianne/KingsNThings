package com.model.game.phase.init;

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
	}

	@Override
	public void turnEnd() {
		
	}

	@Override
	public void phaseEnd() {
		Util.log("Init Phase: End of Starting Tower Phase");
		KNTAppFactory.getBoardPresenter().getView().addDefaultHandler();
	}

}
