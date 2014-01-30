/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.main.KNTAppFactory;
import com.model.game.phase.GamePlay;
import com.presenter.GamePresenter;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author kurtis
 */
public class MVPApplication extends Application {

    static final double WIDTH = 1280;
    static final double HEIGHT = 800;
    
    public static void main(String[] args) {
        launch(args);
    }
	
	
    @Override
    public void start(Stage stage) throws Exception {
    	final GamePresenter main = KNTAppFactory.getGamePresenter();
	
		// GameView view = new GameView(GameService.getInstance().getGame());
		// GamePresenter presenter = new GamePresenter(view);
		// view.setPresenter(presenter);
	
        stage.setTitle("Kings & Things");
        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.getIcons().add(new Image("view/com/assets/pics/icon.png"));
		stage.setOnShown(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent t) {
				main.startGame();
			}
		});
		
        Scene scene = new Scene(main.getView(), WIDTH, HEIGHT);
        scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
        scene.getStylesheets().add("view/com/assets/docs/gameScreen.css");
	
        stage.setScene(scene);
        stage.show();
		
    }
}
