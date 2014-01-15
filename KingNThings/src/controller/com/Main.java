package controller.com;


import view.com.StartScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	static final double WIDTH = 1000;
	static final double HEIGHT = 600;
	public static Stage stage;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Main.stage = stage;
		stage.setTitle("Kings N Things");
		stage.setMinHeight(HEIGHT);
		stage.setMinWidth(WIDTH);
		//stage.setMaxHeight(HEIGHT);
		//stage.setMaxWidth(WIDTH);
		//stage.setFullScreen(true);
		new StartScreen().show();
	}
	
	public static Stage getStage() {
		return stage;
	}

}
