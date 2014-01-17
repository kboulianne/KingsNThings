package view.com;

import model.com.Die;
import controller.com.Main;
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
import javafx.stage.Stage;

public class GameScreen {
	
	Canvas playingArea;
	
	String currentPlayersName = "Frank";
	String otherPlayerName1 = "Joe";
	String otherPlayerName2 = "Roxanne";
	String otherPlayerName3 = "Henry";
	
	static final double HEX_WIDTH = 100.0; //200 can see picture
	static final double HEX_HEIGHT = HEX_WIDTH *0.8;
	double[][] hexCenterPoints;
	double[][] choosenMapping;
	
	StackPane rootStackPane;
	
	int lastHexSelected = -1;
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
	private static final Image HEX_IMAGE = new Image("view/com/assets/pics/tiles/sea.png");
	//GameScreen.class.getResourceAsStream("icon.png"));//
	
	String[] gamePhases = {"Gold Collection","Recruiting Characters", "Recruiting Things",
						"Random Event Phase", "Movement Phase", "Combat Phase", "Construction Phase",
						"Special Powers Phase", "Changing Player Order"};
	
	
	Die die1 = new Die();
	Die die2 = new Die();
	ImageView die1Im;
	ImageView die2Im;
	
	
	public void show(){
		
		//GameStatus
		AnchorPane gameStatus = new AnchorPane();
		gameStatus.setId("gameStatus");
		
		
		
		final Label turn = new Label("Sir "+otherPlayerName3+"'s Turn: Roll Dice");
		turn.getStyleClass().add("title");
		gameStatus.getChildren().add(turn);
		AnchorPane.setLeftAnchor(turn, 0.0);
		AnchorPane.setTopAnchor(turn, 0.0);
		
		die1 = new Die();
		die2 = new Die();
		
		die1Im = new ImageView(die1.getImage());
		die1Im.setFitWidth(48); 
		die1Im.setPreserveRatio(true);
		
		die2Im = new ImageView(die2.getImage());
		die2Im.setFitWidth(48); 
		die2Im.setPreserveRatio(true);
		
		Button button = new Button("Roll");
		
		HBox topRBox = new HBox();
		topRBox.setId("topRBox");
		topRBox.getChildren().addAll(die1Im, die2Im, button);
		AnchorPane.setRightAnchor(topRBox, 0.0);
		AnchorPane.setTopAnchor(topRBox, 0.0);
		gameStatus.getChildren().add(topRBox);
		
		
		//Opponents
		HBox otherPlayerInfo = new HBox();
		otherPlayerInfo.setId("otherPlayerInfo");
		otherPlayerInfo.getChildren().add(new Label("Opponents:"));	
		paintPlayerName(otherPlayerName1, Color.YELLOW, otherPlayerInfo);
		paintPlayerName(otherPlayerName2, Color.RED, otherPlayerInfo);
		paintPlayerName(otherPlayerName3, Color.BLUE, otherPlayerInfo);
		
		//Canvas
		playingArea = new Canvas(900,HEX_HEIGHT*7.4);
		playingArea.setId("playingArea");
		playingArea.getStyleClass().add("border");
		
		ScrollPane sp = new ScrollPane();
		sp.setPrefSize(900, HEX_HEIGHT*8);
		sp.setContent(playingArea);
		
		// drawing on canvas
		GraphicsContext gc = playingArea.getGraphicsContext2D();
		gc.clearRect(0, 0,playingArea.getWidth(), playingArea.getHeight());		
		
		choosenMapping = MAPPING_37_TILES;
		hexCenterPoints = new double[choosenMapping.length][2];
		
		for(int i = 0; i<choosenMapping.length;i++){
			double xOffset = choosenMapping[i][0]*0.75*HEX_WIDTH-50.0;
			double yOffset = choosenMapping[i][1]*0.5*HEX_HEIGHT-25.0;
			hexCenterPoints[i][0]=xOffset+(HEX_WIDTH*0.5);
			hexCenterPoints[i][1]=yOffset+(HEX_HEIGHT*0.5);
			paintHex(i,Color.DARKGRAY);
		}
		
		// Player Info
		HBox playerInfo = new HBox();
		playerInfo.setId("playerInfo");
		paintPlayerName(currentPlayersName, Color.GREEN, playerInfo);
		
        
		VBox rootVBox = new VBox();
		
		rootVBox.getStyleClass().add("border");
		rootVBox.getChildren().addAll(gameStatus, otherPlayerInfo, sp, playerInfo);
		rootVBox.setAlignment(Pos.TOP_CENTER);
		
		rootStackPane = new StackPane();
		rootStackPane.getChildren().add(rootVBox);
		//rootStackPane.setMinSize(1280, 800);
		
		Scene scene = new Scene(rootStackPane,1280,800);
		scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
		scene.getStylesheets().add("view/com/assets/docs/gameScreen.css");
		
		Stage stage = Main.getStage();
		stage.setScene(scene);
		stage.show();
		
		//new GameScreenCntrl(playingArea, button);
		
		
		playingArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double clickPtX = event.getX();
				double clickPtY = event.getY();
				for(int i = 0; i<hexCenterPoints.length;i++){
					if(distanceBtwTwoPts(clickPtX, clickPtY, hexCenterPoints[i][0], hexCenterPoints[i][1] )<HEX_WIDTH*0.30){
						onHexSelected(i);
						return;
					}
				}
			}
		});
		

		
		button.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				
				VBox popupContentVbox = new VBox();				
				popupContentVbox.setMinSize(700, 400);
				popupContentVbox.setAlignment(Pos.CENTER);
				popupContentVbox.getStyleClass().add("border");
				
				Label label = new Label("Test");
				popupContentVbox.getChildren().addAll(label);
				popupWithTitleAndCloseButton("Title:",popupContentVbox);
				die1.roll();
				die1Im.setImage(die1.getImage());
				die2.roll();
				die2Im.setImage(die2.getImage());
			}
		});
	}
	
	VBox popupVbox;
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
					rootStackPane.getChildren().remove(popupVbox);
				}
			});
		}
	}
	
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
		
		double xOffset = choosenMapping[id][0]*0.75*HEX_WIDTH-50.0; //50 move whole grid
		double yOffset = choosenMapping[id][1]*0.5*height-25.0; //25 moves whole grid
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
	
	public void onHexSelected(int id){
		
		if (lastHexSelected != -1){
			paintHex(lastHexSelected,Color.DARKGRAY);
		}
		lastHexSelected = id;
		paintHex(id,Color.WHITE);
	}
	
	public double distanceBtwTwoPts(double x1, double y1, double x2, double y2) {
        return Math.sqrt( (x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
	}
}
