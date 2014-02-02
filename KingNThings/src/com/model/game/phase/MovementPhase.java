/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.DesertCreature;
import com.model.JungleCreature;
import com.model.MountainCreature;
import com.model.game.Game;
import com.presenter.Util;
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public class MovementPhase extends AbstractPhaseStrategy {

	Game game;
	GameView gv;
	
	public MovementPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Movement Phase");
		
		game =  GameService.getInstance().getGame();
		
		gv = KNTAppFactory.getGamePresenter().getView();
		gv.getCurrentActionLbl().setText("Movement Phase");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				turnEnd();
			}
		});
		
		
		
		KNTAppFactory.getThingdetailspresenter().getcView().getMoveButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				KNTAppFactory.getBoardpresenter().handleMoveButtonClick();
				
			}
		});
		
		// for testing
		//Player 1
		//Stack 1
		game.getBoard().getHexes().get(2).addCreatToArmy(new DesertCreature("olddragon"), game.getCurrentPlayer());
		game.getBoard().getHexes().get(2).addCreatToArmy(new DesertCreature("giantspider"), game.getCurrentPlayer());
		game.getBoard().getHexes().get(2).addCreatToArmy(new JungleCreature("elephant"), game.getCurrentPlayer());
		game.getBoard().getHexes().get(2).addCreatToArmy(new MountainCreature("brownknight"), game.getCurrentPlayer());
		game.getBoard().getHexes().get(2).addCreatToArmy(new MountainCreature("giant"), game.getCurrentPlayer());
		game.getBoard().getHexes().get(2).addCreatToArmy(new MountainCreature("dwarves"), game.getCurrentPlayer());
		//Stack 2
		game.getBoard().getHexes().get(9).addCreatToArmy(new DesertCreature("skeletons"), game.getCurrentPlayer());
		game.getBoard().getHexes().get(9).addCreatToArmy(new JungleCreature("watusi"), game.getCurrentPlayer());
		game.getBoard().getHexes().get(9).addCreatToArmy(new MountainCreature("goblins"), game.getCurrentPlayer());
		game.getBoard().getHexes().get(9).addCreatToArmy(new MountainCreature("ogre"), game.getCurrentPlayer());
		

	
		
	}

	/*
	@Override
	public void executePhase(Object input) {
		Util.log("Game Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
	}
	*/

	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Movement Phase");
//		new CombatPhase(context).phaseStart();
	}

	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		
		super.turnStart();
		
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
		GameService.getInstance().endTurn(this);
		
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
