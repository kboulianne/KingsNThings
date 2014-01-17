package view.com;

import controller.com.GameScreenCntrl;
import controller.com.Main;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GameScreen {
	
	String currentPlayersName = "Frank";
	String otherPlayerName1 = "Joe";
	String otherPlayerName2 = "Roxanne";
	String otherPlayerName3 = "Henry";
	
	static final double HEX_WIDTH = 100.0; //200 can see picture
	static final double HEX_HEIGHT = HEX_WIDTH *0.8;
	double[][] hexCenterPoints;
	double[][] choosenMapping;
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
	private static final Image HEX_IMAGE = new Image("view/com/assets/pics/seaTile.png");
        
	public void show(){
		
		//GameStatus
		final Label turn = new Label("Turn: Sir "+otherPlayerName3);	
		HBox gameStatus = new HBox();
		gameStatus.getStyleClass().add("border");
		gameStatus.setId("gameStatus");
		gameStatus.getChildren().addAll(turn);
			
		//Opponents
		Label otherPlayerLbl1 = new Label("Sir "+otherPlayerName1);
                otherPlayerLbl1.getStyleClass().add("playerName"); 
                Circle circle1 = new Circle();
                circle1.setRadius(6);
                circle1.setFill(Paint.valueOf("FFFF00"));   
                HBox otherPlayerBob1 = new HBox();
                otherPlayerBob1.getChildren().addAll(circle1, otherPlayerLbl1);
                otherPlayerBob1.setAlignment(Pos.CENTER);
                HBox otherPlayerInfo = new HBox();
		otherPlayerInfo.setId("otherPlayerInfo");
		otherPlayerInfo.getChildren().add(otherPlayerBob1);
		
		//Canvas
		Canvas playingArea = new Canvas(900,HEX_HEIGHT*8);
		playingArea.setId("playingArea");
		playingArea.getStyleClass().add("border");
		playingArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double clickPtX = event.getX();
				double clickPtY = event.getY();
				for(int i = 0; i<hexCenterPoints.length;i++){
					if(distanceBtwTwoPts(clickPtX, clickPtY, hexCenterPoints[i][0], hexCenterPoints[i][1] )<HEX_WIDTH*0.30){
						turn.setText("Tile "+i+" Selected");
						onHexSelected(i);
						return;
					}
				}
			}
		});
		
		ScrollPane sp = new ScrollPane();
		sp.setPrefSize(900, HEX_HEIGHT*8);
		sp.setContent(playingArea);
		
		// drawing on canvas
		GraphicsContext gc = playingArea.getGraphicsContext2D();
		gc.clearRect(0, 0,playingArea.getWidth(), playingArea.getHeight());		
		
		choosenMapping = MAPPING_37_TILES;
		hexCenterPoints = new double[choosenMapping.length][2];
		
		for(int i = 0; i<choosenMapping.length;i++){
			//double x = choosenMapping[i][0];
			//double y = choosenMapping[i][1];
			double xOffset = choosenMapping[i][0]*0.75*HEX_WIDTH;
			double yOffset = choosenMapping[i][1]*0.5*HEX_HEIGHT;
			hexCenterPoints[i][0]=xOffset+(HEX_WIDTH*0.5);
			hexCenterPoints[i][1]=yOffset+(HEX_HEIGHT*0.5);
			paintHex(i, playingArea);
		}
		
		
		
		
		
		
		Label currentPlayer = new Label("Sir "+currentPlayersName);
                currentPlayer.getStyleClass().add("playerName"); 
                Circle circle = new Circle();
                circle.setRadius(6);
                circle.setFill(Paint.valueOf("00FF00"));   
                HBox currentPlayerBox = new HBox();
                currentPlayerBox.getChildren().addAll(circle, currentPlayer);
                currentPlayerBox.setAlignment(Pos.CENTER);
		HBox playerInfo = new HBox();
		playerInfo.getStyleClass().add("border");
		playerInfo.getChildren().add(currentPlayerBox);
		
        
		VBox root = new VBox();
		root.setMinHeight(8000);
		root.getChildren().addAll(gameStatus, otherPlayerInfo, sp, playerInfo);
		root.setAlignment(Pos.TOP_CENTER);
		
		Scene scene = new Scene(root,1000,600);
		scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
		scene.getStylesheets().add("view/com/assets/docs/gameScreen.css");
		
		Stage stage = Main.getStage();
		stage.setScene(scene);
		stage.show();
		
		new GameScreenCntrl();
	}
	
	
	
	public void paintHex(int id, Canvas canvas){ // will be moved to Hex Class --> myHex.paint(Scene canvas)
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		double height = HEX_HEIGHT;
		
		double xOffset = choosenMapping[id][0]*0.75*HEX_WIDTH;
		double yOffset = choosenMapping[id][1]*0.5*height;
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
		gc.setFill(Color.BLUE);
		gc.fillPolygon(new double[]{(xOffset+(temp_width*0.25)), (xOffset+(temp_width*0.75)), (xOffset+temp_width), 	
				 					xOffset+(temp_width*0.75), xOffset+(temp_width*0.25), xOffset},
				 	   new double[]{yOffset, yOffset, yOffset+(height*0.5), 		
									yOffset+height, yOffset+height, yOffset+(height*0.5)}, 6);
		//image
		gap=temp_width*0.05;
		gc.drawImage(HEX_IMAGE, xOffset+gap, yOffset+gap, temp_width-(gap*2.0), height-(gap*2.0));
		
	}
	
	public void onHexSelected(int id){
		
	}
	
	public double distanceBtwTwoPts(double x1, double y1, double x2, double y2) {
        return Math.sqrt( (x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
	}
}
