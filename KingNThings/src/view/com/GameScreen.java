package view.com;

import controller.com.GameScreenCntrl;
import controller.com.Main;
import controller.com.Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.BeanPathAdapter;
import model.com.game.Game;

public class GameScreen {
	
    private GameScreenCntrl ctrl;
    
	Canvas playingArea;
	VBox detailsBox;
	HBox currentPlayerInfoBox;
	StackPane rootStackPane;
	VBox popupVbox;
	
	static final double HEX_WIDTH = 100.0; 
	static final double HEX_HEIGHT = HEX_WIDTH *0.8;
	double[][] hexCenterPoints;
	double[][] choosenMapping;
		
	int lastHexSelected = -1;
	Rectangle lastThingRect = null;
	
	static final double[][] MAPPING_37_TILES = new double[][]{{4.0,7.0},{4.0,5.0},{5.0,6.0},{5.0,8.0},{4.0,9.0},
			{3.0,8.0},{3.0,6.0},{3.0,4.0},{4.0,3.0},{5.0,4.0},{6.0,5.0},{6.0,7.0},{6.0,9.0},
			{5.0,10.0},{4.0,11.0},{3.0,10.0},{2.0,9.0},{2.0,7.0},{2.0,5.0},{2.0,3.0},{3.0,2.0},
			{4.0,1.0},{5.0,2.0},{6.0,3.0},{7.0,4.0},{7.0,6.0},{7.0,8.0},{7.0,10.0},{6.0,11.0},
			{5.0,12.0},{4.0,13.0},{3.0,12.0},{2.0,11.0},{1.0,10.0},{1.0,8.0},{1.0,6.0},{1.0,4.0}
	};
	static final double[][] MAPPING_19_TILES = new double[][]{{3.0,5.0},{3.0,3.0},{4.0,4.0},
			{4.0,6.0},{3.0,7.0},{2.0,6.0},{2.0,4.0},{2.0,2.0},{3.0,1.0},{4.0,2.0},{5.0,3.0},
			{5.0,5.0},{5.0,7.0},{4.0,8.0},{3.0,9.0},{2.0,8.0},{1.0,7.0},{1.0,5.0},{1.0,3.0}
	};
        // For now
	private static final Image HEX_IMAGE = new Image("view/com/assets/pics/tiles/desert.png");
	
	String[] gamePhases = {"Gold Collection","Recruiting Characters", "Recruiting Things",
						"Random Event Phase", "Movement Phase", "Combat Phase", "Construction Phase",
						"Special Powers Phase", "Changing Player Order"};
	
	Label playerLbl;
	// for now
	ImageView die1Im;
	ImageView die2Im;
	String currentPlayersName = "Frank";
	String otherPlayerName1 = "Joe";
	String otherPlayerName2 = "Roxanne";
	String otherPlayerName3 = "Henry";
	
