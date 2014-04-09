package com.game.phase.init;

import com.game.phase.AbstractPhaseStrategy;
import com.game.phase.GamePlay;
import com.model.Game;
import com.util.Util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import static com.main.KNTAppFactory.*;

/**
 *
 * @author kurtis
 */
//TODO needs a different strategy implementation one with simply executePhase()
public class PlayerOrderPhase extends AbstractPhaseStrategy {
		
	public PlayerOrderPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart(Game game) {
		super.phaseStart(game);
		
		Util.playMusic();
		Util.log("Init Phase: Start of Player Order Phase");

		getBoardPresenter().getView().setOnMouseClicked(null);
		
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(true);
		
		Button rollBtn = getDicePresenter().getView().getRollBtn();
		rollBtn.setOnAction(new EventHandler<ActionEvent>()	{
			@Override
			public void handle(ActionEvent arg0) {
				getDicePresenter().roll();
				getGamePresenter().endTurn();
			}
		});
		rollBtn.setVisible(true);
	}

	@Override
	public void turnStart(Game game) {
		//top label
		super.turnStart(game);
		getGamePresenter().getView().setGame(game, getActionText());
	}

	@Override
	public void turnEnd(Game game) {
		super.turnEnd(game);

		// Notify UI
		getGamePresenter().getView().setGame(game, getActionText());
	}
	
	@Override
	public void phaseEnd(Game game) {
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(false);
		Button rollBtn = getDicePresenter().getView().getRollBtn();
		rollBtn.setOnAction(null);
		rollBtn.setDisable(true);
	}

	@Override
	public String getActionText() {
		return "Roll the dice";
	}
}
