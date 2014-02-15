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
	private ImageView img;
	
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

		final Rectangle coloredRect = new Rectangle();
		coloredRect.setX(0);
		coloredRect.setY(0);
		coloredRect.setWidth(size - 1);
		coloredRect.setHeight(size - 1);
		coloredRect.setArcWidth(20);
		coloredRect.setArcHeight(20);
		//coloredRect.setFill(thing.getColor());

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
		img.getStyleClass().add("thing");

		/*// TODO Clean me up
		img.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent me) {
				// Fire custom event on mouse clicked
				img.fireEvent(new ThingEvent(thing));
			}
		});*/

		getChildren().addAll(borderRect, coloredRect, img);
	}
	
	/*public void setThingHandler(EventHandler<ThingEvent> thingHandler){
		img.addEventFilter(ThingEvent.THING_CLICKED, thingHandler);
	}*/
	
	public void setDefaultHandler(){
		img.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				Util.log("thing default handler");
				KNTAppFactory.getSidePanePresenter().showThingDetailsFor(thing);
			}
		});
	}
	
	public void setMovementHandler(){
		img.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
		img.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				//KNTAppFactory.getSidePanePresenter().showThingDetailsFor(thing);
				//KNTAppFactory.getPlayerInfoPresenter().handleRackClick(thing);
			}
		});
		
	}
	//
	//KNTAppFactory.getPopupPresenter().dismissPopup();
	
	public void setRackHandler(){
		img.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {			
				KNTAppFactory.getPlayerInfoPresenter().handleRackClick(thing);
			}
		});
	}

	public ImageView getImg() {
		return img;
	}

	public void setImg(ImageView img) {
		this.img = img;
	}
}