	public void show(){
		
		//GameStatus
		AnchorPane gameStatus = new AnchorPane();
		gameStatus.setId("gameStatus");
		
		final Label turn = new Label("Sir "+otherPlayerName3+"'s Turn: Roll Dice");
		turn.getStyleClass().add("title");
		gameStatus.getChildren().add(turn);
		AnchorPane.setLeftAnchor(turn, 0.0);
		AnchorPane.setTopAnchor(turn, 5.0);
		
		// TODO bind dice values from model
//		die1 = new Die();
//		die2 = new Die();
		
		die1Im = new ImageView(/*die1.getImage()*/);
		die1Im.setFitWidth(48); 
		die1Im.setPreserveRatio(true);
		
		die2Im = new ImageView(/*die2.getImage()*/);
		die2Im.setFitWidth(48); 
		die2Im.setPreserveRatio(true);
		
		Button button = new Button("Roll");
		
		HBox topRBox = new HBox();
		topRBox.setId("topRBox");
		topRBox.getChildren().addAll(die1Im, die2Im, button);
		AnchorPane.setRightAnchor(topRBox, 0.0);
		AnchorPane.setTopAnchor(topRBox, 0.0);
		gameStatus.getChildren().add(topRBox);
		
		// Canvas / Playing Area
		playingArea = new Canvas(1280*0.5-10,HEX_HEIGHT*7.2);
		playingArea.setId("playingArea");
		playingArea.getStyleClass().add("border");
		GraphicsContext gc = playingArea.getGraphicsContext2D();
		gc.clearRect(0, 0,playingArea.getWidth(), playingArea.getHeight());	
		
		ScrollPane playingAreaScrollPane = new ScrollPane();
		playingAreaScrollPane.setPrefSize(1280*0.5, HEX_HEIGHT*8);
		playingAreaScrollPane.setContent(playingArea);
		
		choosenMapping = MAPPING_37_TILES;
		hexCenterPoints = new double[choosenMapping.length][2];
		for(int i = 0; i<choosenMapping.length;i++){
			double xOffset = choosenMapping[i][0]*0.75*HEX_WIDTH-40.0;
			double yOffset = choosenMapping[i][1]*0.5*HEX_HEIGHT-30.0;
			hexCenterPoints[i][0]=xOffset+(HEX_WIDTH*0.5);
			hexCenterPoints[i][1]=yOffset+(HEX_HEIGHT*0.5);
			paintHex(i,Color.DARKGRAY);
		}
		
		// SIDE PANE
		// Details Box
		detailsBox = new VBox(); 
		detailsBox.setAlignment(Pos.CENTER);
		detailsBox.setId("detailsBox");
		detailsBox.setMinHeight(HEX_HEIGHT*7);
		VBox sidePane = new VBox();
		sidePane.setMinWidth(1280*0.5-20);
		//Opponents
		HBox otherPlayerInfo = new HBox();
		otherPlayerInfo.setId("otherPlayerInfo");
		otherPlayerInfo.getChildren().add(new Label("Opponents:"));	
		paintPlayerName(otherPlayerName1, Color.YELLOW, otherPlayerInfo);
		paintPlayerName(otherPlayerName2, Color.RED, otherPlayerInfo);
		paintPlayerName(otherPlayerName3, Color.BLUE, otherPlayerInfo);
		otherPlayerInfo.setAlignment(Pos.TOP_CENTER);
		sidePane.getChildren().addAll(otherPlayerInfo, detailsBox);
		
		HBox centerBox = new HBox();
		centerBox.getChildren().addAll(playingAreaScrollPane,sidePane);
		
		// Player Info
		currentPlayerInfoBox = new HBox();
		currentPlayerInfoBox.setId("playerInfo");
		VBox currentPlayerNameAndGold = new VBox();
		currentPlayerNameAndGold.setAlignment(Pos.CENTER);
		paintPlayerName(currentPlayersName, Color.GREEN, currentPlayerNameAndGold);
		currentPlayerNameAndGold.getChildren().add(new Label("Gold: 50"));
		currentPlayerInfoBox.getChildren().add(currentPlayerNameAndGold);
		for(int i =0; i<7;i++)
			paintThing(i);
		VBox rootVBox = new VBox();
		rootVBox.getStyleClass().add("border");
		rootVBox.getChildren().addAll(gameStatus, centerBox, currentPlayerInfoBox);
		rootVBox.setAlignment(Pos.TOP_CENTER);
		
		// Stack
		rootStackPane = new StackPane();
		rootStackPane.getChildren().add(rootVBox);
		
		Scene scene = new Scene(rootStackPane,1280,800);
		scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
		scene.getStylesheets().add("view/com/assets/docs/gameScreen.css");
		
		Stage stage = Main.getStage();
		stage.setScene(scene);
		stage.show();
		
		//new GameScreenCntrl(playingArea, button);
		
		// to be moved to controller
		playingArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double clickPtX = event.getX();
				double clickPtY = event.getY();
				for(int i = 0; i<hexCenterPoints.length;i++){
					if(Util.distanceBtwTwoPts(clickPtX, clickPtY, hexCenterPoints[i][0], hexCenterPoints[i][1] )<HEX_WIDTH*0.30){
						onHexSelected(i);
						return;
					}
				}
			}
		});
		
		button.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
			    handleDieRoll(event);
			}
		});
	}
	
	// Pop-up Functions
	public void popup( Node content){	
		if (rootStackPane.getChildren().size() == 1){			
			popupVbox = new VBox();
			popupVbox.getStyleClass().add("popup");
			popupVbox.getChildren().addAll(content);
			popupVbox.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
			rootStackPane.getChildren().add(popupVbox);
		}
	}
	public void dismissPopup(){
		rootStackPane.getChildren().remove(popupVbox);
	}
	public void popupWithTitleAndCloseButton(String title, Node content){
		if (rootStackPane.getChildren().size() == 1){		
			AnchorPane ap = new AnchorPane();
			
			Label label = new Label(title);
			label.getStyleClass().add("title");
			ap.getChildren().add(label);
			AnchorPane.setLeftAnchor(label, 0.0);
			
			Button button = new Button("Close");
			ap.getChildren().add(button);
			AnchorPane.setRightAnchor(button, 0.0);
			
			final VBox popupVbox = new VBox();
			popupVbox.getStyleClass().add("popup");
			popupVbox.getChildren().addAll(ap, content);
			popupVbox.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
			
			rootStackPane.getChildren().add(popupVbox);
			
			button.setOnAction(new EventHandler<ActionEvent>() {		
				@Override
				public void handle(ActionEvent arg0) {
					dismissPopup();
					rootStackPane.getChildren().remove(popupVbox);
				}
			});
		}
	}
	
	// paint function to be moved to associated classes
	
	public void paintPlayerName(final String name, Color c, Pane pane){
		Label playerLbl = new Label("Sir "+name);
		playerLbl.getStyleClass().add("playerName"); 
		Circle circle = new Circle();
		circle.setRadius(6);
		circle.setFill(c);   
		HBox playerBox = new HBox();
		playerBox.getChildren().addAll(circle, playerLbl);
		playerBox.setAlignment(Pos.CENTER);
		
		playerBox.setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				VBox popupContentVbox = new VBox();				
				popupContentVbox.setMinSize(700, 400);
				popupContentVbox.setAlignment(Pos.CENTER);
				popupContentVbox.getStyleClass().add("border");
				
				Label label = new Label("Player Information: "+name);
				popupContentVbox.getChildren().addAll(label);
				popup(popupContentVbox);
			}
		});
		
		playerBox.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				dismissPopup();
			}	
		});
		
		pane.getChildren().addAll(playerBox);
	}
	
	public void paintHex(int id, Color c){ // will be moved to Hex Class --> myHex.paint(Scene canvas)
		GraphicsContext gc = playingArea.getGraphicsContext2D();
		
		double height = HEX_HEIGHT;
		
		double xOffset = choosenMapping[id][0]*0.75*HEX_WIDTH-40.0; //40 move whole grid
		double yOffset = choosenMapping[id][1]*0.5*height-30.0; //30 moves whole grid
		//gc.fillOval(xOffset, yOffset, xOffset+200, yOffset+200);
		
		//outer polygon
		gc.setFill(Color.BLACK);
		gc.fillPolygon(new double[]{(xOffset+(HEX_WIDTH*0.25)), (xOffset+(HEX_WIDTH*0.75)), (xOffset+HEX_WIDTH), 	
									 xOffset+(HEX_WIDTH*0.75), xOffset+(HEX_WIDTH*0.25), xOffset},
					   new double[]{yOffset, yOffset, yOffset+(height*0.5), 		
									yOffset+height, yOffset+height, yOffset+(height*0.5)}, 6);
		//inner polygon
		double gap=HEX_WIDTH*0.05;
		double temp_width = HEX_WIDTH;
		xOffset+=gap;
		yOffset+=gap;
		height-=(gap*2);
		temp_width-=(gap*2);
		gc.setFill(c);
		gc.fillPolygon(new double[]{(xOffset+(temp_width*0.25)), (xOffset+(temp_width*0.75)), (xOffset+temp_width), 	
				 					xOffset+(temp_width*0.75), xOffset+(temp_width*0.25), xOffset},
				 	   new double[]{yOffset, yOffset, yOffset+(height*0.5), 		
									yOffset+height, yOffset+height, yOffset+(height*0.5)}, 6);
		//image
		gap=temp_width*0.05;
		double imageAdjust=4.0;
		gc.drawImage(HEX_IMAGE, xOffset+gap+(imageAdjust/2), yOffset+gap, temp_width-(gap*2.0)-imageAdjust, height-(gap*2.0));		
	}
	
	public void paintBlock(){
		// loop
			//paintThing(i, );
	}
	
	public void paintThing(int blockIndex){
		
		double thingWidth = 75;
		
		StackPane stack = new StackPane();
		
		Rectangle borderRect = new Rectangle();
		borderRect.setX(0);
		borderRect.setY(0);
		borderRect.setWidth(thingWidth);
		borderRect.setHeight(thingWidth);
		borderRect.setArcWidth(20);
		borderRect.setArcHeight(20);
		
		borderRect.setFill(Color.WHITE);
		
		final Rectangle coloredRect = new Rectangle();
		coloredRect.setX(0);
		coloredRect.setY(0);
		coloredRect.setWidth(thingWidth-1);
		coloredRect.setHeight(thingWidth-1);
		coloredRect.setArcWidth(20);
		coloredRect.setArcHeight(20);
		coloredRect.setFill(Color.GREEN);
		
		ImageView img = new ImageView(new Image("view/com/assets/pics/gamepieces/things/creatures/bears.jpeg"));
		img.setFitWidth(thingWidth-7); 
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
        img.getStyleClass().add("thing");
       
		
		stack.getChildren().addAll(borderRect, coloredRect, img);
		
		
		//lastThingIndexSelected= node.getChildren().size();
		currentPlayerInfoBox.getChildren().add(stack);
		
		
		img.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				if (lastThingRect != null){
					lastThingRect.setFill(Color.GREEN);
					//paintThing(lastThingIndexSelected, node);//(lastHexSelected,Color.DARKGRAY);
				}
				if (lastHexSelected != -1){
					paintHex(lastHexSelected,Color.DARKGRAY);
				}
				lastThingRect = coloredRect;
				paintThingInDetails();
				coloredRect.setFill(Color.WHITE);
			}
		});
	}
	
	public void paintThingInDetails(){
		detailsBox.getChildren().clear();
		
		ImageView img = new ImageView(new Image("view/com/assets/pics/gamepieces/things/creatures/bears.jpeg"));
		img.setFitWidth(300); 
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
		
		Label name = new Label("Bear");
		Label type = new Label("Forest");
		Label owner = new Label(currentPlayersName);
		
		detailsBox.getChildren().addAll(img, name, type, owner);
	}
	
	public void paintHexInDetails(){
		detailsBox.getChildren().clear();
		
		ImageView img = new ImageView(HEX_IMAGE);
		img.setFitWidth(300); 
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
		
		Label name = new Label("Desert");
		Label owner = new Label("Not Owned");
		
		detailsBox.getChildren().addAll(img, name, owner);
	}
	
	// temp
	public void onHexSelected(int id){
		if (lastThingRect != null){
			lastThingRect.setFill(Color.GREEN);
			//paintThing(lastThingIndexSelected, node);//(lastHexSelected,Color.DARKGRAY);
		}
		if (lastHexSelected != -1){
			paintHex(lastHexSelected,Color.DARKGRAY);
		}
		lastHexSelected = id;
		paintHex(id,Color.WHITE);
		paintHexInDetails();
	}
	
	
	private void handleDieRoll(ActionEvent event) {
	    // UI stuff ok here.
	    VBox popupContentVbox = new VBox();				
	    popupContentVbox.setMinSize(700, 400);
	    popupContentVbox.setAlignment(Pos.CENTER);
	    popupContentVbox.getStyleClass().add("border");

	    Label label = new Label("Test");
	    popupContentVbox.getChildren().addAll(label);
	    popupWithTitleAndCloseButton("Title:",popupContentVbox);

	    // Bindings will update the images.
	    ctrl.rollDice();
//	    die1.roll();
//	    die1Im.setImage(die1.getImage());
//	    die2.roll();
//	    die2Im.setImage(die2.getImage());
	}
	
	
	// BINDINGS
	//TODO Consider passing adapter to constructor in Main instead
	// Changes in the singleton game instance are automatically reflected in the UI.
	public void setBindings(BeanPathAdapter<Game> adapter) {
	    // Bind player label => game.getPlayer().getName()
//	    adapter.bindBidirectional("player.name", playerLbl.textProperty());
	    
	    // Dice bindings
	    bindDiceImages(adapter);
	}
	
	private void bindDiceImages(BeanPathAdapter<Game> adapter) {
	    
	    die1Im.imageProperty().addListener(new ChangeListener<Image>() {

		@Override
		public void changed(ObservableValue<? extends Image> ov, Image t, Image t1) {
		    System.out.println("Image changed");
		}
	    });
	    
	    // Current WORKAROUND
	    die1Im.imageProperty().bindBidirectional(adapter.getBean().getDie1().getImageProperty());
	    die2Im.imageProperty().bind(adapter.getBean().getDie2().getImageProperty());
	    
	    // Does not work
//	    adapter.bindBidirectional("die.image", die1Im.imageProperty(), Image.class);
	}
	
	
	public final void setController(GameScreenCntrl ctrl) {
	    this.ctrl = ctrl;
	}
}
