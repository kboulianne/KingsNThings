/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.com.GameView;

/**
 *
 * @author kurtis
 */
public class MVPApplication extends Application {

    static final double WIDTH = 1000;
    static final double HEIGHT = 600;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("MVP Option 1");
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setFullScreen(false);
        
        GameView view = new GameView(GameService.getInstance().getGame());
        GamePresenter presenter = new GamePresenter(view);
        
        // TODO create initScene method
        Scene scene = new Scene(view);
	scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
	scene.getStylesheets().add("view/com/assets/docs/gameScreen.css");
	
        stage.setScene(scene);
        stage.show();
    }
    
}
