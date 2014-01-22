/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.view.model.GameViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.com.GameView;

/**
 *
 * @author kurtis
 */
public class MVPMain extends Application {

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
        stage.setResizable(true);
        stage.setFullScreen(false);
        
        GameViewModel model = new GameViewModel();
        GameView view = new GameView(model);
        GamePresenter presenter = new GamePresenter(model, view);
        
        //TODO check how they init in example
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
        
        stage.setScene(null);
    }
    
}
