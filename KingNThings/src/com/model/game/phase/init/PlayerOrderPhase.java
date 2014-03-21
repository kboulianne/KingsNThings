package com.model.game.phase.init;

import com.main.NetworkedMain;
import com.model.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

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
	public void phaseStart() {
		super.phaseStart();
		
		Util.playMusic();
		Util.log("Init Phase: Start of Player Order Phase");
//		game.clearRolls();
		
		//top label
		getGamePresenter().getView().getCurrentActionLbl().setText("Roll the Dice");
		
		game.getBoard().setFaceDown(true);
		// Update the view
		getBoardPresenter().getView().setBoard(game.getBoard());
		getBoardPresenter().getView().setOnMouseClicked(null);
		
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(true);
		
		Button rollBtn = getDicePresenter().getView().getRollBtn();
		rollBtn.setOnAction(new EventHandler<ActionEvent>()	{
			@Override
			public void handle(ActionEvent arg0) {
				getDicePresenter().roll();
				context.endTurn();
			}
		});
		rollBtn.setVisible(true);
		
		//TODO: Update?
	}

	@Override
	public void turnStart() {
		//top label
		super.turnStart();
		getGamePresenter().getView().setGame(game);
//		getSidePanePresenter().getView().showRolls(game.getRolls());
	}

	@Override
	public void turnEnd() {
		super.turnEnd();
//		game.addPlayerRoll(game.diceTotal(), game.getCurrentPlayer());				
//		Util.log("Added roll total " + game.diceTotal()
//				+ " for " + game.getCurrentPlayer().getName());
		
		// Notify UI
		getGamePresenter().getView().setGame(game);
	}
	
	@Override
	public void phaseEnd() {
		// Updates the player order.
		// DEACTIVATED =======================================
//		game.setPlayerOrder(context.getPlayersHighToLow());
		// ===================================================
		
//		Util.log("Init Phase: End of Player Order Phase");
		Button finishBtn = getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(false);
		Button rollBtn = getDicePresenter().getView().getRollBtn();
		rollBtn.setOnAction(null);
	}
}
