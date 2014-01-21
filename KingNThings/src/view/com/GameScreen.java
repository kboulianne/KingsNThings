package view.com;

import java.util.ArrayList;
import java.util.List;

import controller.com.GameScreenCntrl;
import controller.com.Main;
import controller.com.Util;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
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
//import jfxtras.labs.scene.control.BeanPathAdapter;
import model.com.Die;

import model.com.Hex;
import model.com.HexDesert;
import model.com.Player;
import model.com.SwampCreature;
import model.com.Thing;

import model.com.game.Game;

public class GameScreen {
	
    private GameScreenCntrl ctrl;
    
	public static Canvas playingArea;
	public static VBox detailsBox;
	static StackPane rootStackPane;
	static VBox popupVbox;
	
	static final double HEX_WIDTH = 100.0; 
	static final double HEX_HEIGHT = HEX_WIDTH *0.8;
	double[][] hexCenterPoints;
	static double[][] choosenMapping;
		
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

	// for now
	Labeled currentPlayerLbl;
	ImageView die1Im;
	ImageView die2Im;
	Player player1 = new Player(Player.PlayerId.ONE, "Paul McCartney");
	Player player2 = new Player(Player.PlayerId.TWO, "Bill Gates");
	Player player3 = new Player(Player.PlayerId.THREE, "Mick Jagger");
	Player player4 = new Player(Player.PlayerId.FOUR, "Sean Connery");
	Thing thing = new SwampCreature("ghost");
	List<Hex> hexes = new ArrayList<Hex>();
	
	public void show(){
		
		//GameStatus
		AnchorPane gameStatus = new AnchorPane();
		gameStatus.setId("gameStatus");
		
		final Label turn = new Label("Sir "+player4.getName()+"'s Turn: Roll Dice");
		turn.getStyleClass().add("title");
		gameStatus.getChildren().add(turn);
		AnchorPane.setLeftAnchor(turn, 0.0);
		AnchorPane.setTopAnchor(turn, 10.0);
		
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
		Image imgBg = new Image("view/com/assets/pics/background.jpg");
		gc.drawImage(imgBg, 0,0,playingArea.getWidth(), playingArea.getHeight());
		
		//ScrollPane playingAreaScrollPane = new ScrollPane();
		//playingAreaScrollPane.setPrefSize(1280*0.5, HEX_HEIGHT*7.2);//8
		//playingAreaScrollPane.setContent(playingArea);
		
		choosenMapping = MAPPING_37_TILES;
		hexCenterPoints = new double[choosenMapping.length][2];
		for(int i = 0; i<choosenMapping.length;i++){
			double xOffset = choosenMapping[i][0]*0.75*HEX_WIDTH-40.0;
			double yOffset = choosenMapping[i][1]*0.5*HEX_HEIGHT-30.0;
			hexCenterPoints[i][0]=xOffset+(HEX_WIDTH*0.5);
			hexCenterPoints[i][1]=yOffset+(HEX_HEIGHT*0.5);
			Hex hex = new HexDesert(i);
			hexes.add(hex);
			hex.paint(null);
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
		player1.paint(otherPlayerInfo);
		player2.paint(otherPlayerInfo);
		player3.paint(otherPlayerInfo);
		otherPlayerInfo.setAlignment(Pos.TOP_CENTER);
		sidePane.getChildren().addAll(otherPlayerInfo, detailsBox);
		
		HBox centerBox = new HBox();
		sidePane.setId("sidePane");
		centerBox.getChildren().addAll(sidePane, playingArea);
		
		// Player Info
		HBox currentPlayerInfoBox = new HBox();
		currentPlayerInfoBox.setId("playerInfo");
		VBox currentPlayerNameAndGold = new VBox();
		currentPlayerNameAndGold.setAlignment(Pos.CENTER);
		player4.paint(currentPlayerNameAndGold);
		player4.paintGold(currentPlayerNameAndGold);
		currentPlayerInfoBox.getChildren().add(currentPlayerNameAndGold);
		
		for(int i =0; i<7;i++)
			thing.paint(currentPlayerInfoBox);
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
		
		// Test handler
		button.setOnAction(new EventHandler<ActionEvent>() {

		    @Override
		    public void handle(ActionEvent t) {
			test();
		    }
		});
	}
	
	// Pop-up Functions
	public static void popup( Node content){	
		if (rootStackPane.getChildren().size() == 1){			
			popupVbox = new VBox();
			popupVbox.getStyleClass().add("popup");
			popupVbox.getChildren().addAll(content);
			popupVbox.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
			rootStackPane.getChildren().add(popupVbox);
		}
	}
	public static void dismissPopup(){
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
	
	private void createCurrentPlayerLabel(final Pane p) {
	    currentPlayerLbl = new Label();
	    currentPlayerLbl.getStyleClass().add("playerName"); 
	    Circle circle = new Circle();
	    circle.setRadius(6);
	    // TODO Hardcoded. Bind
	    circle.setFill(Color.WHITE);   
	    HBox playerBox = new HBox();
	    playerBox.getChildren().addAll(circle, currentPlayerLbl);
	    playerBox.setAlignment(Pos.CENTER);
	    
	    // TODO no handlers.
	    
	    p.getChildren().addAll(playerBox);
	}
	

	
	// temp
	public void onHexSelected(int id){
		/*if (lastThingRect != null){
			lastThingRect.setFill(Color.GREEN);
			//paintThing(lastThingIndexSelected, node);//(lastHexSelected,Color.DARKGRAY);
		}
		if (lastHexSelected != -1){
			//paintHex(lastHexSelected,Color.DARKGRAY);
		}
		lastHexSelected = id;*/
		hexes.get(id).paintHexInDetails(detailsBox);
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
	}
	
	private void test() {
	    ctrl.test_Phases();
	}
	
	
	// BINDINGS
	//TODO Consider passing adapter to constructor in Main instead
	// Changes in the singleton game instance are automatically reflected in the UI.
	public void setBindingsFor(final Game game) {
	    // Bind player label => game.getPlayer().getName()
//	    adapter.bindBidirectional("player.name", playerLbl.textProperty());
	    
	    // Dice bindings
	    bindDice(game.getDie1Property().get(), game.getDie2Property().get());

	    // Bind players
	    bindPlayers(game.getCurrentPlayer());
	}
	
	public final void bindDice(final Die die1, final Die die2) {
	    die1Im.imageProperty().bind(die1.getImageProperty());
	    die2Im.imageProperty().bind(die2.getImageProperty());
	}

	public final void bindPlayers(final Player player) {
	   
		// Bind the current player
	    // TODO add Male/Female property
		// broken here
	    currentPlayerLbl.textProperty().bind(Bindings.concat("Sir ").concat(player.getNameProperty()));
	}
	
	
	public final void setController(GameScreenCntrl ctrl) {
	    this.ctrl = ctrl;
	}
	
	// getters and setters
	public static double getHexWidth() {
		return HEX_WIDTH;
	}

	public static double getHexHeight() {
		return HEX_HEIGHT;
	}

	public static double[][] getChoosenMapping() {
		return choosenMapping;
	}
}
