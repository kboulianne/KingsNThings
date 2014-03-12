package com.main;

import com.presenter.LobbyPresenter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class LobbyTestMain extends Application {
    static final double WIDTH = 400;
    static final double HEIGHT = 800;
    
    static Scene scene;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
//    	final GamePresenter main = KNTAppFactory.getGamePresenter();
    		final LobbyPresenter lobby = KNTAppFactory.getLobbyPresenter();
//    		lobby.setWindow(stage);
    		
        stage.setTitle("Kings & Things");
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.getIcons().add(new Image("view/com/assets/pics/icon.png"));
		stage.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				lobby.handleWindowShown();
			}
		});
        
        scene = new Scene(lobby.getView(), WIDTH, HEIGHT);
        scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
        scene.getStylesheets().add("view/com/assets/docs/gameScreen.css");
	
        stage.setScene(scene);
        stage.show();
    }
    
    // TODO Implement view interface
    public static void setView(Parent n) {
    		scene.setRoot(n);
    }
}