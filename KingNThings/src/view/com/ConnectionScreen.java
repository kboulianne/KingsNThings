package view.com;

import controller.com.ConnectionScreenCntrl;
import controller.com.Main;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ConnectionScreen {

	private String player1Name;
	static final String W_STR = "Waiting for Connection...";
	
	public ConnectionScreen(String player) {
		player1Name = player;
	}

	public void show(){
		
		ImageView dragonImg = new ImageView(new Image("view/com/assets/pics/dragon.png"));
        dragonImg.setFitWidth(400); 
        dragonImg.setPreserveRatio(true);
        dragonImg.setSmooth(true);
        dragonImg.setCache(true);
		
		Label title = new Label("Kings & Things");
        title.setId("title");
        
        Label players = new Label("Noble Players:");
        players.setId("players");
        
        Label player1 = new Label(W_STR);
        player1.getStyleClass().add("disabled"); 
        Circle circle1 = new Circle();
        circle1.setRadius(10);
        circle1.setFill(Paint.valueOf("999999"));   
        HBox player1Box = new HBox();
        player1Box.getChildren().addAll(circle1, player1);
        player1Box.setAlignment(Pos.CENTER);
        
        Label player2 = new Label(W_STR);
        player2.getStyleClass().add("disabled"); 
        Circle circle2 = new Circle();
        circle2.setRadius(10);
        circle2.setFill(Paint.valueOf("999999"));   
        HBox player2Box = new HBox();
        player2Box.getChildren().addAll(circle2, player2);
        player2Box.setAlignment(Pos.CENTER);
        
        Label player3 = new Label(W_STR);
        player3.getStyleClass().add("disabled"); 
        Circle circle3 = new Circle();
        circle3.setRadius(10);
        circle3.setFill(Paint.valueOf("999999"));   
        HBox player3Box = new HBox();
        player3Box.getChildren().addAll(circle3, player3);
        player3Box.setAlignment(Pos.CENTER);
        
        Label player4 = new Label(W_STR);
        player4.getStyleClass().add("disabled"); 
        Circle circle4 = new Circle();
        circle4.setRadius(10);
        circle4.setFill(Paint.valueOf("999999"));   
        HBox player4Box = new HBox();
        player4Box.getChildren().addAll(circle4, player4);
        player4Box.setAlignment(Pos.CENTER);
        
        Button beginButton = new Button("Begin Game");
		beginButton.setId("beginButton");
		
		VBox cont = new VBox();
		cont.setId("vBox2");
		cont.getChildren().addAll(players,player1Box, player2Box, player3Box, player4Box);
		cont.setAlignment(Pos.CENTER);
		
		VBox cont2 = new VBox();
		cont2.setId("vBox");
		cont2.getChildren().addAll(title, cont, beginButton);
		cont2.setAlignment(Pos.CENTER);
		
		HBox root = new HBox();
		root.getChildren().addAll(dragonImg, cont2);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root,1000,600);
		scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
		scene.getStylesheets().add("view/com/assets/docs/connectionScreen.css");
		
		Stage stage = Main.getStage();
		stage.setScene(scene);
		stage.show();
		
		ConnectionScreenCntrl cSC = new ConnectionScreenCntrl(player1, circle1, player2, circle2, player3, circle3, player4, circle4, beginButton);
		//test
		cSC.addPlayer(player1Name);
		cSC.addPlayer("Beverly");
		cSC.addPlayer("Hans Hanzel");
		//cSC.addPlayer("Wilson");
		
	}
}
