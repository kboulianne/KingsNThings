/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.game.phase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Fort;
import com.model.GamePiece;
import com.model.Hex;
import com.model.IncomeCounter;
import com.model.Player;
import com.model.SpecialCharacter;
import com.model.game.Game;
import com.presenter.Util;
import com.view.GameView;

/**
 *
 * @author kurtis
 */
public class GoldCollectPhase extends AbstractPhaseStrategy {

	public GoldCollectPhase(GamePlay context) {
		super(context);
	}

	@Override
	public void phaseStart() {
		Util.log("Game Phase: Start of Gold Collection Phase");

		gv.getCurrentActionLbl().setText("Gold Collection");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(true);
		Button rollBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getRollBtn();
		rollBtn.setDisable(true);
	}
	
	@Override
	public void phaseEnd() {
		Util.log("Game Phase: End of Gold Collection Phase");
		
		Button finishBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getEndTurnBtn();
		finishBtn.setDisable(true);
		Button rollBtn = KNTAppFactory.getGamePresenter().getDicePresenter().getView().getRollBtn();
		rollBtn.setDisable(true);
		
		
		KNTAppFactory.getPopupPresenter().getView().getCloseBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				KNTAppFactory.getPopupPresenter().getView().dismiss();
			}
		});
	}

	@Override
	public void turnStart() {
		// TODO Auto-generated method stub
		super.turnStart();
		
		Util.log("Game Phase: Logic for " + GameService.getInstance().getGame().getCurrentPlayer().getName());
		
		int hexGold = 0;
		int fortGold = 0;
		int counterGold = 0;
		int specCharGold = 0;
		int totalGold = 0;
		Game game = GameService.getInstance().getGame();
		Player player = game.getCurrentPlayer();
		
		for (Hex h : game.getBoard().getHexes()) {
			if ((h != null) && (h.getOwner() == player)) {
				hexGold++;
				if(h.getMiscItems() != null)	{
					for (GamePiece g : h.getMiscItems()) {
						if(g instanceof Fort)	fortGold ++; //= g.fortValue;
						else if(g instanceof IncomeCounter)	counterGold += ((IncomeCounter)g).getValue();
						else if(g instanceof SpecialCharacter)	specCharGold ++;
					}
				}
			}
		}
		
		hexGold = (int) Math.ceil(hexGold/2);
		totalGold += (hexGold + fortGold + counterGold + specCharGold);
		
		AnchorPane ap = new AnchorPane();
		ap.setPrefSize(500, 500);
		ImageView im = new ImageView("view/com/assets/pics/gold.png");
		im.setFitWidth(500);
		im.setPreserveRatio(true);
		ap.getChildren().add(im);
		AnchorPane.setTopAnchor(im, 60.0);
		Label hexes = new Label("Hex Income:  " + hexGold);
		Label forts = new Label("Fort Income:  " + fortGold);
		Label counters = new Label("Counter Income:  " + counterGold);
		Label specChars = new Label("Character Income:  " + specCharGold);
		Label divider = new Label("------------------------");
		Label total = new Label("Total Income:  " + totalGold);
		VBox labels = new VBox();
		labels.setAlignment(Pos.BASELINE_RIGHT);
		labels.getChildren().addAll(hexes, forts, counters, specChars, divider, total);
		ap.getChildren().add(labels);
		AnchorPane.setLeftAnchor(labels, 120.0);
		AnchorPane.setTopAnchor(labels, 420.0);

		VBox popupVbox = new VBox();
		popupVbox.getChildren().addAll(ap);
		popupVbox.setAlignment(Pos.CENTER);
		popupVbox.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);


		KNTAppFactory.getPopupPresenter().getView().show(popupVbox, "Gold Income:");
		KNTAppFactory.getPopupPresenter().getView().getCloseBtn().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				KNTAppFactory.getPopupPresenter().getView().dismiss();
				context.endTurn();
			}
		});
		
		player.addGold(totalGold);
	}

	@Override
	public void turnEnd() {
		
	}

}
