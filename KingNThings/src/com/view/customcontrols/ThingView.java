package com.view.customcontrols;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Game;
import com.model.Player;
import com.model.SpecialCharacter;
import com.model.Thing;
import com.model.Treasure;
import com.presenter.Util;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// TODO create object pool to avoid recreating image views.
// create and add image views

public class ThingView extends StackPane{
	private int size;
	private Thing thing;
	private Rectangle selectRect;
	private Rectangle coloredRect;
	private ImageView img;
	
	public ThingView(int frameSize, Thing t){
		size=frameSize;
		thing=t;
		buildComponent();
	}

	private void buildComponent() {
		Rectangle borderRect = new Rectangle();
		borderRect.setX(0);
		borderRect.setY(0);
		borderRect.setWidth(size);
		borderRect.setHeight(size);
		borderRect.setArcWidth(20);
		borderRect.setArcHeight(20);
		borderRect.setFill(Color.WHITE);

		coloredRect = new Rectangle();
		coloredRect.setX(0);
		coloredRect.setY(0);
		coloredRect.setWidth(size - 1);
		coloredRect.setHeight(size - 1);
		coloredRect.setArcWidth(20);
		coloredRect.setArcHeight(20);

		Image im = null;
		if(thing.isFacedDown()){
			coloredRect.setFill(Color.BLACK);
			im= Game.FACE_DOWN_THING_IMAGE;
		}else{
			coloredRect.setFill(thing.getColor());
			im=thing.getImage();
		}
		
		img = new ImageView(im);
		img.setFitWidth(size - 7);
		img.setFitHeight(size - 7);
		img.setPreserveRatio(false);
		img.setSmooth(true);
		img.setCache(true);

		selectRect = new Rectangle();
		selectRect.setX(0);
		selectRect.setY(0);
		selectRect.setWidth(size);
		selectRect.setHeight(size);
		selectRect.setArcWidth(20);
		selectRect.setArcHeight(20);
		selectRect.getStyleClass().add("thing");
		if(thing.isSelected()){
			selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.0));
		}else{
			selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.5));
		}
		
		getChildren().addAll(borderRect, coloredRect, img, selectRect);
	}
	
	private void refreshView(){
		Image im = null;
		if(thing.isFacedDown()){
			coloredRect.setFill(Color.BLACK);
			im= Game.FACE_DOWN_THING_IMAGE;
		}else{
			coloredRect.setFill(thing.getColor());
			im=thing.getImage();
		}
			
		if(thing.isSelected()){
			selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.0));
		}else{
			selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.5));
		}
		img.setImage(im);
		//this.
	}
	
	void setDefaultHandler(){
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				Util.playClickSound();
				if(thing.getHexLocation() == -1)	thing.setSelected(!thing.isSelected());
				//refresh view
				if(thing.isSelected()){
					selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.0));
					KNTAppFactory.getSidePanePresenter().showThingDetailsFor(thing);
				}else{
					selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.5));
				}		
			}
		});
	}
	
	void setRecruitingThingsHandler()	{
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>()	{
			public void handle(MouseEvent m)	{
				Util.playClickSound();
				if(thing.getHexLocation() == -1)	thing.setSelected(!thing.isSelected());
				
				if(thing.isSelected())	{
					selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.0));
				}else{
					selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.5));
				}
			}
		});
	}
	
	void setMovementHandler(){
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				Util.playClickSound();
				thing.setSelected(!thing.isSelected());
				if(thing.isSelected()){
					selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.0));
				}else{
					//GameService.getInstance().getGame().getLastSelectedCreaturesOfCurrentPlayerBlock().remove(thing);
					selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.5));
				}
				
				if(thing.getOwner().equals(GameService.getInstance().getGame().getCurrentPlayer().getName()))	{
					KNTAppFactory.getBoardPresenter().handleMoveSetupForThing(thing);
				}
			}
		});
	}
	
	void setDoNothingHandler(){
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {			
				//Do nothing
				Util.log("Do Nothing");
			}
		});
	}

	void setExchangeThingHandler() {
		
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {	

				if(thing.isSelected()){
					KNTAppFactory.getSidePanePresenter().getView().showArbitraryView("Exchange things by clicking the rack\n"
							   + "     Exchange only once per thing", Game.CROWN_IMAGE);
				} else {
					Util.playClickSound();
					Game game = GameService.getInstance().getGame();	
					Thing t = game.getCup().getRandomThing();
					game.moveThingFromCupToPlayer(t, game.getCurrentPlayer());
					KNTAppFactory.getSidePanePresenter().showThingDetailsFor(t);
					game.getCurrentPlayer().getBlock().removeThing(thing);
					game.getCup().addThing(thing);
					thing = t;
					thing.setSelected(true);
					thing.setFacedDown(false);
					refreshView();
				}
			}
		});
		
	}
	
	public void setExchangeTreasureHandler() {
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {	
				Util.playClickSound();
				Game game = GameService.getInstance().getGame();	

				game.getCurrentPlayer().getBlock().removeThing(thing);
				game.getCup().addThing(thing);
				
				game.getCurrentPlayer().addGold(((Treasure) thing).getValue());
				KNTAppFactory.getPlayerInfoPresenter().getView().updateGold(game.getCurrentPlayer());
				KNTAppFactory.getPlayerInfoPresenter().getView().setRackTreasureExchangeHandler(game.getCurrentPlayer());
				refreshView();
			}
		});		
	}

	public void setChooseSpecialCharToRecruitHandler() {
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				Util.playClickSound();
				thing.setSelected(true);
				KNTAppFactory.getSidePanePresenter().getView().showSpecialCharRecruitment((SpecialCharacter) thing);
			}
		});
	}

	public void setApplyHitsForBattleHandler() {
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				KNTAppFactory.getBattlePresenter().handleThingPressed(thing);
			}
		});
	}
	
	
	public void setSendSpecialCharacterBackToCupHandler() {
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				Util.playClickSound();
				Game game = GameService.getInstance().getGame();
				Player currPlay = game.getCurrentPlayer();
				game.getCup().addThing(currPlay.removeSpecialCharacter((SpecialCharacter)thing));
				KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(currPlay);
				KNTAppFactory.getSidePanePresenter().getView().showSpecialCharRecruitment(currPlay.getName(), currPlay.getAllOwnedSpecialChar());
			}
		});
	}
}
