package com.main;

import com.model.Player;
import com.presenter.LobbyPresenter;
import com.presenter.StartScreenPresenter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class NetworkedMain extends Application {    
    static Stage primaryStage;
    static Scene scene;
    
    // The player which owns/plays on this client.
    private static Player player;
    private static String roomName;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
    	String host = "localhost";
    	Integer port = null;
    	
    	// Initialize the client with command line arguments if present
    	if (getParameters().getNamed().containsKey("host")) {
    		host = getParameters().getNamed().get("host");
    	}
    	if (getParameters().getNamed().containsKey("port")) {
    		getParameters().getNamed().get("port");
    	}
    	
    	// Defaults to localhost and default port if not present.
    	KNTAppFactory.init(host, port);
    	
    	final StartScreenPresenter start = KNTAppFactory.getStartScreenPresenter();
    	
        stage.setTitle("Kings & Things");

        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.getIcons().add(new Image("view/com/assets/pics/icon.png"));
        
        scene = new Scene(start.getView());
        scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
        scene.getStylesheets().add("view/com/assets/docs/gameScreen.css");
        primaryStage = stage;
        
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    
    public static boolean isPlayerTurn(Player gameCurrent) {
    		return gameCurrent.equals(player);
    }
    
    public static final Player getPlayer() {
    		return player;
    }
    
    
    public static final void setPlayer(final Player player) {
    		NetworkedMain.player = player;
    }
    
    public static final String getRoomName() {
    		return roomName;
    }
    
	public static final void setRoomName(String room) {
		roomName = room;
	}
    
    // TODO Implement view interface
    public static void setView(Parent n) {
	    	scene.setRoot(n);
	    	// size content
	    	primaryStage.sizeToScene();
	    	primaryStage.centerOnScreen();
    }
    
    public static void setView(Parent n, double w, double h) {
    		scene.setRoot(n);
    		primaryStage.setMinWidth(w);
    		primaryStage.setMinHeight(h);
    		primaryStage.setWidth(w);
    		primaryStage.setHeight(h);
    		primaryStage.centerOnScreen();
//    		primaryStage.sizeToScene();
    }
    
    public static void setTitle(String title) {
    		primaryStage.setTitle(title);
    }
}