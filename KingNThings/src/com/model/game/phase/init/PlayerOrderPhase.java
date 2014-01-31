/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase.init;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Hex;
import com.model.game.Game;
import com.model.game.phase.AbstractPhaseStrategy;
import com.model.game.phase.GamePlay;
import com.presenter.Util;
import com.view.GameView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


/**
 *
 * @author kurtis
 */
//TODO needs a different strategy implementation one with simply executePhase()
public class PlayerOrderPhase extends AbstractPhaseStrategy<Object> {
	
	Game game;
	GameView gv;
	


	public PlayerOrderPhase(GamePlay contxt) {
		super(contxt);
	}

	@Override
	public void phaseStart() {
		System.out.println("Init Phase: Start of Player Order Phase");
		context.clearRolls();
		
<<<<<<< HEAD

		game =  GameService.getInstance().getGame();
		gv = KNTAppFactory.getGamePresenter().getView();
		
		//top label
		gv.getCurrentActionLbl().setText("Roll the Dice");
=======
		Util.log("Kurtis See player order phase --> phase start");
		
		// Hey Kurtis want to get the game variable and associated Game Views but they are null
		/// i believe i started the game wrong, also we need to connect the button I added labeled 'Finished Turn'
		KNTAppFactory.getGamePresenter().getView();
		Game game =  GameService.getInstance().getGame();
		GameView gv = KNTAppFactory.getGamePresenter().getView();
		
		//top label
		gv.getCurrentActionLbl().setText("Player Order Phase");
		
>>>>>>> 39bce22bd6e6d09d62e764b04157b10d88bdbc6f
		
		//detail pane
		
		//on clicks
		//GameService.getInstance().endTurn();
		//game.nextPlayer();
		
		game.getBoard().setFaceDown(true);
		for(Hex h: game.getBoard().getHexes())
			KNTAppFactory.getBoardpresenter().getView().paintHex(h);
		
		KNTAppFactory.getBoardpresenter().getView().setOnMouseClicked(null);
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getFinishTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				turnEnd();
			}
		});		
	}

	/*@Override
	public void executePhase(Object input) {
		Game game = GameService.getInstance().getGame();
		

		// Add the rolled dice total to the context for later use.
		context.addPlayerRoll(game.diceTotal(), game.getCurrentPlayer());
//	//TODO handle case where dice totals are equal.
//	phase.getRolls().put(game.diceTotal(), game.getCurrentPlayer());
//	
		System.out.println("Added roll total " + game.diceTotal()
				+ " for " + game.getCurrentPlayer().getName());
//	
//	
		// Move to postExecute in AbstractPhaseImpl?
//	game.nextPlayer();
	}
 */

	@Override
	public void phaseEnd() {
		// Updates the player order.
		
		//game.setPlayerOrder(context.getPlayersHighToLow());
		
		Util.log("Init Phase: End of Player Order Phase");
		new StartingPosPhase(context).phaseStart();
		
	}

	@Override
	public void turnStart() {
<<<<<<< HEAD
		// TODO Auto-generated method stub
		//top label
		
		super.turnStart();
		
=======
		Game game =  GameService.getInstance().getGame();
		GameView gv = KNTAppFactory.getGamePresenter().getView();
			
		//top label
		gv.getCurrentPlayerLbl().setText("Sir " + game.getCurrentPlayer().getName() + "'s Turn: ");		
>>>>>>> 39bce22bd6e6d09d62e764b04157b10d88bdbc6f
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		GameService.getInstance().endTurn(this);
=======
		System.out.println("Init Phase: Player Order Phase - turnEnd()");
>>>>>>> 39bce22bd6e6d09d62e764b04157b10d88bdbc6f
	}

	@Override
	public void addHandlers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeHandlers() {
		// TODO Auto-generated method stub
		
	}

}
