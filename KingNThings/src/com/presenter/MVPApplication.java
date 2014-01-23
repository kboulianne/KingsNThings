/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import com.main.KingsNThingsAppFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

	
	KingsNThingsAppFactory factory = new KingsNThingsAppFactory();
	GamePresenter main = factory.getMainPresenter();
	
//        GameView view = new GameView(GameService.getInstance().getGame());
//        GamePresenter presenter = new GamePresenter(view);
//        view.setPresenter(presenter);
	
        stage.setTitle("Kings & Things");
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.getIcons().add(new Image("view/com/assets/pics/icon.png"));
	
        Scene scene = new Scene(main.getView(), WIDTH, HEIGHT);
	scene.getStylesheets().add("view/com/assets/docs/kingsnthings.css");
	scene.getStylesheets().add("view/com/assets/docs/gameScreen.css");
	
        stage.setScene(scene);
        stage.show();
    }
    
}
