package com.presenter;

import com.view.GameScreen;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
	
	static final double WIDTH = 1000;
	static final double HEIGHT = 600;
	public static Stage stage;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
	    System.err.println("RUN MVPApplication");
		Main.stage = stage;
		stage.setTitle("Kings & Things");
		stage.setMinHeight(HEIGHT);
		stage.setMinWidth(WIDTH);
		stage.initStyle(StageStyle.DECORATED);
		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.getIcons().add(new Image("view/com/assets/pics/icon.png"));
		
		//doesn't work
		//Font.loadFont(Main.class.getResource("VIKING.TTF").toExternalForm(), 12);
		//stage.setIconified(true);
		//stage.setMaxHeight(HEIGHT);
		//stage.setMaxWidth(WIDTH);
		//stage.setFullScreen(true);
	    //new StartScreen().show();
		//view.show(); //temporary
		
	    GameScreen view = new GameScreen();
	    // Model is taken from GameService
	    GameScreenCntrl ctrl = new GameScreenCntrl(view);
	    view.setController(ctrl);
	    
	    ctrl.initialize();
	}
	
	public static Stage getStage() {
		return stage;
	}
}
