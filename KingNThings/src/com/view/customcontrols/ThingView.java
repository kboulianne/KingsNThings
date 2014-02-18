package com.view.customcontrols;

import com.game.services.GameService;
import com.main.KNTAppFactory;
import com.model.Thing;
import com.model.game.Game;
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
	ImageView img;
	
	public ThingView(int frameSize, Thing t){
		size=frameSize;
		thing=t;
		buildComponent();
	}

	protected void buildComponent() {
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
	
	public void refreshView(){
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
	
	public void setDefaultHandler(){
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				Util.log("thing default handler");
				thing.setSelected(!thing.isSelected());
				//refresh view
				if(thing.isSelected()){
					selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.0));
				}else{
					//GameService.getInstance().getGame().getLastSelectedCreaturesOfCurrentPlayerBlock().remove(thing);
					selectRect.setFill(new Color(0.0, 0.0, 0.0, 0.5));
				}
				KNTAppFactory.getSidePanePresenter().showThingDetailsFor(thing);
			}
		});
	}
	
	public void setMovementHandler(){
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				
				if(thing.getOwner().equals(GameService.getInstance().getGame().getCurrentPlayer().getName()))	{
					KNTAppFactory.getBoardPresenter().handleMoveSetupForThing(thing);
				}
				//img.fireEvent(new ThingEvent(thing));
			}
		});
	}
	
	public void setCupHandler(){
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				Util.log("thing cup handler");
				//KNTAppFactory.getSidePanePresenter().showThingDetailsFor(thing);
				//KNTAppFactory.getPlayerInfoPresenter().handleRackClick(thing);
			}
		});
		
	}
	//
	//KNTAppFactory.getPopupPresenter().dismissPopup();
	
	public void setRackHandler(){
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {			
				KNTAppFactory.getPlayerInfoPresenter().handleRackClick(thing);
			}
		});
	}

	public void setExchangeThingHandler() {
		// TODO Auto-generated method stub
		selectRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {			
				if(thing.isSelected()){
					KNTAppFactory.getSidePanePresenter().getView().showArbituaryView("Exchange things by clicking the rack\n"
							   + "     Exchange only once per thing", Game.CROWN_IMAGE);
				}else{
					Game game = GameService.getInstance().getGame();
					
					thing.setSelected(false);
					Thing t = game.getCup().getRandomCreature();
					t.setSelected(true);
					game.moveThingFromCupToPlayer(t, game.getCurrentPlayer());
					KNTAppFactory.getSidePanePresenter().showThingDetailsFor(t);
					game.getCurrentPlayer().getBlock().removeThing(thing);
					thing = t;
					refreshView();
				}
				//TODO if exchanged start pos things

			}
		});
		
	}
}
